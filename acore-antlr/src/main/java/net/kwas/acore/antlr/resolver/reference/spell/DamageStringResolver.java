package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record DamageStringResolver(NumberResolver minResolver, NumberResolver maxResolver) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lowerBound = minResolver.resolveNumber(ctx);
        var upperBound = maxResolver.resolveNumber(ctx);

        var hasVariance = Double.compare(lowerBound, upperBound) != 0;

        String retVal;
        if (hasVariance) {
            retVal = lowerBound + " to " + upperBound;
        }
        else {
            retVal = Double.toString(lowerBound);
        }
        return retVal;
    }

}
