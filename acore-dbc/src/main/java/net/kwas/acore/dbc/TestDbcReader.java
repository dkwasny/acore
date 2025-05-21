package net.kwas.acore.dbc;

import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellDescriptionVariables;
import net.kwas.acore.dbc.model.record.DbcSpellDuration;
import net.kwas.acore.dbc.model.record.DbcSpellRadius;
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

    private static final String FORMULA_REGEX = "\\$\\{.+\\}";
    private static final Pattern FORMULA_PATTERN = Pattern.compile(FORMULA_REGEX);

    public static void main(String[] args) throws Exception {
        var dirName = "/Users/dkwasny/Files/dbc";
        var dirPath = Paths.get(dirName);

        var reader = new DbcReader(dirPath);

//        var spells = reader.readDbc(DbcSpell.class);
//        printList(spells.stream().filter(x -> x.id == 25771L).toList();
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

        var spells = reader.readDbc(DbcSpell.class);

        var relevantSpells = spells.stream()
//            .filter(x -> (x.description0.contains("$o1") && x.effectDieSides0 > 1) || (x.description0.contains("$o2") && x.effectDieSides1 > 1) || (x.description0.contains("$o3") && x.effectDieSides2 > 1))
            .filter(x -> x.id == 1079L)
//            .filter(x -> x.description0.contains("$a"))
//            .filter(x -> x.procChance < 100)
            .toList();



        var spellRadii = reader.readDbc(DbcSpellRadius.class);

        System.out.println("");

        System.out.println(Double.parseDouble("-.5"));

//        var spellDurations = reader.readDbc(DbcSpellDuration.class);
//        printAll(spellDurations);
//        System.out.println();
//
//        var perLevelDurations = spellDurations.stream()
//            .filter(x -> x.durationPerLevel != 0)
//            .toList();
//        printAll(perLevelDurations);
//
//        var perLevelDurationIds = perLevelDurations.stream()
//            .map(x -> x.id)
//            .collect(Collectors.toSet());
//
//        var durationSpells = spells.stream()
//            .filter(x -> perLevelDurationIds.contains(x.durationIndex))
//            .toList();
//        printAll(durationSpells);
//
//
//        var durationSpellStrings = durationSpells.stream()
//            .map(x -> x.id + " -> " + x.durationIndex)
//            .collect(Collectors.toSet())
//            .stream()
//            .sorted()
//            .toList();
//        printAll(durationSpellStrings);

//        var spellIds = Set.of(53L, 587L, 597L, 120L, 27650L, 47772L, 65422L);



//        var ternarySpellId = 69427L;
//        var ternarySpell = spells.stream().filter(x -> x.id == ternarySpellId).toList();
//        System.out.println(ternarySpell.get(0).description0);

//
//
//        var spellId = 116L;
//        var spell = spells.stream().filter(x -> x.id == spellId).toList();
//        System.out.println(spell);
//
//        var spellDescriptionVariablesId = 181L;
        var spellDescriptionVariables = reader.readDbc(DbcSpellDescriptionVariables.class);
//        var spellDescriptionVariable = spellDescriptionVariables.stream().filter(x -> x.id == spellDescriptionVariablesId).toList();
//        System.out.println(spellDescriptionVariable);
//
//        var descSpell = spells.stream().filter(x -> x.descriptionVariablesId == spellDescriptionVariablesId).toList();
//        System.out.println(descSpell.get(0).description0);

//        var allSpellDescriptionVars = spellDescriptionVariables.stream()
//            .map(x -> x.id + " -> " + x.variable)
//            .collect(Collectors.toSet())
//            .stream()
//            .sorted()
//            .collect(Collectors.joining("\n----\n"));
//        System.out.println(allSpellDescriptionVars);

//        var allFormulas = spells.stream()
//            .flatMap(x -> getFormulas(x.description0).stream())
//            .collect(Collectors.toSet())
//            .stream()
//            .sorted()
//            .toList();
//        printAll(allFormulas);

//        printList(spells.stream().filter(x -> spellIds.contains(x.id)).map(x -> new AbstractMap.SimpleImmutableEntry<>(x.id, x.description0)).toList());
//
//        var allTags = spells.stream()
//            .flatMap(x -> getTags(x.description0).stream())
//            .collect(Collectors.toSet())
//            .stream()
//            .sorted()
//            .toList();
//        printAll(allTags);
//
//        var tagsPerSpell = spells.stream()
//            .map(x -> new AbstractMap.SimpleImmutableEntry<>(x.id, getTags(x.description0)))
//            .filter(x -> !x.getValue().isEmpty())
//            .toList();
//        printAll(tagsPerSpell);


    }

    private static List<String> getTags(String string) {
        var matcher = TAG_PATTERN.matcher(string);
        return matcher.results()
            .map(x -> x.group())
            .toList();
    }

    private static List<String> getFormulas(String string) {
        var matcher = FORMULA_PATTERN.matcher(string);
        return matcher.results()
            .map(x -> x.group())
            .toList();
    }

    private static void printList(Collection<?> list) {
        System.out.println(list.stream().limit(20).map(Object::toString).collect(Collectors.joining("\n")));
    }

    private static void printAll(Collection<?> list) {
        System.out.println(list.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

}
