package net.kwas.acore.antlr.resolver.function;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record GreaterThanResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        return left.resolveNumber(ctx) > right().resolveNumber(ctx) ? 1.0 : 0.0;
    }

}
