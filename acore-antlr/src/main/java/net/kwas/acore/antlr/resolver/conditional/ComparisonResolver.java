package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.BooleanResolver;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record ComparisonResolver(NumberResolver left, NumberResolver right, ComparisonType comparisonType) implements BooleanResolver {

    @Override
    public boolean resolveBoolean(SpellContext ctx) {
        var leftNum = left.resolveNumber(ctx);
        var rightNum = right().resolveNumber(ctx);
        var cmp = Double.compare(leftNum, rightNum);

        switch (comparisonType) {
            case EQUAL -> { return cmp == 0; }
            case GREATER_THAN -> { return cmp > 0; }
            case GREATER_THAN_OR_EQUAL -> { return cmp >= 0; }
            default -> throw new RuntimeException("Unexpected comparison: " + comparisonType);
        }
    }

}
