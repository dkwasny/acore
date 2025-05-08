package net.kwas.acore.antlr.resolver;

public interface NumberResolver extends StringResolver {

    double resolveNumber();

    @Override
    default String resolveString() {
        return Double.toString(resolveNumber());
    }

}
