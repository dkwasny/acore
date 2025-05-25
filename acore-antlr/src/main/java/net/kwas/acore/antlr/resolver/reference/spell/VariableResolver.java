package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record VariableResolver(String variable) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var resolver = ctx.getVariableMap().get(variable);
        if (resolver == null) {
            throw new RuntimeException("Variable not found: " + variable);
        }
        return resolver.resolveNumber(ctx);
    }

}
