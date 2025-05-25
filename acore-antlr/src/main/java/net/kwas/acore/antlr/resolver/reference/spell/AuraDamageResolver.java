package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record AuraDamageResolver(int index, Long spellId, boolean isMax) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var baseDamageResolver = new DamageResolver(index, spellId, isMax);
        var baseDamage = baseDamageResolver.resolveNumber(ctx);

        var auraPeriodResolver = new AuraPeriodResolver(spellId, index);
        var auraPeriod = auraPeriodResolver.resolveNumber(ctx);

        var durationResolver = new DurationResolver(spellId);
        var duration = durationResolver.resolveNumber(ctx);

        return baseDamage / (auraPeriod / 1000.0) * duration;
    }

}
