package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenderStringResolverTest {

  @Test
  public void maleSelectsFirstValue() {
    var ctx = getSpellCtx(Gender.MALE);
    var resolver = new GenderStringResolver(List.of("he", "she"));
    Assertions.assertEquals("he", resolver.resolveString(ctx));
  }

  @Test
  public void femaleSelectsSecondValue() {
    var ctx = getSpellCtx(Gender.FEMALE);
    var resolver = new GenderStringResolver(List.of("he", "she"));
    Assertions.assertEquals("she", resolver.resolveString(ctx));
  }

  private SpellContext getSpellCtx(Gender gender) {
    var charInfo = new CharacterInfoBuilder().gender(gender).createCharacterInfo();
    return new SpellContextBuilder().characterInfo(charInfo).createSpellContext();
  }

}
