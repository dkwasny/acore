package net.kwas.acore.antlr.resolver.function;

import net.kwas.acore.antlr.resolver.NumberResolver;

public record MaxNumberResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resoveNumber() {
        return Double.max(left.resoveNumber(), right.resoveNumber());
    }

}
