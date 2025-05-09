package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record VariableResolver(String variable) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        // TODO Get value from context
        return 400.0;
    }

}
