package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record DamageStringResolver(NumberResolver lowerBoundResolver, NumberResolver upperBoundResolver) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lowerBound = lowerBoundResolver.resolveNumber(ctx);
        var upperBound = upperBoundResolver.resolveNumber(ctx);

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
