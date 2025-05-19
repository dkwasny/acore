package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionLexer;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AntlrPlayground {

    private static final String INPUT = "Backstab the target 5, causing $m2% weapon damage plus ${$m1*1.5} to the target.  Must be behind the target.  Requires a dagger in the main hand.  Awards $s3 combo $lpoint:points;.  Math is ${(-1 + 2) / (3 + 4 / 5)} and ${ 1+2 } and ${$<mult> + $max( 10.20 ,$max($max(50 , 1),2 ))}.  Actual is $<actual>.  Var1 is $<var1>.";

    private static final String VARIABLE_INPUT = """
        $var1=30-10
        $var2=${$<var1> + 3}
        $actual=$?s50[${$<var2>*10}][${1}]
        $mult=3
        """;

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

        var visitor = new SpellDescriptionVisitor();

        var varLexer = new SpellDescriptionLexer(CharStreams.fromString(VARIABLE_INPUT));
        var varTokens = new CommonTokenStream(varLexer);
        var varParser = new SpellDescriptionParser(varTokens);
        var varTree = varParser.spellDescriptionVariables();
        var variables = visitor.parseSpellDescriptionVariables(varTree);
        System.out.println("VARIABLES: " + variables);

        var ctx = new SpellContext(
            10L,
            Map.of(
                10L, new SpellInfo(
                    List.of(1, 2, 1),
                    List.of(1, 2, 0),
                    List.of(1f, 2f, 3f),
                    List.of(0f, 0f, 0f),
                    List.of(0L, 0L, 0L),
                    20
                )
            ),
            new CharacterInfo(
                30,
                "Male",
                Set.of(50L)
            ),
            variables
        );

        var descLexer = new SpellDescriptionLexer(CharStreams.fromString(INPUT));
        var descTokens = new CommonTokenStream(descLexer);
        var descParser = new SpellDescriptionParser(descTokens);
        var descTree = descParser.spellDescription();
        var description = visitor.parseSpellDescription(descTree);

        System.out.println("DESCRIPTION: " + description.resolveString(ctx));

    }

}
