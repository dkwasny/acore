package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.StringResolver;

public record GenderStringResolver(String male, String female) implements StringResolver {

    @Override
    public String resolveString() {
        // TODO: Return correct string based on context
        return male + " or " + female;
    }

}
