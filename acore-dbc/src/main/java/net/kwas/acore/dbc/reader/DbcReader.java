package net.kwas.acore.dbc.reader;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcHeader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DbcReader(Path dbcFolder) {

    // Every DBC file should start with the bytes "WDBC"
    private static final long MAGIC_VALUE = 'W' | 'D' << 8 | 'B' << 16 | 'C' << 24;

    // Four bytes for the magic value described above
    private static final long MAGIC_OFFSET = 4L;

    // One byte for the magic value and four for the header itself.
    private static final long HEADER_OFFSET = 5L * 4L;

    public <T> List<T> readDbc(Class<T> recordType) {
        try {
            return readDbcChecked(recordType);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> readDbcChecked(Class<T> recordType) throws Exception {
        var fileName = getFilename(recordType);

        if (!checkMagicValue(fileName)) {
            throw new RuntimeException("Invalid magic value at start of file: " + fileName);
        }

        var header = getHeader(fileName);

        if (!checkRecordSize(recordType, header)) {
            throw new RuntimeException("Invalid record size '" + header.recordSize() + "' for record type: " + recordType);
        }

        var stringMap = getStringMap(fileName, header);
        return getRecords(recordType, fileName, header, stringMap);
    }

    private InputStream newBufferedStream(Path filename) throws IOException {
        return new BufferedInputStream(Files.newInputStream(filename));
    }

    private <T> Path getFilename(Class<T> recordType) {
        var className = recordType.getSimpleName();
        var fileName = className.replaceAll("^Dbc", "");
        return dbcFolder.resolve(fileName + ".dbc");
    }

    private boolean checkMagicValue(Path fileName) throws IOException {
        try (var inputStream = newBufferedStream(fileName)) {
            var magicValue = readUint(inputStream);
            return MAGIC_VALUE == magicValue;
        }
    }

    private DbcHeader getHeader(Path fileName) throws IOException {
        try (var inputStream = newBufferedStream(fileName)) {
            return getHeader(inputStream);
        }
    }

    private DbcHeader getHeader(InputStream inputStream) throws IOException {
        long skipped = inputStream.skip(MAGIC_OFFSET);
        if (skipped != MAGIC_OFFSET) {
            throw new RuntimeException("Incorrect amount of bytes skipped: " + skipped);
        }


        long recordCount = readUint(inputStream);
        long fieldCount = readUint(inputStream);
        long recordSize = readUint(inputStream);
        long stringBlockSize = readUint(inputStream);

        return new DbcHeader(recordCount, fieldCount, recordSize, stringBlockSize);
    }

    private <T> boolean checkRecordSize(Class<T> recordType, DbcHeader header) {
        var fields = recordType.getFields();

        int totalSize = 0;
        for (var field : fields) {
            var annotation = getDbcField(field);
            totalSize += annotation.value().getSize();
        }

        return totalSize == header.recordSize();
    }

    private <T> List<T> getRecords(Class<T> recordType, Path filePath, DbcHeader header, Map<Long, String> stringMap) throws Exception {
        try (var inputStream = newBufferedStream(filePath)) {
            return getRecords(recordType, inputStream, header, stringMap);
        }
    }

    private <T> List<T> getRecords(Class<T> recordType, InputStream inputStream, DbcHeader header, Map<Long, String> stringMap) throws Exception {
        long skipped = inputStream.skip(HEADER_OFFSET);
        if (skipped != HEADER_OFFSET) {
            throw new RuntimeException("Incorrect amount of bytes skipped: " + skipped);
        }

        var retVal = new ArrayList<T>();

        var fields = recordType.getFields();
        var constructor = recordType.getConstructor();

        for (int i = 0; i < header.recordCount(); i++) {
            var newRecord = constructor.newInstance();

            for (var field : fields) {
                var dbcField = getDbcField(field);
                var type = dbcField.value();

                var inputValue = switch (type) {
                    case UINT32 -> readUint(inputStream);
                    case INT32 -> readInt(inputStream);
                    case FLOAT -> readFloat(inputStream);
                    case STRING -> readString(inputStream, stringMap);
                };

                field.set(newRecord, inputValue);
            }

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
        long stringStartOffset = currOffset;
        var builder = new StringBuilder();
        while (currOffset < header.stringBlockSize()) {

            int nextChar = inputStream.read();

            if (nextChar == -1) {
                throw new RuntimeException("Unexpected EOF when reading string block");
            }
            else if (nextChar == 0) {
                retVal.put(stringStartOffset, builder.toString());
                builder = new StringBuilder();
                // Add one to skip the current null byte
                stringStartOffset = currOffset + 1;
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

    private DbcField getDbcField(Field field) {
        var annotation = field.getAnnotation(DbcField.class);

        if (annotation == null) {
            throw new RuntimeException("Unable to find DbcField annotation on field: " + field);
        }

        return annotation;
    }

    private long readUint(InputStream inputStream) throws IOException {
        var bytes = new long[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = inputStream.read();
        }

        return bytes[0] | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
    }

    private int readInt(InputStream inputStream) throws IOException {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = inputStream.read();
        }

        return bytes[0] | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
    }

    private float readFloat(InputStream inputStream) throws IOException {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = inputStream.read();
        }

        var intBits = bytes[0] | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24;
        return Float.intBitsToFloat(intBits);
    }

    private String readString(InputStream inputStream, Map<Long, String> stringMap) throws IOException {
        long stringMapIndex = readUint(inputStream);
        String stringValue = stringMap.get(stringMapIndex);
        if (stringValue == null) {
            throw new RuntimeException("Unable to find string for offset: " + stringMapIndex);
        }
        return stringValue;
    }

}
