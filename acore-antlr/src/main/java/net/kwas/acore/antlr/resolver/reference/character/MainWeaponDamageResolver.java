package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record MainWeaponDamageResolver(boolean isMax) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var charInfo = ctx.getCharacterInfo();
    return isMax
      ? charInfo.mainWeaponMaxDamage()
      : charInfo.mainWeaponMinDamage();
  }

}
