package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionBaseListener;
import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class AntlrPlayground {

    private static class MyListener extends SpellDescriptionBaseListener {

        private StringBuilder output = null;

        @Override
        public void enterDesc(SpellDescriptionParser.DescContext ctx) {
            output = new StringBuilder();
        }

        @Override
        public void enterText(SpellDescriptionParser.TextContext ctx) {
            output.append(ctx.getText());
        }
        
    }

    private static final String INPUT = "Backstab the target, causing $m2% weapon damage plus ${$m1*1.5} to the target.  Must be behind the target.  Requires a dagger in the main hand.  Awards $s3 combo $lpoint:points;.";

    public static void main(String[] args) {

        var lexer = new SpellDescriptionLexer(CharStreams.fromString(INPUT));
        var tokens = new CommonTokenStream(lexer);
        var parser = new SpellDescriptionParser(tokens);
        var tree = parser.desc();

        var listener = new MyListener();

        var walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        System.out.println(tree.toStringTree(parser));
        System.out.println(listener.output.toString());
    }

}
