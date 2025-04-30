package net.kwas.acore.antlr.resolver;

public record StaticStringResolver(String value) implements StringResolver {

    @Override
    public String resolveString() {
        return value;
    }

}
