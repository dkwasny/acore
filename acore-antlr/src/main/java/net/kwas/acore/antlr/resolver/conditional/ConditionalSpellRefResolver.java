package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.BooleanResolver;

public record ConditionalSpellRefResolver(long spellId) implements BooleanResolver {

    @Override
    public boolean resolveBoolean(SpellContext ctx) {
        return ctx.getCharacterInfo().learnedSpellIds().contains(spellId);
    }

}
