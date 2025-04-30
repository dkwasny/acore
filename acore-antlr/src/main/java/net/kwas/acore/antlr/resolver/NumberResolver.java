package net.kwas.acore.antlr.resolver;

public interface NumberResolver extends StringResolver {

    double resoveNumber();

    @Override
    default String resolveString() {
        return Double.toString(resoveNumber());
    }

}
