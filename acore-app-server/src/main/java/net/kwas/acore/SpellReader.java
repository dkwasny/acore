package net.kwas.acore;

import net.kwas.acore.dbc.model.record.ItemDbc;
import net.kwas.acore.dbc.model.record.SpellDbc;
import net.kwas.acore.dbc.reader.DbcReader;

import java.nio.file.Paths;
import java.util.stream.Collectors;

public class SpellReader {

    public static void main(String[] args) throws Exception {
        var dirName = "/Users/dkwasny/Desktop/dbc";
        var dirPath = Paths.get(dirName);

        var reader = new DbcReader(dirPath);

        var spells = reader.readDbc(SpellDbc.class);
        System.out.println(spells.stream().limit(10).map(Object::toString).collect(Collectors.joining("\n")));

        var items = reader.readDbc(ItemDbc.class);
        System.out.println(items.stream().limit(10).map(Object::toString).collect(Collectors.joining("\n")));

//        var inputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(filePath)));
//
//        if (!checkMagic(inputStream)) {
//            throw new Exception("INVALID MAGIC");
//        }
//
//        long recordCount = readUint(inputStream);
//        long fieldCount = readUint(inputStream);
//        long recordSize = readUint(inputStream);
//        long stringBlockSize = readUint(inputStream);
//
//        System.out.println("Record Count: " + recordCount);
//        System.out.println("Field Count: " + fieldCount);
//        System.out.println("Record Size: " + recordSize);
//        System.out.println("String Block Size: " + stringBlockSize);
//
//        var stringBlockOffset = (4 * 5) + (recordCount * recordSize);
//        System.out.println("String Block Offset: " + HexFormat.of().toHexDigits(stringBlockOffset));
//        var stringMap = getStringMap(filePath, stringBlockOffset, stringBlockSize);
//
//        System.out.println("STRING MAP");
//        System.out.println(stringMap.entrySet().stream().limit(20).map(x -> x.getKey() + " -> " + x.getValue()).collect(Collectors.joining("\n")));
//
//        System.out.println("TEST STRING OUT");
//        System.out.println("0 -> " + stringMap.get(0L));
//        System.out.println("1 -> " + stringMap.get(1L));
//
//        var records = getRecords(SpellDbc.class, filePath, recordCount, recordSize, stringMap);
//        System.out.println("RECORDS");
//        System.out.println(records.stream().limit(10).map(Object::toString).collect(Collectors.joining("\n")));
//
//        System.out.println(records.stream().filter(x -> x.spellName().get().equals("Word of Recall (OLD)")).map(Object::toString).findFirst());
    }





}
