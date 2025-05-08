package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;

public record MultiplicationResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resolveNumber() {
        return left.resolveNumber() * right.resolveNumber();
    }

}
