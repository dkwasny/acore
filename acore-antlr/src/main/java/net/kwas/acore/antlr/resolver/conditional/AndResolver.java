package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.BooleanResolver;

public record AndResolver(BooleanResolver left, BooleanResolver right) implements BooleanResolver {

    @Override
    public boolean resolveBoolean(SpellContext ctx) {
        return left.resolveBoolean(ctx) && right.resolveBoolean(ctx);
    }

}
