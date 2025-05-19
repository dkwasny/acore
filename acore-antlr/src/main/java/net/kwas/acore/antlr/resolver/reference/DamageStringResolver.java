package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.DamageUtils;

public record DamageStringResolver(int index, Long spellId) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lowerBoundResolver = new DamageBoundResolver(index, spellId, false);
        var lowerBound = lowerBoundResolver.resolveNumber(ctx);

        var upperBoundResolver = new DamageBoundResolver(index, spellId, true);
        var upperBound = upperBoundResolver.resolveNumber(ctx);

        return DamageUtils.renderSpellDamage(lowerBound, upperBound);
    }

}
