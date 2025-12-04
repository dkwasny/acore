package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    var charInfo = new CharacterInfo(
      1,
      gender,
      0L,
      0L,
      0L,
      0L,
      0f,
      0f,
      0f,
      0f,
      0f,
      false,
      Set.of(),
      ""
    );
    return new SpellContext(0L, Map.of(), charInfo, Map.of());
  }

}
