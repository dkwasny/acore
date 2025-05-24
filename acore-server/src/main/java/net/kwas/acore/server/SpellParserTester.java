package net.kwas.acore.server;

import net.kwas.acore.antlr.SpellDescriptionVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.dbc.model.record.DbcSpell;
import net.kwas.acore.dbc.reader.DbcReader;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SpellParserTester {

    private static final SpellDescriptionVisitor VISITOR = new SpellDescriptionVisitor();

    public static void main(String[] args) {
        var dbcReader = new DbcReader(Path.of("/Users/dkwasny/Files/dbc"));
        var spells = dbcReader.readDbc(DbcSpell.class);

        var outputs = spells.stream()
            .map(SpellParserTester::parseDescription)
            .toList();

//        System.out.println(outputs.stream().collect(Collectors.joining("\n")));
    }

    private static String parseDescription(DbcSpell dbcSpell) {
        var rawText = dbcSpell.description0;

        // Spell 57861 contains a stray dollar sign, which is mucking things up ATM.
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
                System.out.println("ERROR " + dbcSpell.name0 + " (" + dbcSpell.id + "): " + msg);
                System.out.println("TEXT: " + rawText);
                System.out.println("-----------------------");
//                throw new RuntimeException();
            }
        });

        var descTree = descParser.spellDescription();
//        var description = VISITOR.parseSpellDescription(descTree);

        return rawText;

//        var context = new SpellContext(
//            5L,
//            Map.of(),
//            new CharacterInfo(
//                1L,
//                "Male",
//                Set.of()
//            ),
//            Map.of()
//        );
//
//        var output = description.resolveString(context);
//        return output;
    }

}
