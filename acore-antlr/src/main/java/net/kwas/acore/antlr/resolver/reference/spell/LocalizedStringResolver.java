package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record LocalizedStringResolver(List<String> values) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var isPlural = ctx.isNextLocalizedStringPlural();
        int index = isPlural ? 1 : 0;
        return values.get(index);
    }

}
