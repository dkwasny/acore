package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;

public record StaticStringResolver(String value) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        return value;
    }

}
