package net.kwas.acore.antlr.resolver.util;


public class DamageUtils {

    public static String renderSpellDamage(double lowerBound, double upperBound) {
        var hasVariance = Double.compare(lowerBound, upperBound) != 0;

        String retVal;
        if (hasVariance) {
            retVal = lowerBound + " to " + upperBound;
        }
        else {
            retVal = Double.toString(lowerBound);
        }
        return retVal;
    }

}
