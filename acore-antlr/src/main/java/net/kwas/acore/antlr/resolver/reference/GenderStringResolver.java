package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record GenderStringResolver(List<String> values) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var gender = ctx.getCharacterInfo().gender();
        // TODO: Convert gender to index (maybe use enum??)
        // I have seen up to three values for a gender string.
        // Assume the third one is unknown?
        var idx = 1;

        return values.get(idx);
    }

}
