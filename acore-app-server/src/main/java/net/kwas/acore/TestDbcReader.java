package net.kwas.acore;

import net.kwas.acore.dbc.model.record.ItemDbc;
import net.kwas.acore.dbc.model.record.ItemDisplayInfoDbc;
import net.kwas.acore.dbc.model.record.SpellDbc;
import net.kwas.acore.dbc.model.record.SpellIconDbc;
import net.kwas.acore.dbc.reader.DbcReader;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TestDbcReader {

    public static void main(String[] args) throws Exception {
        var dirName = "/Users/dkwasny/Desktop/dbc";
        var dirPath = Paths.get(dirName);

        var reader = new DbcReader(dirPath);

        var spells = reader.readDbc(SpellDbc.class);
        printList(spells);

        var spellIcons = reader.readDbc(SpellIconDbc.class);
        printList(spellIcons);

        var items = reader.readDbc(ItemDbc.class);
        printList(items);

        var itemDisplayInfos = reader.readDbc(ItemDisplayInfoDbc.class);
        printList(itemDisplayInfos);

        var blizzard = spells.stream().filter(x -> x.id == 10).findFirst().get();
        var blizzardIconId = blizzard.spellIconId;
        var blizzardIcon = spellIcons.stream().filter(x -> x.id == blizzardIconId).findFirst().get();
        System.out.println(blizzard + "\n" + blizzardIconId + "\n" + blizzardIcon);
    }

    private static void printList(List<?> list) {
        System.out.println(list.stream().limit(20).map(Object::toString).collect(Collectors.joining("\n")));
    }

}
