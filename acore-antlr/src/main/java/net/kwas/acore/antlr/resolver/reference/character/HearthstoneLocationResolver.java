package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record HearthstoneLocationResolver() implements StringResolver {

  @Override
  public String resolveString(SpellContext ctx) {
    return ctx.getCharacterInfo().hearthstoneLocation();
  }

}
