package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public interface NumberResolver extends StringResolver {

    double resolveNumber(SpellContext ctx);

    @Override
    default String resolveString(SpellContext ctx) {
        var value = resolveNumber(ctx);
        return ResolverUtils.renderNumber(value, ctx);
    }

}
