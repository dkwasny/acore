package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record NotResolver(NumberResolver resolver) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var value = ResolverUtils.toBool(resolver.resolveNumber(ctx));
    return ResolverUtils.toDouble(!value);
  }

}
