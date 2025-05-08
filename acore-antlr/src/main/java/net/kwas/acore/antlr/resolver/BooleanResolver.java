package net.kwas.acore.antlr.resolver;

public interface BooleanResolver extends StringResolver {

    boolean resolveBoolean();

    @Override
    default String resolveString() {
        return Boolean.toString(resolveBoolean());
    }

}
