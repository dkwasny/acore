package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record RangedAttackPowerResolver() implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    return ctx.getCharacterInfo().rangedAttackPower();
  }

}
