package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;

public record SubtractionResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resoveNumber() {
        return left.resoveNumber() - right.resoveNumber();
    }

}
