package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record ConditionalSpellRefResolver(long spellId) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var hasSpell = ctx.getCharacterInfo().learnedSpellIds().contains(spellId);
    return ResolverUtils.toDouble(hasSpell);
  }

}
