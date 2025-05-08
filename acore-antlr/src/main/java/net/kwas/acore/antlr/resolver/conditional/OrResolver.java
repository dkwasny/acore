package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.BooleanResolver;

public record OrResolver(BooleanResolver left, BooleanResolver right) implements BooleanResolver {

    @Override
    public boolean resolveBoolean() {
        return left.resolveBoolean() || right.resolveBoolean();
    }

}
