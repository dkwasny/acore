package net.kwas.acore.antlr.resolver.util;

import net.kwas.acore.antlr.resolver.context.SpellContext;

public class ResolverUtils {

    public static long getSpellId(Long possibleSpellId, SpellContext ctx) {
        return possibleSpellId != null ? possibleSpellId : ctx.getSpellId();
    }

    public static boolean toBool(double value) {
        return Double.compare(value, 0.0) != 0;
    }

    public static double toDouble(boolean value) {
        return value ? 1.0 : 0.0;
    }

    public static String renderNumber(double number, SpellContext ctx) {
        var rounded = Math.round(number);
        var absoluteValue = Math.abs(rounded);
        ctx.setLastRenderedNumber(absoluteValue);
        return Long.toString(absoluteValue);
    }

}
