package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record LocalizedStringResolver(List<String> resolvers) implements StringResolver {

    @Override
    public String resolveString() {
        // TODO: Pick string based on lastSeenValue in context
        return resolvers.getFirst();
    }

}
