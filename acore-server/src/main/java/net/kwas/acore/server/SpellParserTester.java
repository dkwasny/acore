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

public class SpellParserTester {

    private static final SpellDescriptionVisitor VISITOR = new SpellDescriptionVisitor();

    public static void main(String[] args) {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var spells = dbcReader.readDbc(DbcSpell.class);

        var allVariables = parseVariables();

        var outputs = spells.stream()
            .map(x -> parseDescription(x, allVariables.get((long)x.descriptionVariablesId)))
            .toList();

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

    private static String parseDescription(DbcSpell dbcSpell, Map<String, NumberResolver> variables) {
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

        var descTree = descParser.spellDescription();
        var description = VISITOR.parseSpellDescription(descTree);

        var ctx = new SpellContext(
            10L,
            Map.of(
                10L, new SpellInfo(
                    List.of(1, 2, 1),
                    List.of(1, 2, 0),
                    List.of(1f, 2f, 3f),
                    List.of(1f, 1f, 1f),
                    List.of(1000L, 1000L, 1000L),
                    List.of(2L, 2L, 2L),
                    List.of(10f, 10f, 10f),
                    List.of(5, 5, 5),
                    List.of(1.2f, 1.2f, 1.2f),
                    List.of(1.5f, 1.5f, 1.5f),
                    List.of(0.5f, 0.5f, 0.5f),
                    List.of(2.0f, 2.0f, 2.0f),
                    List.of(50.0f, 50.0f, 50.0f),
                    30000,
                    30,
                    4,
                    6,
                    80,
                    7
                )
            ),
            new CharacterInfo(
                30,
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
            variables
        );

        var output = description.resolveString(ctx);
        return output;
    }

}
