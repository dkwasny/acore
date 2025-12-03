package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record AttackRatingResolver() implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    // See the grammar for the reasoning behind this formula.
    return ctx.getCharacterInfo().characterLevel() * 5;
  }

}
