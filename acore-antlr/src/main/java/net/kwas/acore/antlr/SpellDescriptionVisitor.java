package net.kwas.acore.antlr;

import net.kwas.acore.antlr.grammar.SpellDescriptionBaseVisitor;
import net.kwas.acore.antlr.grammar.SpellDescriptionParser;
import net.kwas.acore.antlr.resolver.conditional.AndResolver;
import net.kwas.acore.antlr.resolver.conditional.ComparisonResolver;
import net.kwas.acore.antlr.resolver.conditional.ComparisonType;
import net.kwas.acore.antlr.resolver.conditional.ConditionBranch;
import net.kwas.acore.antlr.resolver.conditional.NumericConditionalResolver;
import net.kwas.acore.antlr.resolver.conditional.StringConditionalResolver;
import net.kwas.acore.antlr.resolver.conditional.ConditionalSpellRefResolver;
import net.kwas.acore.antlr.resolver.conditional.NotResolver;
import net.kwas.acore.antlr.resolver.conditional.OrResolver;
import net.kwas.acore.antlr.resolver.math.MinResolver;
import net.kwas.acore.antlr.resolver.math.AdditionResolver;
import net.kwas.acore.antlr.resolver.math.DivisionResolver;
import net.kwas.acore.antlr.resolver.math.MaxResolver;
import net.kwas.acore.antlr.resolver.math.FloorResolver;
import net.kwas.acore.antlr.resolver.math.MultiplicationResolver;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StaticNumberResolver;
import net.kwas.acore.antlr.resolver.StaticStringResolver;
import net.kwas.acore.antlr.resolver.StringConcatenationResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.math.SubtractionResolver;
import net.kwas.acore.antlr.resolver.reference.character.AttackPowerResolver;
import net.kwas.acore.antlr.resolver.reference.character.AttackRatingResolver;
import net.kwas.acore.antlr.resolver.reference.character.LevelResolver;
import net.kwas.acore.antlr.resolver.reference.character.MainWeaponDamageResolver;
import net.kwas.acore.antlr.resolver.reference.character.MainWeaponHandednessResolver;
import net.kwas.acore.antlr.resolver.reference.character.MainWeaponSpeedResolver;
import net.kwas.acore.antlr.resolver.reference.character.RangedAttackPowerResolver;
import net.kwas.acore.antlr.resolver.reference.character.SpellPowerResolver;
import net.kwas.acore.antlr.resolver.reference.character.SpiritResolver;
import net.kwas.acore.antlr.resolver.reference.spell.AmplitudeResolver;
import net.kwas.acore.antlr.resolver.reference.spell.AuraDamageResolver;
import net.kwas.acore.antlr.resolver.reference.spell.AuraPeriodResolver;
import net.kwas.acore.antlr.resolver.reference.spell.ChainAmplitudeResolver;
import net.kwas.acore.antlr.resolver.reference.spell.ChainTargetsResolver;
import net.kwas.acore.antlr.resolver.reference.spell.CumulativeAuraResolver;
import net.kwas.acore.antlr.resolver.reference.spell.DamageStringResolver;
import net.kwas.acore.antlr.resolver.reference.spell.DurationResolver;
import net.kwas.acore.antlr.resolver.reference.character.GenderStringResolver;
import net.kwas.acore.antlr.resolver.reference.character.HearthstoneLocationResolver;
import net.kwas.acore.antlr.resolver.reference.spell.LocalizedStringResolver;
import net.kwas.acore.antlr.resolver.reference.spell.DamageResolver;
import net.kwas.acore.antlr.resolver.reference.spell.MaxTargetLevelResolver;
import net.kwas.acore.antlr.resolver.reference.spell.MaxTargetsResolver;
import net.kwas.acore.antlr.resolver.reference.spell.MiscValueResolver;
import net.kwas.acore.antlr.resolver.reference.spell.PointsPerComboResolver;
import net.kwas.acore.antlr.resolver.reference.spell.ProcChanceResolver;
import net.kwas.acore.antlr.resolver.reference.spell.ProcChargesResolver;
import net.kwas.acore.antlr.resolver.reference.spell.RadiusResolver;
import net.kwas.acore.antlr.resolver.reference.spell.RangeResolver;
import net.kwas.acore.antlr.resolver.reference.spell.VariableResolver;
import net.kwas.acore.antlr.resolver.util.ParseNumberResolver;
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
        var stringResolver = getStringResolver(ctx.variableDefinition().accept(this));
        var value = new ParseNumberResolver(stringResolver);
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    @Override
    public List<StringResolver> visitText(SpellDescriptionParser.TextContext ctx) {
        var children = super.visitText(ctx);
        return concatIfNecessary(children);
    }

    @Override
    public List<StringResolver> visitConditionalText(SpellDescriptionParser.ConditionalTextContext ctx) {
        var children = super.visitConditionalText(ctx);
        return concatIfNecessary(children);
    }

    @Override
    public List<StringResolver> visitMiscChars(SpellDescriptionParser.MiscCharsContext ctx) {
        var text = ctx.getText();
        return List.of(new StaticStringResolver(text));
    }

    @Override
    public List<StringResolver> visitSquareBrackets(SpellDescriptionParser.SquareBracketsContext ctx) {
        var text = ctx.getText();
        return List.of(new StaticStringResolver(text));
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
        if (ctx.numericReference() != null) {
            return children;
        }
        else if (ctx.numericDefinition() != null) {
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
    public List<StringResolver> visitMinDamage(SpellDescriptionParser.MinDamageContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new DamageResolver(index, spellId, false));
    }

    @Override
    public List<StringResolver> visitMaxDamage(SpellDescriptionParser.MaxDamageContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new DamageResolver(index, spellId, true));
    }

    @Override
    public List<StringResolver> visitFloor(SpellDescriptionParser.FloorContext ctx) {
        var child = getNumberResolver(super.visitFloor(ctx));
        return List.of(new FloorResolver(child));
    }

    @Override
    public List<StringResolver> visitDuration(SpellDescriptionParser.DurationContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new DurationResolver(spellId));
    }

    @Override
    public List<StringResolver> visitAuraPeriod(SpellDescriptionParser.AuraPeriodContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        var index = getIndex(ctx.index);
        return List.of(new AuraPeriodResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitProcCharges(SpellDescriptionParser.ProcChargesContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new ProcChargesResolver(spellId));
    }

    @Override
    public List<StringResolver> visitProcChance(SpellDescriptionParser.ProcChanceContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new ProcChanceResolver(spellId));
    }

    @Override
    public List<StringResolver> visitChainTargets(SpellDescriptionParser.ChainTargetsContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new ChainTargetsResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitRadius(SpellDescriptionParser.RadiusContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new RadiusResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitHearthstoneLocation(SpellDescriptionParser.HearthstoneLocationContext ctx) {
        return List.of(new HearthstoneLocationResolver());
    }

    @Override
    public List<StringResolver> visitMiscValue(SpellDescriptionParser.MiscValueContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new MiscValueResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitPointsPerCombo(SpellDescriptionParser.PointsPerComboContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new PointsPerComboResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitAmplitude(SpellDescriptionParser.AmplitudeContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new AmplitudeResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitChainAmplitude(SpellDescriptionParser.ChainAmplitudeContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new ChainAmplitudeResolver(spellId, index));
    }

    @Override
    public List<StringResolver> visitMinRange(SpellDescriptionParser.MinRangeContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new RangeResolver(spellId, index, false));
    }

    @Override
    public List<StringResolver> visitMaxRange(SpellDescriptionParser.MaxRangeContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new RangeResolver(spellId, index, true));
    }

    @Override
    public List<StringResolver> visitCumulativeAura(SpellDescriptionParser.CumulativeAuraContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new CumulativeAuraResolver(spellId));
    }

    @Override
    public List<StringResolver> visitMaxTargets(SpellDescriptionParser.MaxTargetsContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new MaxTargetsResolver(spellId));
    }

    @Override
    public List<StringResolver> visitMaxTargetLevel(SpellDescriptionParser.MaxTargetLevelContext ctx) {
        var spellId = getOptionalInteger(ctx.spellId);
        return List.of(new MaxTargetLevelResolver(spellId));
    }

    @Override
    public List<StringResolver> visitAttackPower(SpellDescriptionParser.AttackPowerContext ctx) {
        return List.of(new AttackPowerResolver());
    }

    @Override
    public List<StringResolver> visitRangedAttackPower(SpellDescriptionParser.RangedAttackPowerContext ctx) {
        return List.of(new RangedAttackPowerResolver());
    }

    @Override
    public List<StringResolver> visitMainWeaponMinDamage(SpellDescriptionParser.MainWeaponMinDamageContext ctx) {
        return List.of(new MainWeaponDamageResolver(false));
    }

    @Override
    public List<StringResolver> visitMainWeaponMaxDamage(SpellDescriptionParser.MainWeaponMaxDamageContext ctx) {
        return List.of(new MainWeaponDamageResolver(true));
    }

    @Override
    public List<StringResolver> visitMainWeaponSpeed(SpellDescriptionParser.MainWeaponSpeedContext ctx) {
        return List.of(new MainWeaponSpeedResolver());
    }

    @Override
    public List<StringResolver> visitMainWeaponMinBaseDamage(SpellDescriptionParser.MainWeaponMinBaseDamageContext ctx) {
        // TODO: How is base damage different from normal damage?
        // Create new resolver once I figure that out...
        return List.of(new MainWeaponDamageResolver(false));
    }

    @Override
    public List<StringResolver> visitMainWeaponMaxBaseDamage(SpellDescriptionParser.MainWeaponMaxBaseDamageContext ctx) {
        // TODO: How is base damage different from normal damage?
        // Create new resolver once I figure that out...
        return List.of(new MainWeaponDamageResolver(true));
    }

    @Override
    public List<StringResolver> visitSpellPower(SpellDescriptionParser.SpellPowerContext ctx) {
        return List.of(new SpellPowerResolver());
    }

    @Override
    public List<StringResolver> visitSpirit(SpellDescriptionParser.SpiritContext ctx) {
        return List.of(new SpiritResolver());
    }

    @Override
    public List<StringResolver> visitCharacterLevel(SpellDescriptionParser.CharacterLevelContext ctx) {
        return List.of(new LevelResolver());
    }

    @Override
    public List<StringResolver> visitAttackRating(SpellDescriptionParser.AttackRatingContext ctx) {
        return List.of(new AttackRatingResolver());
    }

    @Override
    public List<StringResolver> visitMainWeaponHandedness(SpellDescriptionParser.MainWeaponHandednessContext ctx) {
        return List.of(new MainWeaponHandednessResolver());
    }

    @Override
    public List<StringResolver> visitLocalizedString(SpellDescriptionParser.LocalizedStringContext ctx) {
        var values = ctx.text().stream().map(RuleContext::getText).toList();
        return List.of(new LocalizedStringResolver(values));
    }

    @Override
    public List<StringResolver> visitGenderString(SpellDescriptionParser.GenderStringContext ctx) {
        var values = ctx.text().stream().map(RuleContext::getText).toList();
        return List.of(new GenderStringResolver(values));
    }

    @Override
    public List<StringResolver> visitDamageString(SpellDescriptionParser.DamageStringContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);
        return createDamageStringResolver(index, spellId);
    }

    @Override
    public List<StringResolver> visitPercentDamageStringRule(SpellDescriptionParser.PercentDamageStringRuleContext ctx) {
        var integer = Integer.parseInt(ctx.positiveInteger().getText());

        // This is special logic I made up based on a few spells that use this rule.
        // See the grammar file for more information.
        int index = 0;
        Long spellId = null;
        if (integer <= 3) {
            index = integer - 1;
        }
        else {
            spellId = (long)integer;
        }

        return createDamageStringResolver(index, spellId);
    }

    private List<StringResolver> createDamageStringResolver(int index, Long spellId) {
        var minResolver = new DamageResolver(index, spellId, false);
        var maxResolver = new DamageResolver(index, spellId, true);

        return List.of(new DamageStringResolver(minResolver, maxResolver));
    }

    @Override
    public List<StringResolver> visitAuraDamageString(SpellDescriptionParser.AuraDamageStringContext ctx) {
        var index = getIndex(ctx.index);
        var spellId = getOptionalInteger(ctx.spellId);

        var minResolver = new AuraDamageResolver(index, spellId, false);
        var maxResolver = new AuraDamageResolver(index, spellId, true);

        return List.of(new DamageStringResolver(minResolver, maxResolver));
    }

    @Override
    public List<StringResolver> visitArithmeticDamageString(SpellDescriptionParser.ArithmeticDamageStringContext ctx) {
        var right = Double.parseDouble(ctx.right.getText());
        var rightResolver = new StaticNumberResolver(right);

        StringResolver retVal;
        if (ctx.numericDefinition() != null) {
            var childResolver = getNumberResolver(ctx.numericDefinition().accept(this));
            if (ctx.STAR() != null) {
                retVal = new MultiplicationResolver(childResolver, rightResolver);
            }
            else if (ctx.FORWARD_SLASH() != null) {
                retVal = new DivisionResolver(childResolver, rightResolver);
            }
            else {
                throw new RuntimeException("Unexpected operation for arithmetic string resolver");
            }

        }
        else {
            List<StringResolver> childResolver;
            if (ctx.damageString() != null) {
                childResolver = ctx.damageString().accept(this);
            } else if (ctx.auraDamageString() != null) {
                childResolver = ctx.auraDamageString().accept(this);
            } else {
                throw new RuntimeException("No child resolver for arithmetic string resolver");
            }

            // Leverage DamageStringResolver as a "pair" of number resolvers.
            // Throw away the damage resolver itself after retrieving the two inner number resolvers.
            var damageResolver = getResolver(childResolver, DamageStringResolver.class);

            NumberResolver lowerBoundResolver;
            NumberResolver upperBoundResolver;
            if (ctx.STAR() != null) {
                lowerBoundResolver = new MultiplicationResolver(damageResolver.minResolver(), rightResolver);
                upperBoundResolver = new MultiplicationResolver(damageResolver.maxResolver(), rightResolver);
            } else if (ctx.FORWARD_SLASH() != null) {
                lowerBoundResolver = new DivisionResolver(damageResolver.minResolver(), rightResolver);
                upperBoundResolver = new DivisionResolver(damageResolver.maxResolver(), rightResolver);
            } else {
                throw new RuntimeException("Unexpected operation for arithmetic string resolver");
            }

            retVal = new DamageStringResolver(lowerBoundResolver, upperBoundResolver);
        }

        return List.of(retVal);
    }

    @Override
    public List<StringResolver> visitVariableReference(SpellDescriptionParser.VariableReferenceContext ctx) {
        var text = ctx.identifier().getText();
        return List.of(new VariableResolver(text));
    }

    @Override
    public List<StringResolver> visitMin(SpellDescriptionParser.MinContext ctx) {
        var left = getNumberResolver(ctx.left.accept(this));
        var right = getNumberResolver(ctx.right.accept(this));
        return List.of(new MinResolver(left, right));
    }

    @Override
    public List<StringResolver> visitMax(SpellDescriptionParser.MaxContext ctx) {
        var left = getNumberResolver(ctx.left.accept(this));
        var right = getNumberResolver(ctx.right.accept(this));
        return List.of(new MaxResolver(left, right));
    }

    @Override
    public List<StringResolver> visitGreaterThan(SpellDescriptionParser.GreaterThanContext ctx) {
        return createComparisonResolver(ctx.left, ctx.right, ComparisonType.GREATER_THAN);
    }

    @Override
    public List<StringResolver> visitGreaterThanOrEqual(SpellDescriptionParser.GreaterThanOrEqualContext ctx) {
        return createComparisonResolver(ctx.left, ctx.right, ComparisonType.GREATER_THAN_OR_EQUAL);
    }

    @Override
    public List<StringResolver> visitEqual(SpellDescriptionParser.EqualContext ctx) {
        return createComparisonResolver(ctx.left, ctx.right, ComparisonType.EQUAL);
    }

    private List<StringResolver> createComparisonResolver(SpellDescriptionParser.FormulaFragmentContext left, SpellDescriptionParser.FormulaFragmentContext right, ComparisonType comparisonType) {
        var leftResolver = getNumberResolver(left.accept(this));
        var rightResolver = getNumberResolver(right.accept(this));
        return List.of(new ComparisonResolver(leftResolver, rightResolver, comparisonType));
    }

    @Override
    public List<StringResolver> visitFormulaConditional(SpellDescriptionParser.FormulaConditionalContext ctx) {
        var condition = getNumberResolver(ctx.condition.accept(this));
        var trueCase = getNumberResolver(ctx.trueCase.accept(this));
        var branches = List.of(new ConditionBranch<>(condition, trueCase));

        var falseCase = getNumberResolver(ctx.falseCase.accept(this));

        return List.of(new NumericConditionalResolver(branches, falseCase));
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
        else if (ctx.numericReference() != null) {
            return children;
        }

        if (ctx.EXCLAMATION_POINT() != null) {
            var resolver = getNumberResolver(children);
            return List.of(new NotResolver(resolver));
        }

        // Remaining cases are binary operations that expect two children
        if (children.size() != 2) {
            throw new RuntimeException("Unexpected number of children for conditional fragment: " + children);
        }

        var left = (NumberResolver) children.getFirst();
        var right = (NumberResolver)children.getLast();

        if (ctx.AMPERSAND() != null) {
            return List.of(new AndResolver(left, right));
        }
        else if (ctx.PIPE() != null) {
            return List.of(new OrResolver(left, right));
        }

        throw new RuntimeException("Unexpected usecase for conditional fragment: " + ctx);
    }

    @Override
    public List<StringResolver> visitStringConditional(SpellDescriptionParser.StringConditionalContext ctx) {
        var branches = new ArrayList<ConditionBranch<StringResolver>>();

        var initialBranch = createBranch(
            ctx.stringConditionalIf().conditionalFragment(),
            ctx.stringConditionalIf().conditionalText()
        );
        branches.add(initialBranch);

        var remainingBranches = ctx.stringConditionalElseIf().stream()
            .map(x -> createBranch(
                x.conditionalFragment(),
                x.conditionalText()
            ))
            .toList();
        branches.addAll(remainingBranches);

        var elseCase = visitOptionalContext(ctx.stringConditionalElse().conditionalText());

        return List.of(new StringConditionalResolver(branches, elseCase));
    }

    private ConditionBranch<StringResolver> createBranch(
        SpellDescriptionParser.ConditionalFragmentContext conditionalFragmentContext,
        ParserRuleContext valueContext
    ) {
        var conditions = conditionalFragmentContext.accept(this);
        var condition = getNumberResolver(conditions);

        var value = visitOptionalContext(valueContext);

        return new ConditionBranch<>(condition, value);
    }

    @Override
    public List<StringResolver> visitRuleOfRhunok(SpellDescriptionParser.RuleOfRhunokContext ctx) {
        List<StringResolver> resolvers = List.of(
            new DurationResolver(null),
            new StaticStringResolver(".")
        );
        return List.of(new StringConcatenationResolver(resolvers));
    }

    @Override
    public List<StringResolver> visitTortureRule(SpellDescriptionParser.TortureRuleContext ctx) {
        List<StringResolver> resolvers = List.of(
            new ProcChanceResolver(null),
            new StaticStringResolver("%")
        );
        return List.of(new StringConcatenationResolver(resolvers));
    }

    private NumberResolver getNumberResolver(List<StringResolver> resolvers) {
        return getResolver(resolvers, NumberResolver.class);
    }

    private StringResolver visitOptionalContext(ParserRuleContext valueContext) {
        StringResolver retVal;
        if (valueContext == null) {
            retVal = new StaticStringResolver("");
        }
        else {
            var children = valueContext.accept(this);
            retVal = getStringResolver(children);
        }
        return retVal;
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
        return ctx != null ? Long.parseLong(ctx.getText()) : null;
    }

    private int getIndex(SpellDescriptionParser.PositiveIntegerContext ctx) {
        // A missing index implies the first index.
        var index = ctx != null ? Integer.parseInt(ctx.getText()) : 1;
        // Convert the index to base zero.
        return index - 1;
    }

    // Don't wrap with a concatenation resolver unless there are actually
    // multiple resolvers after reducing.
    private List<StringResolver> concatIfNecessary(List<StringResolver> resolvers) {
        var reducedResolvers = resolvers;
        if (resolvers.size() > 1) {
            reducedResolvers = reduceStaticStringResolvers(resolvers);
        }

        StringResolver retVal;
        if (reducedResolvers.size() > 1) {
            retVal = new StringConcatenationResolver(reducedResolvers);
        }
        else {
            retVal = reducedResolvers.getFirst();
        }
        return List.of(retVal);
    }

    // Combine sequential sibling static string resolves into one.
    // We end up with a bunch of small static resolvers due to how fine-grained
    // the grammar is.
    private List<StringResolver> reduceStaticStringResolvers(List<StringResolver> resolvers) {
        var retVal = new ArrayList<StringResolver>();

        var builder = new StringBuilder();
        for (var resolver : resolvers) {
            if (resolver instanceof StaticStringResolver(String value)) {
                builder.append(value);
            }
            else {
                if (!builder.isEmpty()) {
                    var staticResolver = new StaticStringResolver(builder.toString());
                    retVal.add(staticResolver);
                    builder = new StringBuilder();
                }
                retVal.add(resolver);
            }
        }

        if (!builder.isEmpty()) {
            var staticResolver = new StaticStringResolver(builder.toString());
            retVal.add(staticResolver);
        }

        return retVal;
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
