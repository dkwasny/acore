package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;

import java.util.List;

public record GenderStringResolver(List<String> values) implements StringResolver {

  @Override
  public String resolveString(SpellContext ctx) {
    var gender = ctx.getCharacterInfo().gender();
    var idx = gender == Gender.MALE ? 0 : 1;
    return values.get(idx);
  }

}
