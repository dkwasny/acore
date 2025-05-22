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
import net.kwas.acore.antlr.resolver.reference.AuraDamageBoundResolver;
import net.kwas.acore.antlr.resolver.reference.AuraPeriodResolver;
import net.kwas.acore.antlr.resolver.reference.DamageStringResolver;
import net.kwas.acore.antlr.resolver.reference.DurationResolver;
import net.kwas.acore.antlr.resolver.reference.GenderStringResolver;
import net.kwas.acore.antlr.resolver.reference.LocalizedStringResolver;
import net.kwas.acore.antlr.resolver.reference.DamageBoundResolver;
import net.kwas.acore.antlr.resolver.reference.VariableResolver;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpellDescriptionVisitor extends SpellDescriptionBaseVisitor<List<StringResolver>> {

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
    public List<StringResolver> visitDamageBound(SpellDescriptionParser.DamageBoundContext ctx) {
        var index = Integer.parseInt(ctx.index.getText());
        var spellId = getOptionalInteger(ctx.spellId);
        var isUpper = ctx.UPPER_M_CHAR() != null;
        return List.of(new DamageBoundResolver(index, spellId, isUpper));
    }

    @Override
    public List<StringResolver> visitDuration(SpellDescriptionParser.DurationContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new DurationResolver(spellId));
    }

    @Override
    public List<StringResolver> visitAuraPeriod(SpellDescriptionParser.AuraPeriodContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        var index = Integer.parseInt(ctx.index.getText());
        return List.of(new AuraPeriodResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitProcCharges(SpellDescriptionParser.ProcChargesContext ctx) {
        // TODO IMPLEMENT
        return super.visitProcCharges(ctx);
    }

    @Override
    public List<StringResolver> visitProcChance(SpellDescriptionParser.ProcChanceContext ctx) {
        // TODO IMPLEMENT
        return super.visitProcChance(ctx);
    }

    @Override
    public List<StringResolver> visitChainTargets(SpellDescriptionParser.ChainTargetsContext ctx) {
        // TODO IMPLEMENT
        return super.visitChainTargets(ctx);
    }

    @Override
    public List<StringResolver> visitRadius(SpellDescriptionParser.RadiusContext ctx) {
        // TODO IMPLEMENT
        return super.visitRadius(ctx);
    }

    @Override
    public List<StringResolver> visitHearthstoneLocation(SpellDescriptionParser.HearthstoneLocationContext ctx) {
        // TODO IMPLEMENT
        return super.visitHearthstoneLocation(ctx);
    }

    @Override
    public List<StringResolver> visitMiscValue(SpellDescriptionParser.MiscValueContext ctx) {
        // TODO IMPLEMENT
        return super.visitMiscValue(ctx);
    }

    @Override
    public List<StringResolver> visitPointsPerCombo(SpellDescriptionParser.PointsPerComboContext ctx) {
        // TODO IMPLEMENT
        return super.visitPointsPerCombo(ctx);
    }

    @Override
    public List<StringResolver> visitAmplitude(SpellDescriptionParser.AmplitudeContext ctx) {
        // TODO IMPLEMENT
        // Index can be missing on this one.  Use `1` in this case.
        return super.visitAmplitude(ctx);
    }

    @Override
    public List<StringResolver> visitMaxTargets(SpellDescriptionParser.MaxTargetsContext ctx) {
        // TODO IMPLEMENT
        return super.visitMaxTargets(ctx);
    }

    @Override
    public List<StringResolver> visitAttackPower(SpellDescriptionParser.AttackPowerContext ctx) {
        // TODO IMPLEMENT
        return super.visitAttackPower(ctx);
    }

    @Override
    public List<StringResolver> visitRangedAttackPower(SpellDescriptionParser.RangedAttackPowerContext ctx) {
        // TODO IMPLEMENT
        // Not sure if we can get specifically ranged attack power...might need to settle for normal attack power.
        return super.visitRangedAttackPower(ctx);
    }

    @Override
    public List<StringResolver> visitMainWeaponDamage(SpellDescriptionParser.MainWeaponDamageContext ctx) {
        // TODO IMPLEMENT
        return super.visitMainWeaponDamage(ctx);
    }

    @Override
    public List<StringResolver> visitMainWeaponSpeed(SpellDescriptionParser.MainWeaponSpeedContext ctx) {
        // TODO IMPLEMENT
        return super.visitMainWeaponSpeed(ctx);
    }

    @Override
    public List<StringResolver> visitSpellPower(SpellDescriptionParser.SpellPowerContext ctx) {
        // TODO IMPLEMENT
        return super.visitSpellPower(ctx);
    }

    @Override
    public List<StringResolver> visitSpirit(SpellDescriptionParser.SpiritContext ctx) {
        // TODO IMPLEMENT
        return super.visitSpirit(ctx);
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
    public List<StringResolver> visitDamageStringFragment(SpellDescriptionParser.DamageStringFragmentContext ctx) {
        var index = Integer.parseInt(ctx.index.getText());
        var spellId = getOptionalInteger(ctx.spellId);

        var lowerBoundResolver = new DamageBoundResolver(index, spellId, false);
        var upperBoundResolver = new DamageBoundResolver(index, spellId, true);

        return List.of(new DamageStringResolver(lowerBoundResolver, upperBoundResolver));
    }

    @Override
    public List<StringResolver> visitAuraDamageStringFragment(SpellDescriptionParser.AuraDamageStringFragmentContext ctx) {
        var index = Integer.parseInt(ctx.index.getText());
        var spellId = getOptionalInteger(ctx.spellId);

        var lowerBoundResolver = new AuraDamageBoundResolver(index, spellId, false);
        var upperBoundResolver = new AuraDamageBoundResolver(index, spellId, true);

        return List.of(new DamageStringResolver(lowerBoundResolver, upperBoundResolver));
    }

    @Override
    public List<StringResolver> visitArithmeticDamageString(SpellDescriptionParser.ArithmeticDamageStringContext ctx) {
        var right = Double.parseDouble(ctx.right.getText());

        List<StringResolver> childResolver;
        if (ctx.damageStringFragment() != null) {
            childResolver = ctx.damageStringFragment().accept(this);
        }
        else if (ctx.auraDamageStringFragment() != null) {
            childResolver = ctx.auraDamageStringFragment().accept(this);
        }
        else {
            throw new RuntimeException("No damage resolver for arithmetic resolver");
        }

        // Leverage DamageStringResolver as a "pair" of number resolvers.
        // Throw away the damage resolver itself after retrieving the two bound resolvers.
        var damageResolver = getResolver(childResolver, DamageStringResolver.class);
        var rightResolver = new StaticNumberResolver(right);

        NumberResolver lowerBoundResolver;
        NumberResolver upperBoundResolver;
        if (ctx.STAR() != null) {
            lowerBoundResolver = new MultiplicationResolver(damageResolver.lowerBoundResolver(), rightResolver);
            upperBoundResolver = new MultiplicationResolver(damageResolver.upperBoundResolver(), rightResolver);
        }
        else if (ctx.FORWARD_SLASH() != null) {
            lowerBoundResolver = new DivisionResolver(damageResolver.lowerBoundResolver(), rightResolver);
            upperBoundResolver = new DivisionResolver(damageResolver.upperBoundResolver(), rightResolver);
        }
        else {
            throw new RuntimeException("Unexpected operation for arithmetic string resolver");
        }

        return List.of(new DamageStringResolver(lowerBoundResolver, upperBoundResolver));
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

    private Long getOptionalInteger(SpellDescriptionParser.PositiveIntegerContext ctx) {
        return ctx != null ? Long.getLong(ctx.getText()) : null;
    }

    @Override
    protected List<StringResolver> defaultResult() {
        return List.of();
    }

    @Override
    protected List<StringResolver> aggregateResult(List<StringResolver> aggregate, List<StringResolver> nextResult) {
        List<StringResolver> retVal;

        if (aggregate.isEmpty()) {
            retVal = nextResult;
        }
        else if (nextResult.isEmpty()) {
            retVal = aggregate;
        }
        else {
            retVal = new ArrayList<>();
            retVal.addAll(aggregate);
            retVal.addAll(nextResult);
        }

        return retVal;
    }

}
