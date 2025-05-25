package net.kwas.acore.antlr.resolver.util;

import net.kwas.acore.antlr.resolver.context.SpellContext;

public class ResolverUtils {

    public static long getSpellId(Long possibleSpellId, SpellContext ctx) {
        return possibleSpellId != null ? possibleSpellId : ctx.getSpellId();
    }

}
