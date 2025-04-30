package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;

public record MultiplierResolver(long id) implements NumberResolver {

    @Override
    public double resoveNumber() {
        return 0;
    }

}
