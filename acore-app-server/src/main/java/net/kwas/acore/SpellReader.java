package net.kwas.acore;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SpellReader {

    public static void main(String[] args) throws Exception {
        var filename = "/Users/dkwasny/Desktop/dbc/Spell.dbc";
        var filePath = Paths.get(filename);

        var inputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(filePath)));

        if (!checkMagic(inputStream)) {
            throw new Exception("INVALID MAGIC");
        }

        long recordCount = readUint(inputStream);
        long fieldCount = readUint(inputStream);
        long recordSize = readUint(inputStream);
        long stringBlockSize = readUint(inputStream);

        System.out.println("Record Count: " + recordCount);
        System.out.println("Field Count: " + fieldCount);
        System.out.println("Record Size: " + recordSize);
        System.out.println("String Block Size: " + stringBlockSize);

        var stringBlockOffset = (4 * 5) + (recordCount * recordSize);
        System.out.println("String Block Offset: " + HexFormat.of().toHexDigits(stringBlockOffset));
        var stringMap = getStringMap(filePath, stringBlockOffset, stringBlockSize);

        System.out.println("STRING MAP");
        System.out.println(stringMap.entrySet().stream().limit(20).map(x -> x.getKey() + " -> " + x.getValue()).collect(Collectors.joining("\n")));

        System.out.println("TEST STRING OUT");
        System.out.println("0 -> " + stringMap.get(0L));
        System.out.println("1 -> " + stringMap.get(1L));

        var records = getRecords(SpellDbc.class, filePath, recordCount, recordSize, stringMap);
        System.out.println("RECORDS");
        System.out.println(records.stream().limit(10).map(Object::toString).collect(Collectors.joining("\n")));

        System.out.println(records.stream().filter(x -> x.spellName().get().equals("Word of Recall (OLD)")).map(Object::toString).findFirst());
    }



    private static <T> List<T> getRecords(Class<T> dbcType, Path filePath, long recordCount, long recordSize, Map<Long, String> stringMap) throws Exception {
        if (!dbcType.isRecord()) {
            throw new RuntimeException("NON RECORD TYPE FOUND: " + dbcType);
        }

        var components = dbcType.getRecordComponents();

        var constructorArgs = new ArrayList<Class<?>>();
        for (var component : components) {
            var annotation = component.getAnnotation(DbcField.class);

            if (annotation == null) {
                throw new RuntimeException("CANT FIND ANNOTATION!!");
            }

            constructorArgs.add(component.getType());
        }

        var constructorArgsArray = constructorArgs.toArray(new Class[0]);
        var constructor = dbcType.getConstructor(constructorArgsArray);

        var inputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(filePath)));
        int offset = 4 * 5;
        long skipped = inputStream.skip(offset);
        if (skipped != offset) {
            throw new RuntimeException("UNABLE TO SKIP!");
        }


        var retVal = new ArrayList<T>();

        for (int i = 0; i < recordCount; i++) {
            var currIdx = 0;
            var constructorFields = new Object[components.length];
            for (var component : components) {
                var dbcField = component.getAnnotation(DbcField.class);

                if (dbcField == null) {
                    throw new RuntimeException("CANT FIND ANNOTATION!!");
                }

                var type = dbcField.type();
                var count = dbcField.count();

                var inputValue = switch (type) {
                    case UINT32 -> readMultiple(SpellReader::readUint, count, inputStream);
                    case INT32 -> readMultiple(SpellReader::readInt, count, inputStream);
                    case STRING_REF -> readMultiple(x -> readDbcStringRef(x, stringMap), count, inputStream);
                    case FLOAT -> readMultiple(SpellReader::readFloat, count, inputStream);
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

    private static Map<Long, String> getStringMap(Path filePath, long startOffset, long blockSize) throws Exception {
        var retVal = new HashMap<Long, String>();
        var inputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(filePath)));

        long actualOffset = startOffset;
        long skipped = inputStream.skip(actualOffset);
        if (skipped != actualOffset) {
            throw new RuntimeException("UNABLE TO SKIP!");
        }

        long currOffset = 0;
        long thisOffset = currOffset;
        var builder = new StringBuilder();
        while (currOffset < blockSize) {

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

        if (inputStream.read() != -1) {
            throw new RuntimeException("NOT EOF");
        }

        return retVal;
    }

    private static boolean checkMagic(DataInputStream inputStream) {
        final var expected = 'W' | 'D' << 8 | 'B' << 16 | 'C' << 24;
        var magicValue = readUint(inputStream);
        return expected == magicValue;
    }

    private static <T> List<T> readMultiple(Function<DataInputStream, T> reader, int count, DataInputStream inputStream) {
        var retVal = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
            var newValue = reader.apply(inputStream);
            retVal.add(newValue);
        }
        return retVal;
    }

    private static long readUint(DataInputStream inputStream) {
        var bytes = new long[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        long value = (long)(bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24);
        return value;
    }

    private static int readInt(DataInputStream inputStream) {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int value = (int)(bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24);
        return value;
    }

    private static float readFloat(DataInputStream inputStream) {
        var bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                bytes[i] = inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        float value = (float)(bytes[0] << 0 | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24);
        return value;
    }

    private static DbcStringRef readDbcStringRef(DataInputStream inputStream, Map<Long, String> stringMap) {
        var data = new EnumMap<DbcLocale, String>(DbcLocale.class);
        for (DbcLocale locale : DbcLocale.values()) {
            long stringMapIndex = readUint(inputStream);
            String stringValue = stringMap.get(stringMapIndex);
            if (stringValue == null) {
                throw new RuntimeException("MISSING STRING VALUE AT INDEX: " + stringMapIndex);
            }
            data.put(locale, stringValue);
        }

        var flags = readUint(inputStream);

        return new DbcStringRef(data, flags);
    }

}
