package net.kwas.acore.antlr.resolver;

import java.util.List;
import java.util.stream.Collectors;

public record StringConcatenationResolver(List<StringResolver> children) implements StringResolver {

    @Override
    public String resolveString() {
        return children.stream()
            .map(x -> x.resolveString())
            .collect(Collectors.joining(""));
    }

}
