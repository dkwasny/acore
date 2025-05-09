package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;

import java.util.List;
import java.util.stream.Collectors;

public record StringConcatenationResolver(List<StringResolver> children) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        return children.stream()
            .map(x -> x.resolveString(ctx))
            .collect(Collectors.joining(""));
    }

}
