package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;

public record StaticNumberResolver(double value) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    return value;
  }

}
