package net.kwas.acore;

import net.kwas.acore.dbc.model.record.DbcAreaTable;
import net.kwas.acore.dbc.model.record.DbcChrClasses;
import net.kwas.acore.dbc.model.record.DbcChrRaces;
import net.kwas.acore.dbc.model.record.DbcCreatureDisplayInfo;
import net.kwas.acore.dbc.model.record.DbcItem;
import net.kwas.acore.dbc.model.record.DbcItemClass;
import net.kwas.acore.dbc.model.record.DbcItemDisplayInfo;
import net.kwas.acore.dbc.model.record.DbcItemSubClass;
import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellIcon;
import net.kwas.acore.dbc.reader.DbcReader;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TestDbcReader {

    public static void main(String[] args) throws Exception {
        var dirName = "/Users/dkwasny/Desktop/dbc";
        var dirPath = Paths.get(dirName);

        var reader = new DbcReader(dirPath);

//        var spells = reader.readDbc(DbcSpell.class);
//        printList(spells);
//
//        var spellIcons = reader.readDbc(DbcSpellIcon.class);
//        printList(spellIcons);
//
//        var items = reader.readDbc(DbcItem.class);
//        printList(items);
//
//        var itemDisplayInfos = reader.readDbc(DbcItemDisplayInfo.class);
//        printList(itemDisplayInfos);
//
//        var itemClasses = reader.readDbc(DbcItemClass.class);
//        printList(itemClasses);
//
//        var itemSubClasses = reader.readDbc(DbcItemSubClass.class);
//        printList(itemSubClasses);

//        var creatureDisplayInfos = reader.readDbc(DbcCreatureDisplayInfo.class);
//        printList(creatureDisplayInfos);

//        var chrRaces = reader.readDbc(DbcChrRaces.class);
//        printList(chrRaces);
//
//        var chrClasses = reader.readDbc(DbcChrClasses.class);
//        printList(chrClasses);

        var areaTables = reader.readDbc(DbcAreaTable.class);
        printList(areaTables);
    }

    private static void printList(List<?> list) {
        System.out.println(list.stream().limit(20).map(Object::toString).collect(Collectors.joining("\n")));
    }

}
