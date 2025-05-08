package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AntlrPlayground {

    private static final String INPUT = "Backstab the target 5, causing $m2% weapon damage plus ${$m1*1.5} to the target.  Must be behind the target.  Requires a dagger in the main hand.  Awards $s3 combo $lpoint:points;.  Math is ${(-1 + 2) / (3 + 4 / 5)} and ${ 1+2 } and ${$<mult> + $max( 10.20 ,$max($max(50 , 1),2 ))}.  Flat number is 2.5.";

    private static final String CONDITIONAL_INPUT = """
        Absorbs $s1 healing. This is a conditional: $?(s25306|!((!a48165)|a66109))[something returned true, $ghe:she; didn't do anything with $48165s3]?!a66109[something false else something true] [something false else something false, did $s2 mod damage to $ghim:her;] and lasts $d.
        
        funny = $<funny> $lfunny:funnies;
        maybe = $<maybe> $lmaybe:maybes;
        storm = ${$<storm>}.2 $lstorm:storms;
        """;

    private static final String VARIABLES_INPUT = """
        $arctic1=$?s31674[${1.01}][${1}]
        $arctic2=$?s31675[${1.02}][${$<arctic1>}]
        $arctic3=$?s31676[${1.03}][${$<arctic2>}]
        $arctic4=$?s31677[${1.04}][${$<arctic3>}]
        $arctic5=$?s31678[${1.05}][${$<arctic4>}]
        $piercing1=$?s11151[${1.02}][${1}]
        $piercing2=$?s12952[${1.04}][${$<piercing1>}]
        $piercing3=$?s12953[${1.06}][${$<piercing2>}]
        $mult=${$<arctic5>*$<piercing3>}
        """;

    public static void main(String[] args) {

        var spellContext = new SpellContext(
            100L,
            Map.of()
        );
        var visitor = new SpellDescriptionVisitor(spellContext);

        {
            var lexer = new SpellDescriptionLexer(CharStreams.fromString(INPUT));
            var tokens = new CommonTokenStream(lexer);
            var parser = new SpellDescriptionParser(tokens);
            var tree = parser.spellDescription();
            var variables = visitor.parseSpellDescription(tree);
            System.out.println("DESCRIPTION 1: " + variables.resolveString());
        }

        {
            var lexer = new SpellDescriptionLexer(CharStreams.fromString(CONDITIONAL_INPUT));
            var tokens = new CommonTokenStream(lexer);
            var parser = new SpellDescriptionParser(tokens);
            var tree = parser.spellDescription();
            var variables = visitor.parseSpellDescription(tree);
            System.out.println("DESCRIPTION 2: " + variables.resolveString());
        }

        {
            var lexer = new SpellDescriptionLexer(CharStreams.fromString(VARIABLES_INPUT));
            var tokens = new CommonTokenStream(lexer);
            var parser = new SpellDescriptionParser(tokens);
            var tree = parser.spellDescriptionVariables();
            var variables = visitor.parseSpellDescriptionVariables(tree);
            System.out.println("VARIABLES 2: " + variables);
        }

    }

}
