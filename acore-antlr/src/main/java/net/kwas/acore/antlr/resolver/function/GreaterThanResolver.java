package net.kwas.acore.antlr.resolver.function;

import net.kwas.acore.antlr.resolver.NumberResolver;

public record GreaterThanResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resolveNumber() {
        return left.resolveNumber() > right().resolveNumber() ? 1.0 : 0.0;
    }

}
