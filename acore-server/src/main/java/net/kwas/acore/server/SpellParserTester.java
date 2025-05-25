package net.kwas.acore.server;

import net.kwas.acore.antlr.SpellDescriptionVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellDescriptionVariables;
import net.kwas.acore.dbc.model.record.DbcSpellDuration;
import net.kwas.acore.dbc.model.record.DbcSpellRadius;
import net.kwas.acore.dbc.model.record.DbcSpellRange;
import net.kwas.acore.dbc.reader.DbcReader;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SpellParserTester {

    private static final long TEST_SPELL_ID = 10;
    private static final SpellDescriptionVisitor VISITOR = new SpellDescriptionVisitor();

    public static void main(String[] args) {
        var spellInfoMap = getSpellInfoMap();

        var variableMap = parseVariables();

        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var dbcSpells = dbcReader.readDbc(DbcSpell.class);

        for (var dbcSpell : dbcSpells) {
            var variables = variableMap.get((long)dbcSpell.descriptionVariablesId);
            var description = parseDescription(dbcSpell, variables, spellInfoMap);
        }

        System.out.println("");
    }


    private static Map<Long, Map<String, NumberResolver>> parseVariables() {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var spellDescriptionVariableSets = dbcReader.readDbc(DbcSpellDescriptionVariables.class);

        var retVal = new HashMap<Long, Map<String, NumberResolver>>();

        for (var spellDescriptionVariableSet : spellDescriptionVariableSets) {
            var id = spellDescriptionVariableSet.id;
            var rawText = spellDescriptionVariableSet.variable;
            var descLexer = new SpellDescriptionLexer(CharStreams.fromString(rawText));
            var descTokens = new CommonTokenStream(descLexer);
            var descParser = new SpellDescriptionParser(descTokens);

            descParser.removeErrorListeners();
            descParser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    System.out.println("VARIABLE ERROR " + id + ": " + msg);
                    System.out.println("TEXT: " + rawText);
                    System.out.println("-----------------------");
                }
            });

            var variablesTree = descParser.spellDescriptionVariables();
            var resolvers = VISITOR.parseSpellDescriptionVariables(variablesTree);

            retVal.put(id, resolvers);
        }

        return retVal;
    }

    private static String parseDescription(
        DbcSpell dbcSpell,
        Map<String, NumberResolver> variableMap,
        Map<Long, SpellInfo> spellInfoMap
    ) {
        var rawText = dbcSpell.description0;

        if (rawText == null || rawText.isEmpty()) {
            return "";
        }

        var descLexer = new SpellDescriptionLexer(CharStreams.fromString(rawText));
        var descTokens = new CommonTokenStream(descLexer);
        var descParser = new SpellDescriptionParser(descTokens);

        descParser.removeErrorListeners();
        descParser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                System.out.println("DESCRIPTION ERROR " + dbcSpell.name0 + " (" + dbcSpell.id + "): " + msg);
                System.out.println("TEXT: " + rawText);
                System.out.println("-----------------------");
            }
        });

        if (dbcSpell.id == TEST_SPELL_ID) {
            System.out.println("START");
        }

        var descTree = descParser.spellDescription();
        var description = VISITOR.parseSpellDescription(descTree);

        var ctx = new SpellContext(
            dbcSpell.id,
            spellInfoMap,
            new CharacterInfo(
                20,
                "Male",
                20,
                100,
                110,
                120,
                10,
                20,
                1.0f,
                false,
                Set.of(50L),
                "HearthstoneLocation"
            ),
            variableMap
        );

        var output = description.resolveString(ctx);

        if (dbcSpell.id == TEST_SPELL_ID) {
            System.out.println(output);
        }

        return output;
    }

    private static Map<Long, SpellInfo> getSpellInfoMap() {
        var radiusMap = getRadiusMap();
        var rangeMap = getRangeMap();
        var durationMap = getDurationMap();

        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var spells = dbcReader.readDbc(DbcSpell.class);
        return spells.stream()
            .collect(Collectors.toMap(
                x -> x.id,
                x -> createSpellInfo(x, radiusMap, rangeMap, durationMap)
            ));
    }

    private static List<Float> getRadii(DbcSpell dbcSpell, Map<Long, DbcSpellRadius> radiusMap) {
        return List.of(
            getRadius(dbcSpell.effectRadiusIndex0, radiusMap),
            getRadius(dbcSpell.effectRadiusIndex1, radiusMap),
            getRadius(dbcSpell.effectRadiusIndex2, radiusMap)
        );
    }

    private static float getRadius(long index, Map<Long, DbcSpellRadius> radiusMap) {
        var dbcSpellRadius = radiusMap.get(index);
        var retVal = 0.0f;
        if (dbcSpellRadius != null) {
            retVal = dbcSpellRadius.radius;
        }
        return retVal;
    }

    private static SpellInfo createSpellInfo(
        DbcSpell dbcSpell,
        Map<Long, DbcSpellRadius> radiusMap,
        Map<Long, DbcSpellRange> rangeMap,
        Map<Long, DbcSpellDuration> durationMap
    ) {
        var radii = getRadii(dbcSpell, radiusMap);

        var dbcRange = rangeMap.get(dbcSpell.rangeIndex);
        var minRanges = List.of(
            dbcRange != null ? dbcRange.rangeMin0 : 0,
            dbcRange != null ? dbcRange.rangeMin1 : 0
        );
        var maxRanges = List.of(
            dbcRange != null ? dbcRange.rangeMax0 : 0,
            dbcRange != null ? dbcRange.rangeMax1 : 0
        );

        var dbcSpellDuration = durationMap.get(dbcSpell.durationIndex);

        return new SpellInfo(
            List.of(dbcSpell.effectBasePoints0, dbcSpell.effectBasePoints1, dbcSpell.effectBasePoints2),
            List.of(dbcSpell.effectDieSides0, dbcSpell.effectDieSides1, dbcSpell.effectDieSides2),
            List.of(dbcSpell.effectRealPointsPerLevel0, dbcSpell.effectRealPointsPerLevel1, dbcSpell.effectRealPointsPerLevel2),
            List.of(dbcSpell.effectAuraPeriod0, dbcSpell.effectAuraPeriod1, dbcSpell.effectAuraPeriod2),
            List.of(dbcSpell.effectChainTargets0, dbcSpell.effectChainTargets0, dbcSpell.effectChainTargets0),
            radii,
            List.of(dbcSpell.effectMiscValue0, dbcSpell.effectMiscValue1, dbcSpell.effectMiscValue2),
            List.of(dbcSpell.effectPointsPerCombo0, dbcSpell.effectPointsPerCombo1, dbcSpell.effectPointsPerCombo2),
            List.of(dbcSpell.effectAmplitude0, dbcSpell.effectAmplitude1, dbcSpell.effectAmplitude2),
            List.of(dbcSpell.effectChainAmplitude0, dbcSpell.effectChainAmplitude1, dbcSpell.effectChainAmplitude2),
            minRanges,
            maxRanges,
            dbcSpellDuration != null ? dbcSpellDuration.duration : 0,
            dbcSpellDuration != null ? dbcSpellDuration.durationPerLevel : 0,
            dbcSpellDuration != null ? dbcSpellDuration.maxDuration : 0,
            dbcSpell.procChance,
            dbcSpell.procCharges,
            dbcSpell.maxTargets,
            dbcSpell.maxTargetLevel,
            dbcSpell.cumulativeAura
        );
    }

    private static Map<Long, DbcSpellRadius> getRadiusMap() {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var values = dbcReader.readDbc(DbcSpellRadius.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

    private static Map<Long, DbcSpellRange> getRangeMap() {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var values = dbcReader.readDbc(DbcSpellRange.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

    private static Map<Long, DbcSpellDuration> getDurationMap() {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var values = dbcReader.readDbc(DbcSpellDuration.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

}
