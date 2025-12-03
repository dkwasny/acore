package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record AndResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var leftVal = ResolverUtils.toBool(left.resolveNumber(ctx));
    var rightVal = ResolverUtils.toBool(right.resolveNumber(ctx));
    var result = leftVal && rightVal;
    return ResolverUtils.toDouble(result);
  }

}
