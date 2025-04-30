package net.kwas.acore.antlr.resolver;

public record StaticNumberResolver(double value) implements NumberResolver {

    @Override
    public double resoveNumber() {
        return value;
    }

}
