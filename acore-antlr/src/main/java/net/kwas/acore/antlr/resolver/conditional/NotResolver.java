package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.BooleanResolver;

public record NotResolver(BooleanResolver value) implements BooleanResolver {

    @Override
    public boolean resolveBoolean() {
        return !value.resolveBoolean();
    }

}
