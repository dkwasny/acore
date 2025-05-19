package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.DamageUtils;

public record DividedDamageStringResolver(int index, Long spellId, double divisor) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lowerBoundResolver = new DamageBoundResolver(index, spellId, false);
        var baseLowerBound = lowerBoundResolver.resolveNumber(ctx);
        var lowerBound = baseLowerBound / divisor;

        var upperBoundResolver = new DamageBoundResolver(index, spellId, true);
        var baseUpperBound = upperBoundResolver.resolveNumber(ctx);
        var upperBound = baseUpperBound / divisor;

        return DamageUtils.renderSpellDamage(lowerBound, upperBound);
    }

}
