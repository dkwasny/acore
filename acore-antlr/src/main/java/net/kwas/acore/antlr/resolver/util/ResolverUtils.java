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
        return renderNumber(number, 0, ctx);
    }

    public static String renderNumber(double number, int decimalPlaces, SpellContext ctx) {
        var absoluteValue = Math.abs(number);

        // The only singular number is one without a decimal point.
        boolean isPlural = !(Double.compare(absoluteValue, 1.0) == 0 && decimalPlaces == 0);
        ctx.setNextLocalizedStringPlural(isPlural);

        var formatString = "%." + decimalPlaces + "f";
        return String.format(formatString, absoluteValue);
    }

}
