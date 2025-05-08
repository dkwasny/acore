package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionBaseVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.BooleanResolver;
import net.kwas.acore.antlr.resolver.conditional.AndResolver;
import net.kwas.acore.antlr.resolver.conditional.ConditionBranch;
import net.kwas.acore.antlr.resolver.conditional.NumericConditionalResolver;
import net.kwas.acore.antlr.resolver.conditional.StringConditionalResolver;
import net.kwas.acore.antlr.resolver.conditional.ConditionalSpellRefResolver;
import net.kwas.acore.antlr.resolver.conditional.NotResolver;
import net.kwas.acore.antlr.resolver.conditional.OrResolver;
import net.kwas.acore.antlr.resolver.function.GreaterThanResolver;
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
import net.kwas.acore.antlr.resolver.reference.GenderStringResolver;
import net.kwas.acore.antlr.resolver.reference.LocalizedStringResolver;
import net.kwas.acore.antlr.resolver.reference.VariableResolver;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SpellDescriptionVisitor extends SpellDescriptionBaseVisitor<List<StringResolver>> {

    private final SpellContext spellCtx;

    public SpellDescriptionVisitor(SpellContext spellCtx) {
        this.spellCtx = spellCtx;
    }

    // TODO: Figure out how to add top level methods to easily get descriptions and variables (already in map form).
    public StringResolver parseSpellDescription(SpellDescriptionParser.SpellDescriptionContext ctx) {
        var results = visitSpellDescription(ctx);
        return getStringResolver(results);
    }

    public Map<String, NumberResolver> parseSpellDescriptionVariables(SpellDescriptionParser.SpellDescriptionVariablesContext ctx) {
        return ctx.spellDescriptionVariable().stream()
            .map(this::parseSpellDescriptionVariable)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, NumberResolver> parseSpellDescriptionVariable(SpellDescriptionParser.SpellDescriptionVariableContext ctx) {
        var key = ctx.identifier().getText();
        var value = getNumberResolver(ctx.variableDefinition().accept(this));
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    @Override
    public List<StringResolver> visitText(SpellDescriptionParser.TextContext ctx) {
        var children = super.visitText(ctx);
        return List.of(new StringConcatenationResolver(children));
    }

    @Override
    public List<StringResolver> visitMiscChars(SpellDescriptionParser.MiscCharsContext ctx) {
        var value = ctx.getText();
        return List.of(new StaticStringResolver(value));
    }

    @Override
    public List<StringResolver> visitIdentifier(SpellDescriptionParser.IdentifierContext ctx) {
        var text = ctx.getText();
        return List.of(new StaticStringResolver(text));
    }

    @Override
    public List<StringResolver> visitNumber(SpellDescriptionParser.NumberContext ctx) {
        var number = Double.parseDouble(ctx.getText());
        return List.of(new StaticNumberResolver(number));
    }

    @Override
    public List<StringResolver> visitFormulaFragment(SpellDescriptionParser.FormulaFragmentContext ctx) {
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

        // Only remaining usecases are binary operations that expect two children.
        if (children.size() != 2) {
            throw new RuntimeException("Unexpected amount of children for math operation");
        }

        var left = (NumberResolver)children.getFirst();
        var right = (NumberResolver)children.getLast();

        if (ctx.PLUS() != null) {
            return List.of(new AdditionResolver(left, right));
        }
        else if (ctx.HYPHEN() != null) {
            return List.of(new SubtractionResolver(left, right));
        }
        else if (ctx.STAR() != null) {
            return List.of(new MultiplicationResolver(left, right));
        }
        else if (ctx.FORWARD_SLASH() != null) {
            return List.of(new DivisionResolver(left, right));
        }

        throw new RuntimeException("Unexpected usecase for formula fragment: " + ctx);
    }

    @Override
    public List<StringResolver> visitMultiplier(SpellDescriptionParser.MultiplierContext ctx) {
        // TODO NEEDS TO BE <baseValue> + <1 for die count 1> at least
        // Need to incorporate spell power and coefficient or something.....
        // Who knows what to do if die count is > 1...
//        var spellId = Integer.parseInt(ctx.spellId.getText());
//        var index = Integer.parseInt(ctx.index.getText());
//        var value = spellCtx.multipliers().get(spellId).get(index);
        var value = Long.parseLong(ctx.index.getText());
        return List.of(new StaticNumberResolver(10.0));
    }

    @Override
    public List<StringResolver> visitSpellEffect(SpellDescriptionParser.SpellEffectContext ctx) {
        // TODO Need to use lookups
        var index = Long.parseLong(ctx.index.getText());
        return List.of(new StaticNumberResolver(20.0));
    }

    @Override
    public List<StringResolver> visitDuration(SpellDescriptionParser.DurationContext ctx) {
        // TODO actually implement
        return List.of(new StaticNumberResolver(120.0));
    }

    @Override
    public List<StringResolver> visitLocalizedString(SpellDescriptionParser.LocalizedStringContext ctx) {
        var values = ctx.identifier().stream().map(RuleContext::getText).toList();
        return List.of(new LocalizedStringResolver(values));
    }

    @Override
    public List<StringResolver> visitGenderString(SpellDescriptionParser.GenderStringContext ctx) {
        var maleString = ctx.male.getText();
        var femaleString = ctx.female.getText();
        return List.of(new GenderStringResolver(maleString, femaleString));
    }

    @Override
    public List<StringResolver> visitVariableReference(SpellDescriptionParser.VariableReferenceContext ctx) {
        var text = ctx.identifier().getText();
        return List.of(new VariableResolver(text));
    }

    @Override
    public List<StringResolver> visitMax(SpellDescriptionParser.MaxContext ctx) {
        var left = getNumberResolver(ctx.left.accept(this));
        var right = getNumberResolver(ctx.right.accept(this));
        return List.of(new MaxNumberResolver(left, right));
    }

    @Override
    public List<StringResolver> visitGreaterThan(SpellDescriptionParser.GreaterThanContext ctx) {
        var left = getNumberResolver(ctx.left.accept(this));
        var right = getNumberResolver(ctx.right.accept(this));
        return List.of(new GreaterThanResolver(left, right));
    }


    @Override
    public List<StringResolver> visitConditionalSpellRef(SpellDescriptionParser.ConditionalSpellRefContext ctx) {
        var spellId = Long.parseLong(ctx.positiveInteger().getText());
        return List.of(new ConditionalSpellRefResolver(spellId));
    }

    @Override
    public List<StringResolver> visitConditionalFragment(SpellDescriptionParser.ConditionalFragmentContext ctx) {
        var children = super.visitConditionalFragment(ctx);

        // Passthrough cases
        if (ctx.OPEN_PAREN() != null && ctx.CLOSE_PAREN() != null) {
            return children;
        }
        else if (ctx.conditionalSpellRef() != null) {
            return children;
        }

        if (ctx.EXCLAMATION_POINT() != null) {
            var resolver = getBooleanResolver(children);
            return List.of(new NotResolver(resolver));
        }

        // Remaining cases are binary operations that expect two children
        if (children.size() != 2) {
            throw new RuntimeException("Unexpected number of children for conditional fragment: " + children);
        }

        var left = (BooleanResolver)children.getFirst();
        var right = (BooleanResolver)children.getLast();

        if (ctx.AMPERSAND() != null) {
            return List.of(new AndResolver(left, right));
        }
        else if (ctx.PIPE() != null) {
            return List.of(new OrResolver(left, right));
        }

        throw new RuntimeException("Unexpected usecase for conditional fragment: " + ctx);
    }

    // TODO: Maybe find a way to merge visitStringConditional() and visitNumericConditional().
    // It's a bit tricky due to the ANTLR context objects.  Not worth it right now.
    @Override
    public List<StringResolver> visitStringConditional(SpellDescriptionParser.StringConditionalContext ctx) {
        var branches = new ArrayList<ConditionBranch<StringResolver>>();

        var initialBranch = createBranch(
            ctx.stringConditionalIf().conditionalFragment(),
            ctx.stringConditionalIf().text(),
            StringResolver.class
        );
        branches.add(initialBranch);

        var remainingBranches = ctx.stringConditionalElseIf().stream()
            .map(x -> createBranch(
                x.conditionalFragment(),
                x.text(),
                StringResolver.class
            ))
            .toList();
        branches.addAll(remainingBranches);

        var elseCases = ctx.stringConditionalElse().accept(this);
        var elseCase = getStringResolver(elseCases);

        return List.of(new StringConditionalResolver(branches, elseCase));
    }

    @Override
    public List<StringResolver> visitNumericConditional(SpellDescriptionParser.NumericConditionalContext ctx) {
        var branches = new ArrayList<ConditionBranch<NumberResolver>>();

        var initialBranch = createBranch(
            ctx.numericConditionalIf().conditionalFragment(),
            ctx.numericConditionalIf().formula(),
            NumberResolver.class
        );
        branches.add(initialBranch);

        var remainingBranches = ctx.numericConditionalElseIf().stream()
            .map(x -> createBranch(
                x.conditionalFragment(),
                x.formula(),
                NumberResolver.class
            ))
            .toList();
        branches.addAll(remainingBranches);

        var elseCases = ctx.numericConditionalElse().accept(this);
        var elseCase = getNumberResolver(elseCases);

        return List.of(new NumericConditionalResolver(branches, elseCase));
    }

    private <T extends StringResolver> ConditionBranch<T> createBranch(
        SpellDescriptionParser.ConditionalFragmentContext conditionalFragmentContext,
        ParserRuleContext valueContext,
        Class<T> clazz
    ) {
        var conditions = conditionalFragmentContext.accept(this);
        var condition = getBooleanResolver(conditions);

        var values = valueContext.accept(this);
        var value = getResolver(values, clazz);

        return new ConditionBranch<>(condition, value);
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

    private BooleanResolver getBooleanResolver(List<StringResolver> resolvers) {
        return getResolver(resolvers, BooleanResolver.class);
    }

    private NumberResolver getNumberResolver(List<StringResolver> resolvers) {
        return getResolver(resolvers, NumberResolver.class);
    }

    private StringResolver getStringResolver(List<StringResolver> resolvers) {
        return getResolver(resolvers, StringResolver.class);
    }

    private <T extends StringResolver> T getResolver(List<StringResolver> resolvers, Class<T> clazz) {
        if (resolvers.size() != 1) {
            throw new RuntimeException("Unexpected number of resolvers: " + resolvers);
        }

        var stringResolver = resolvers.getFirst();

        if (!clazz.isInstance(stringResolver)) {
            throw new RuntimeException("Unexpected resolver type: " + stringResolver);
        }

        return clazz.cast(stringResolver);
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
