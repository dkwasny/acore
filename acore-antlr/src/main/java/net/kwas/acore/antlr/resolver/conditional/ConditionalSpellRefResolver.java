package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.BooleanResolver;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record ConditionalSpellRefResolver(long spellId) implements BooleanResolver {

    @Override
    public boolean resolveBoolean() {
        // TODO: See if context has spellId
        return true;
    }

}
