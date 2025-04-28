package net.kwas.acore.dbc;

import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellDuration;
import net.kwas.acore.dbc.reader.DbcReader;

import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestDbcReader {

    private static final String TAG_REGEX = "\\$[^+*/\\-{} .]*";
    private static final Pattern TAG_PATTERN = Pattern.compile(TAG_REGEX);

    public static void main(String[] args) throws Exception {
        var dirName = "/Users/dkwasny/Desktop/dbc";
        var dirPath = Paths.get(dirName);

        var reader = new DbcReader(dirPath);

//        var spells = reader.readDbc(DbcSpell.class);
//        printList(spells.stream().filter(x -> x.id == 25771L).collect(Collectors.toUnmodifiableList()));
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

//        var areaTables = reader.readDbc(DbcAreaTable.class);
//        printList(areaTables);

//        var spellDescriptionVariables = reader.readDbc(DbcSpellDescriptionVariables.class);
//        printList(spellDescriptionVariables);

//        var spellDurations = reader.readDbc(DbcSpellDuration.class);
//        printList(spellDurations.stream().filter(x -> x.id == 4).collect(Collectors.toUnmodifiableList()));

        var spellIds = Set.of(53L, 587L, 597L, 120L, 27650L);
        var spells = reader.readDbc(DbcSpell.class);

        printList(spells.stream().filter(x -> spellIds.contains(x.id)).map(x -> new AbstractMap.SimpleImmutableEntry<>(x.id, x.description0)).collect(Collectors.toList()));

        var allTags = spells.stream()
            .flatMap(x -> getTags(x.description0).stream())
            .collect(Collectors.toSet())
            .stream()
            .sorted()
            .collect(Collectors.toList());
        printAll(allTags);

        var tagsPerSpell = spells.stream()
            .map(x -> new AbstractMap.SimpleImmutableEntry<>(x.id, getTags(x.description0)))
            .filter(x -> !x.getValue().isEmpty())
            .collect(Collectors.toUnmodifiableList());
        printAll(tagsPerSpell);
    }

    private static List<String> getTags(String string) {
        var matcher = TAG_PATTERN.matcher(string);
        return matcher.results()
            .map(x -> x.group())
            .collect(Collectors.toList());
    }

    private static void printList(Collection<?> list) {
        System.out.println(list.stream().limit(20).map(Object::toString).collect(Collectors.joining("\n")));
    }

    private static void printAll(Collection<?> list) {
        System.out.println(list.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

}
