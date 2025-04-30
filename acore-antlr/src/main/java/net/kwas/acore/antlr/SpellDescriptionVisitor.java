package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionBaseVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.math.AdditionResolver;
import net.kwas.acore.antlr.resolver.math.DivisionResolver;
import net.kwas.acore.antlr.resolver.function.MaxNumberResolver;
import net.kwas.acore.antlr.resolver.math.MultiplicationResolver;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StaticNumberResolver;
import net.kwas.acore.antlr.resolver.StaticStringResolver;
import net.kwas.acore.antlr.resolver.StringConcatenationResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.math.SubtractionResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpellDescriptionVisitor extends SpellDescriptionBaseVisitor<List<StringResolver>> {

    private final SpellContext spellCtx;

    public SpellDescriptionVisitor(SpellContext spellCtx) {
        this.spellCtx = spellCtx;
    }

    @Override
    public List<StringResolver> visitSpellDescription(SpellDescriptionParser.SpellDescriptionContext ctx) {
        var children = super.visitSpellDescription(ctx);
        return List.of(new StringConcatenationResolver(children));
    }

    @Override
    public List<StringResolver> visitExpression(SpellDescriptionParser.ExpressionContext ctx) {
        if (ctx.text != null && !ctx.text.isEmpty()) {
            var text = ctx.text.stream().map(x -> x.getText()).collect(Collectors.joining(""));
            return List.of(new StaticStringResolver(text));
        }
        else {
            return super.visitExpression(ctx);
        }
    }

    @Override
    public List<StringResolver> visitFormulaFragment(SpellDescriptionParser.FormulaFragmentContext ctx) {
//        // Number constants
//        if (ctx.number() != null) {
//            // Going from integer to double feels silly, but it's just easier
//            // having a common number type for now.  Revisit if this becomes
//            // annoying.
//            var numberValue = Double.parseDouble(ctx.NUMBER().getText());
//            return List.of(new StaticNumberResolver(numberValue));
//        }

        var children = super.visitFormulaFragment(ctx);

        // Passthrough cases
        if (ctx.formulaReference() != null) {
            return children;
        }
        else if (ctx.number() != null) {
            return children;
        }
        else if (ctx.formulaFunction() != null) {
            return children;
        }
        else if (ctx.OPEN_PAREN() != null && ctx.CLOSE_PAREN() != null) {
            return children;
        }

        // Only remaining usecases are math operations that expect two children.
        if (children.size() != 2) {
            throw new RuntimeException("Unexpected amount of children for math operation");
        }

        var left = (NumberResolver)children.get(0);
        var right = (NumberResolver)children.get(1);

        if (ctx.ADDITION() != null) {
            return List.of(new AdditionResolver(left, right));
        }
        else if (ctx.SUBTRACTION() != null) {
            return List.of(new SubtractionResolver(left, right));
        }
        else if (ctx.MULTIPLICATION() != null) {
            return List.of(new MultiplicationResolver(left, right));
        }
        else if (ctx.DIVISION() != null) {
            return List.of(new DivisionResolver(left, right));
        }

        throw new RuntimeException("Unexpected usecase for formula fragment: " + this);
    }

    @Override
    public List<StringResolver> visitMultiplier(SpellDescriptionParser.MultiplierContext ctx) {
        // TODO NEEDS TO BE <baseValue> + <1 for die count 1> at least
        // Need to incorporate spell power and coefficient or something.....
        // Who knows what to do if die count is > 1...
//        var spellId = Integer.parseInt(ctx.spellId.getText());
//        var index = Integer.parseInt(ctx.index.getText());
//        var value = spellCtx.multipliers().get(spellId).get(index);
        return List.of(new StaticNumberResolver(10.0));
    }

    @Override
    public List<StringResolver> visitSpellEffect(SpellDescriptionParser.SpellEffectContext ctx) {
        // TODO Need to use lookups
//        var spellId = Double.parseDouble(ctx.spellId.getText());
        var index = Double.parseDouble(ctx.index.getText());
        var value = index * 20.0;
        return List.of(new StaticNumberResolver(value));
    }

    @Override
    public List<StringResolver> visitNumber(SpellDescriptionParser.NumberContext ctx) {
        var number = Double.parseDouble(ctx.getText());
        return List.of(new StaticNumberResolver(number));
    }

    @Override
    public List<StringResolver> visitLocalizedString(SpellDescriptionParser.LocalizedStringContext ctx) {
        var children = super.visitLocalizedString(ctx);
        // TODO Need to align with previously resolved number
        return List.of(children.getFirst());
    }

    @Override
    public List<StringResolver> visitWord(SpellDescriptionParser.WordContext ctx) {
        var text = ctx.getText();
        return List.of(new StaticStringResolver(text));
    }

    @Override
    public List<StringResolver> visitVariableReference(SpellDescriptionParser.VariableReferenceContext ctx) {
        // TODO: Grab resolvers from already created set from spelldescriptionvariables
        // Might want to pass a string resolver to the lookup resolver and resolve the name every time??
        // Not really necessary, but retains the spirit of modularity??
        var text = ctx.word().getText();
        return List.of(new StaticNumberResolver(40.0));
    }

    @Override
    public List<StringResolver> visitMax(SpellDescriptionParser.MaxContext ctx) {
        var left = getNumberResolver(ctx.left.accept(this));
        var right = getNumberResolver(ctx.right.accept(this));
        return List.of(new MaxNumberResolver(left, right));
    }

    @Override
    protected List<StringResolver> defaultResult() {
        return List.of();
    }

    @Override
    protected List<StringResolver> aggregateResult(List<StringResolver> aggregate, List<StringResolver> nextResult) {
        var retVal = new ArrayList<StringResolver>();
        retVal.addAll(aggregate);
        retVal.addAll(nextResult);
        return retVal;
    }

    private NumberResolver getNumberResolver(List<StringResolver> resolvers) {
        if (resolvers.size() != 1) {
            throw new RuntimeException("Unexpected number of resolvers: " + resolvers);
        }

        return (NumberResolver)resolvers.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpellDescriptionVisitor that = (SpellDescriptionVisitor) o;
        return Objects.equals(spellCtx, that.spellCtx);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(spellCtx);
    }

    @Override
    public String toString() {
        return "SpellDescriptionVisitor{" +
            "spellCtx=" + spellCtx +
            '}';
    }
}
