package net.kwas.acore.dbc.reader;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcHeader;
import net.kwas.acore.dbc.model.DbcLocale;
import net.kwas.acore.dbc.model.DbcLocaleString;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public record DbcReader(Path dbcFolder) {

    // Every DBC file should start with the bytes "WDBC"
    private static final long MAGIC_VALUE = 'W' | 'D' << 8 | 'B' << 16 | 'C' << 24;

    // One byte for the magic value and four for the header itself.
    private static final long HEADER_OFFSET = 5 * 4;

    public <T> List<T> readDbc(Class<T> recordType) {
        try {
            return readDbcChecked(recordType);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> readDbcChecked(Class<T> recordType) throws Exception {
        if (!recordType.isRecord()) {
            throw new RuntimeException("Non record type passed in: " + recordType);
        }

        var fileName = getFilename(recordType);

        if (!checkMagicValue(fileName)) {
            throw new RuntimeException("Invalid magic value at start of file: " + fileName);
        }

        var header = getHeader(fileName);

        if (!checkRecordSize(recordType, header)) {
            throw new RuntimeException("Invalid record size '" + header.recordSize() + "' for record type: " + recordType);
        }

        var stringMap = getStringMap(fileName, header);
        var records = getRecords(recordType, fileName, header, stringMap);

        return records;
    }

    private InputStream newBufferedStream(Path filename) throws IOException {
        return new BufferedInputStream(Files.newInputStream(filename));
    }

    private <T> Path getFilename(Class<T> recordType) {
        var className = recordType.getSimpleName();
        var fileName = className.replaceAll("Dbc$", "");
        return dbcFolder.resolve(fileName + ".dbc");
    }

    private boolean checkMagicValue(Path fileName) throws IOException {
        try (var inputStream = newBufferedStream(fileName)) {
            var magicValue = readUint(inputStream);
            return MAGIC_VALUE == magicValue;
        }
    }

    private DbcField getDbcField(RecordComponent component) {
        var annotation = component.getAnnotation(DbcField.class);

        if (annotation == null) {
            throw new RuntimeException("Unable to find DbcField annotation on record component: " + component);
        }

        return annotation;
    }

    private <T> boolean checkRecordSize(Class<T> recordType, DbcHeader header) {
        var components = recordType.getRecordComponents();

        int totalSize = 0;
        for (var component : components) {
            var annotation = getDbcField(component);
            totalSize += annotation.type().getSize() * annotation.count();
        }

        return totalSize == header.recordSize();
    }

    private DbcHeader getHeader(Path fileName) throws IOException {
        try (var inputStream = newBufferedStream(fileName)) {
            return getHeader(inputStream);
        }
    }

    private DbcHeader getHeader(InputStream inputStream) throws IOException {
        long skipped = inputStream.skip(4L);

        long recordCount = readUint(inputStream);
        long fieldCount = readUint(inputStream);
        long recordSize = readUint(inputStream);
        long stringBlockSize = readUint(inputStream);

        return new DbcHeader(recordCount, fieldCount, recordSize, stringBlockSize);
    }

    private <T> List<T> getRecords(Class<T> recordType, Path filePath, DbcHeader header, Map<Long, String> stringMap) throws Exception {
        try (var inputStream = newBufferedStream(filePath)) {
            return getRecords(recordType, inputStream, header, stringMap);
        }
    }

    private <T> List<T> getRecords(Class<T> recordType, InputStream inputStream, DbcHeader header, Map<Long, String> stringMap) throws Exception {
        var components = recordType.getRecordComponents();
        var constructorArgs = Arrays.stream(components)
            .map(RecordComponent::getType)
            .toArray(Class[]::new);
        var constructor = recordType.getConstructor(constructorArgs);

        long skipped = inputStream.skip(HEADER_OFFSET);
        if (skipped != HEADER_OFFSET) {
            throw new RuntimeException("Incorrect amount of bytes skipped: " + skipped);
        }

        var retVal = new ArrayList<T>();

        for (int i = 0; i < header.recordCount(); i++) {
            var currIdx = 0;
            var constructorFields = new Object[components.length];
            for (var component : components) {
                var dbcField = getDbcField(component);

                var type = dbcField.type();
                var count = dbcField.count();

                var inputValue = switch (type) {
                    case UINT32 -> readMultiple(this::readUint, count, inputStream);
                    case INT32 -> readMultiple(this::readInt, count, inputStream);
                    case FLOAT -> readMultiple(this::readFloat, count, inputStream);
                    case LOCALE_STRING -> readMultiple(x -> readLocaleString(x, stringMap), count, inputStream);
                };

                if (count == 1) {
                    constructorFields[currIdx++] = inputValue.get(0);
                }
                else {
                    constructorFields[currIdx++] = inputValue;
                }
            }

            var newRecord = constructor.newInstance(constructorFields);
            retVal.add(newRecord);
        }

        return retVal;
    }

    private Map<Long, String> getStringMap(Path filePath, DbcHeader header) throws IOException {
        try (var inputStream = newBufferedStream(filePath)) {
            return getStringMap(inputStream, header);
        }
    }

    private Map<Long, String> getStringMap(InputStream inputStream, DbcHeader header) throws IOException {
        var retVal = new HashMap<Long, String>();

        long startOffset = HEADER_OFFSET + header.getRecordOffset();
        long skipped = inputStream.skip(startOffset);
        if (skipped != startOffset) {
            throw new RuntimeException("Incorrect amount of bytes skipped: " + skipped);
        }

        long currOffset = 0;
        long thisOffset = currOffset;
        var builder = new StringBuilder();
        while (currOffset < header.stringBlockSize()) {

            int nextChar = inputStream.read();

            if (nextChar == 0) {
                retVal.put(thisOffset, builder.toString());
                builder = new StringBuilder();
                // Add one to skip the current null byte
                thisOffset = currOffset + 1;
            }
            else {
                builder.append((char)nextChar);
            }

            currOffset++;
        }

        // Ensure that we're at the end of the file at this point.
        if (inputStream.read() != -1) {
            throw new RuntimeException("Not at EOF");
        }

        return retVal;
    }

    private <T> List<T> readMultiple(Function<InputStream, T> reader, int count, InputStream inputStream) {
        var retVal = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
            var newValue = reader.apply(inputStream);
            retVal.add(newValue);
        }
        return retVal;
    }

    private long readUint(InputStream inputStream) {
        var bytes = new long[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        long value = bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
        return value;
    }

    private int readInt(InputStream inputStream) {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int value = bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
        return value;
    }

    private float readFloat(InputStream inputStream) {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        var intBits = bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
        var retVal = Float.intBitsToFloat(intBits);
        return retVal;
    }

    private DbcLocaleString readLocaleString(InputStream inputStream, Map<Long, String> stringMap) {
        var data = new EnumMap<DbcLocale, String>(DbcLocale.class);
        for (DbcLocale locale : DbcLocale.values()) {
            long stringMapIndex = readUint(inputStream);
            String stringValue = stringMap.get(stringMapIndex);
            if (stringValue == null) {
                throw new RuntimeException("Unable to find string for offset: " + stringMapIndex);
            }
            data.put(locale, stringValue);
        }

        var flags = readUint(inputStream);

        return new DbcLocaleString(data, flags);
    }

}
