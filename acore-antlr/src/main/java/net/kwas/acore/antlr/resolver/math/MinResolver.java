package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record MinResolver(NumberResolver left, NumberResolver right) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    return Double.min(left.resolveNumber(ctx), right.resolveNumber(ctx));
  }

}
