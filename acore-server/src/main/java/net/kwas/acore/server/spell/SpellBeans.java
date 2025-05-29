package net.kwas.acore.server.spell;

import net.kwas.acore.antlr.SpellDescriptionVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StaticStringResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.model.record.DbcSpellDescriptionVariables;
import net.kwas.acore.dbc.model.record.DbcSpellDuration;
import net.kwas.acore.dbc.model.record.DbcSpellIcon;
import net.kwas.acore.dbc.model.record.DbcSpellRadius;
import net.kwas.acore.dbc.model.record.DbcSpellRange;
import net.kwas.acore.server.dbc.DbcMgr;
import net.kwas.acore.server.util.Icons;
import net.kwas.acore.server.util.Stopwatch;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpellBeans {

    private static final Logger LOG = LoggerFactory.getLogger(SpellBeans.class);

    @Bean
    @Qualifier("SpellSummaryMap")
    public static Map<Long, SpellSummary> createRecordMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("ReadSpells");
        var dbcReader = dbcMgr.getReader();
        var dbcSpells = dbcReader.readDbc(DbcSpell.class);
        var dbcSpellIcons = dbcReader.readDbc(DbcSpellIcon.class);

        var dbcSpellIconMap = dbcSpellIcons.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x.filename));

        var retVal = Collections.synchronizedMap(new LinkedHashMap<Long, SpellSummary>());
        for (var dbcSpell : dbcSpells) {
            var dbcIcon = dbcSpellIconMap.get(dbcSpell.spellIconId);
            var iconUrl = Icons.getIconUrl(dbcIcon);
            var spell = new SpellSummary(
                dbcSpell.id,
                dbcSpell.name0,
                dbcSpell.nameSubtext0,
                dbcSpell.description0,
                iconUrl,
                dbcSpell.descriptionVariablesId
            );
            retVal.put(spell.id(), spell);
        }

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

    @Bean
    @Qualifier("SpellDescriptionMap")
    public static Map<Long, StringResolver> createDescriptionMap(
        @Qualifier("SpellSummaryMap") Map<Long, SpellSummary> spellSummaries
    ) {
        var stopwatch = Stopwatch.start("CreateSpellDescriptionMap");

        var retVal = spellSummaries.values().parallelStream()
            .collect(Collectors.toConcurrentMap(SpellSummary::id, SpellBeans::parseSpellDescription));

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

    private static StringResolver parseSpellDescription(SpellSummary spellSummary) {
        var rawText = spellSummary.rawDescription();

        var visitor = new SpellDescriptionVisitor();
        StringResolver resolver;
        if (rawText == null || rawText.isEmpty()) {
            resolver = new StaticStringResolver("");
        }
        else {
            // TODO Extract parsing code
            // TODO Better error handling...fail fast!
            var descLexer = new SpellDescriptionLexer(CharStreams.fromString(rawText));
            var descTokens = new CommonTokenStream(descLexer);
            var descParser = new SpellDescriptionParser(descTokens);

            descParser.removeErrorListeners();
            descParser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    LOG.error("Spell Description Parser Error '{}' ({}): {}", spellSummary.name(), spellSummary.id(), msg);
                    LOG.error("Spell Description Raw Text ({}): {}", spellSummary.id(), rawText);
                    throw new RuntimeException("Error parsing spell description: " + spellSummary.id());
                }
            });

            var descTree = descParser.spellDescription();
            resolver = visitor.parseSpellDescription(descTree);
        }

        return resolver;
    }

    @Bean
    @Qualifier("SpellDescriptionVariableMap")
    public static Map<Long, Map<String, NumberResolver>> createVariableMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("CreateSpellDescriptionVariableMap");
        var dbcReader = dbcMgr.getReader();
        var dbcSpellDescriptionVariablesList = dbcReader.readDbc(DbcSpellDescriptionVariables.class);

        var retVal = new HashMap<Long, Map<String, NumberResolver>>();

        var visitor = new SpellDescriptionVisitor();
        for (var dbcSpellDescriptionVariables : dbcSpellDescriptionVariablesList) {
            var id = dbcSpellDescriptionVariables.id;
            var rawText = dbcSpellDescriptionVariables.variable;
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

                    LOG.error("Spell Description Variable Parser Error ({}): {}", id, msg);
                    LOG.error("Spell Description Variable Raw Text ({}): {}", id, rawText);
                    throw new RuntimeException("Error parsing spell description variable: " + id);
                }
            });

            var variablesTree = descParser.spellDescriptionVariables();
            var resolvers = visitor.parseSpellDescriptionVariables(variablesTree);

            retVal.put(id, resolvers);
        }

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

    @Bean
    @Qualifier("SpellInfoMap")
    public static Map<Long, SpellInfo> createSpellInfoMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("CreateSpellInfoMap");
        var radiusMap = getRadiusMap(dbcMgr);
        var rangeMap = getRangeMap(dbcMgr);
        var durationMap = getDurationMap(dbcMgr);

        var dbcReader = dbcMgr.getReader();
        var spells = dbcReader.readDbc(DbcSpell.class);
        var retVal = spells.stream()
            .collect(Collectors.toMap(
                x -> x.id,
                x -> createSpellInfo(x, radiusMap, rangeMap, durationMap)
            ));
        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
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

    private static Map<Long, DbcSpellRadius> getRadiusMap(DbcMgr dbcMgr) {
        var dbcReader = dbcMgr.getReader();
        var values = dbcReader.readDbc(DbcSpellRadius.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

    private static Map<Long, DbcSpellRange> getRangeMap(DbcMgr dbcMgr) {
        var dbcReader = dbcMgr.getReader();
        var values = dbcReader.readDbc(DbcSpellRange.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

    private static Map<Long, DbcSpellDuration> getDurationMap(DbcMgr dbcMgr) {
        var dbcReader = dbcMgr.getReader();
        var values = dbcReader.readDbc(DbcSpellDuration.class);
        return values.stream()
            .collect(Collectors.toMap(x -> x.id, x -> x));
    }

}
