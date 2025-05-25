package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record DamageStringResolver(NumberResolver minResolver, NumberResolver maxResolver) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var minDamage = minResolver.resolveNumber(ctx);
        var maxDamage = maxResolver.resolveNumber(ctx);

        var hasVariance = Double.compare(minDamage, maxDamage) != 0;

        String retVal;
        if (hasVariance) {
            retVal = minDamage + " to " + maxDamage;
        }
        else {
            retVal = Double.toString(minDamage);
        }
        return retVal;
    }

}
