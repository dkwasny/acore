package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record ComparisonResolver(
  NumberResolver left, NumberResolver right,
  ComparisonType comparisonType
) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var leftNum = left.resolveNumber(ctx);
    var rightNum = right().resolveNumber(ctx);
    var cmp = Double.compare(leftNum, rightNum);

    boolean result;
    switch (comparisonType) {
      case EQUAL -> result = cmp == 0;
      case GREATER_THAN -> result = cmp > 0;
      case GREATER_THAN_OR_EQUAL -> result = cmp >= 0;
      default -> throw new RuntimeException("Unexpected comparison: " + comparisonType);
    }

    return ResolverUtils.toDouble(result);
  }

}
