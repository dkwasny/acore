package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

public record GenderStringResolver(String male, String female) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var gender = ctx.getCharacterInfo().gender();

        String retVal;

        // TODO: Try and standardize gender via an enum at some point
        if ("Male".equals(gender)) {
            retVal = male;
        }
        else if ("Female".equals(gender)) {
            retVal = female;
        }
        else {
            throw new RuntimeException("Unknown gender for character: " + gender);
        }

        return retVal;
    }

}
