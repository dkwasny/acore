package net.kwas.acore.antlr.resolver.function;

import net.kwas.acore.antlr.resolver.BooleanResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record GreaterThanResolver(NumberResolver left, NumberResolver right) implements BooleanResolver {

    @Override
    public boolean resolveBoolean(SpellContext ctx) {
        return left.resolveNumber(ctx) > right().resolveNumber(ctx);
    }

}
