package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;

public interface BooleanResolver extends StringResolver {

    boolean resolveBoolean(SpellContext ctx);

    @Override
    default String resolveString(SpellContext ctx) {
        return Boolean.toString(resolveBoolean(ctx));
    }

}
