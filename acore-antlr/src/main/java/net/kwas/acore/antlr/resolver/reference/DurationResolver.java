package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record DurationResolver() implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        return ctx.getCurrentSpellInfo().duration();
    }

}
