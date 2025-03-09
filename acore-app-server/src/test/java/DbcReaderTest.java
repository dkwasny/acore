import net.kwas.acore.dbc.reader.DbcReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DbcReaderTest {

    private Path dbcDir;

    @BeforeEach
    public void before() throws IOException {
        dbcDir = Files.createTempDirectory("DbcReaderTest");
        dbcDir.toFile().deleteOnExit();
    }

    @Test
    public void testReadDbc_happyPath() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(3, 4, 16, 13));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // Record 2
        byteList.addAll(toBytes(10L));
        byteList.addAll(toBytes(20));
        byteList.addAll(toBytes(3.14F));
        byteList.addAll(toBytes(6));

        // Record 3
        byteList.addAll(toBytes(5L));
        byteList.addAll(toBytes(-10));
        byteList.addAll(toBytes(-5.1F));
        byteList.addAll(toBytes(12));

        // String Block
        byteList.addAll(toBytes("12345\0abcde\0\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);
        var actual = dbcReader.readDbc(TestRecordDbc.class);
        var expected = List.of(
            new TestRecordDbc(1L, 2, 3.0F, "12345"),
            new TestRecordDbc(10L, 20, 3.14F, "abcde"),
            new TestRecordDbc(5L, -10, -5.1F, "")
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testReadDbc_simpleCase() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);
        var actual = dbcReader.readDbc(TestRecordDbc.class);
        var expected = List.of(
            new TestRecordDbc(1L, 2, 3.0F, "12345")
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testReadDbc_badMagicValue() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDNO"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_badRecordSize() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 200, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_stringBlockTooLarge() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0asdfasdfasdfasdf"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_stringBlockTooSmall() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("1\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_brokenRecordClass() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList, "TestBrokenRecord.dbc");
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestBrokenRecordDbc.class));
    }

    @Test
    public void testReadDbc_incorrectStringOffset() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(2));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_recordCountTooLarge() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1000, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestRecordDbc.class));
    }

    @Test
    public void testReadDbc_missingFile() throws IOException {
        var byteList = new ArrayList<Byte>();

        // Header
        byteList.addAll(toBytes("WDBC"));
        byteList.addAll(toBytes(1, 4, 16, 6));

        // Record 1
        byteList.addAll(toBytes(1L));
        byteList.addAll(toBytes(2));
        byteList.addAll(toBytes(3.0F));
        byteList.addAll(toBytes(0));

        // String Block
        byteList.addAll(toBytes("12345\0"));

        toFile(byteList);
        var dbcReader = new DbcReader(dbcDir);

        Assertions.assertThrows(RuntimeException.class, () -> dbcReader.readDbc(TestBrokenRecordDbc.class));
    }

    private Path toFile(List<Byte> bytes) throws IOException {
        return toFile(bytes, "TestRecord.dbc");
    }

    private Path toFile(List<Byte> bytes, String filename) throws IOException {
        var outputPath = dbcDir.resolve(filename);

        try (var outputStream = Files.newOutputStream(outputPath)) {
            for (Byte myByte : bytes) {
                outputStream.write(myByte);
            }
        }

        outputPath.toFile().deleteOnExit();
        return outputPath;
    }

    private List<Byte> toBytes(int... values) {
        var retVal = new ArrayList<Byte>();
        for (int value : values) {
            var valueList = List.of(
                (byte) (value),
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24)
            );
            retVal.addAll(valueList);
        }
        return retVal;
    }

    private List<Byte> toBytes(long... values) {
        var retVal = new ArrayList<Byte>();
        for (long value : values) {
            var valueList = List.of(
                (byte) (value),
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24)
            );
            retVal.addAll(valueList);
        }
        return retVal;
    }

    private List<Byte> toBytes(float... values) {
        var retVal = new ArrayList<Byte>();
        for (float value : values) {
            var intBits = Float.floatToIntBits(value);
            var valueList = List.of(
                (byte)(intBits),
                (byte)(intBits >>> 8),
                (byte)(intBits >>> 16),
                (byte)(intBits >>> 24)
            );
            retVal.addAll(valueList);
        }
        return retVal;
    }

    private List<Byte> toBytes(String... values) {
        var retVal = new ArrayList<Byte>();
        for (String value : values) {
            for (byte myByte : value.getBytes()) {
                retVal.add(myByte);
            }
        }
        return retVal;
    }

}
