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
import net.kwas.acore.dbc.model.record.DbcSpellRadius;
import net.kwas.acore.dbc.model.record.DbcSpellRange;
import net.kwas.acore.server.dbc.DbcMgr;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpellDescriptionBeans {

    private static final Logger LOG = LoggerFactory.getLogger(SpellDescriptionBeans.class);

    @Bean
    @Qualifier("SpellDescriptionMap")
    public static Map<Long, StringResolver> createSpellDescriptionMap(
        @Qualifier("RawSpellMap") Map<Long, RawSpell> rawSpellMap
    ) {
        var stopwatch = Stopwatch.start("CreateSpellDescriptionMap");

        // Use parallelStream().  The parsing code is CHUNKY.
        var retVal = rawSpellMap.values().parallelStream()
            .collect(Collectors.toConcurrentMap(RawSpell::id, SpellDescriptionBeans::parseSpellDescription));

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

    private static StringResolver parseSpellDescription(RawSpell rawSpell) {
        var rawText = rawSpell.rawDescription();

        var visitor = new SpellDescriptionVisitor();
        StringResolver resolver;
        if (rawText == null || rawText.isEmpty()) {
            resolver = new StaticStringResolver("");
        }
        else {
            var parser = getParser(rawText);
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                    LOG.error("Spell Description Parser Error '{}' ({}): {}", rawSpell.name(), rawSpell.id(), msg);
                    LOG.error("Spell Description Raw Text ({}): {}", rawSpell.id(), rawText);
                    throw new RuntimeException("Error parsing spell description: " + rawSpell.id());
                }
            });

            var descTree = parser.spellDescription();
            resolver = visitor.parseSpellDescription(descTree);
        }

        return resolver;
    }

    @Bean
    @Qualifier("SpellDescriptionVariableMap")
    public static Map<Long, Map<String, NumberResolver>> createSpellDescriptionVariableMap(DbcMgr dbcMgr) {
        var stopwatch = Stopwatch.start("CreateSpellDescriptionVariableMap");
        var dbcReader = dbcMgr.getReader();
        var dbcSpellDescriptionVariablesList = dbcReader.readDbc(DbcSpellDescriptionVariables.class);

        var retVal = dbcSpellDescriptionVariablesList.stream()
            .collect(Collectors.toConcurrentMap(x -> x.id, SpellDescriptionBeans::parseVariables));

        stopwatch.stop();
        return Collections.unmodifiableMap(retVal);
    }

    private static Map<String, NumberResolver> parseVariables(DbcSpellDescriptionVariables dbcSpellDescriptionVariables) {
        var id = dbcSpellDescriptionVariables.id;
        var rawText = dbcSpellDescriptionVariables.variable;

        var parser = getParser(rawText);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                LOG.error("Spell Description Variable Parser Error ({}): {}", id, msg);
                LOG.error("Spell Description Variable Raw Text ({}): {}", id, rawText);
                throw new RuntimeException("Error parsing spell description variable: " + id);
            }
        });

        var variablesTree = parser.spellDescriptionVariables();
        var visitor = new SpellDescriptionVisitor();
        return visitor.parseSpellDescriptionVariables(variablesTree);
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
            .collect(Collectors.toConcurrentMap(
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
            dbcSpell.baseLevel,
            dbcSpell.maxLevel,
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

    private static SpellDescriptionParser getParser(String rawText) {
        var descLexer = new SpellDescriptionLexer(CharStreams.fromString(rawText));
        var descTokens = new CommonTokenStream(descLexer);
        return new SpellDescriptionParser(descTokens);
    }

}
