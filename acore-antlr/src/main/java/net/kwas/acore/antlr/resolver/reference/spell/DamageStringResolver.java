package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DamageStringResolver(NumberResolver minResolver, NumberResolver maxResolver) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var minDamage = minResolver.resolveNumber(ctx);
        var maxDamage = maxResolver.resolveNumber(ctx);

        var hasVariance = Double.compare(minDamage, maxDamage) != 0;

        var renderedMinDamage = ResolverUtils.renderNumber(minDamage, ctx);

        String retVal;
        if (hasVariance) {
            var renderedMaxDamage = ResolverUtils.renderNumber(maxDamage, ctx);
            retVal = renderedMinDamage + " to " + renderedMaxDamage;
        }
        else {
            retVal = renderedMinDamage;
        }
        return retVal;
    }

}
