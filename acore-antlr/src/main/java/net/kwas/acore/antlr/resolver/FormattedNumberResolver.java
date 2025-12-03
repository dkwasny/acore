package net.kwas.acore.antlr.resolver;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record FormattedNumberResolver(NumberResolver child, int decimalPlaces) implements StringResolver {

  @Override
  public String resolveString(SpellContext ctx) {
    var number = child.resolveNumber(ctx);
    return ResolverUtils.renderNumber(number, decimalPlaces, ctx);
  }

}
