package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record FloorResolver(NumberResolver child) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var value = child.resolveNumber(ctx);
        return Math.floor(value);
    }

}
