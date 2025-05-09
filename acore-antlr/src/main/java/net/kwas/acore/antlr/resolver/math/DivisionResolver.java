package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record DivisionResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        return left.resolveNumber(ctx) / right.resolveNumber(ctx);
    }

}
