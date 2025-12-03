package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record MaxResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    return Double.max(left.resolveNumber(ctx), right.resolveNumber(ctx));
  }

}
