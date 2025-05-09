package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record LocalizedStringResolver(List<String> resolvers) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lastRenderedNumber = ctx.getLastRenderedNumber();

        var index = (int)Math.max(Math.min(lastRenderedNumber, resolvers.size() - 1), 0);

        return resolvers.get(index);
    }

}
