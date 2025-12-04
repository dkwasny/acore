package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class SpiritResolverTest {

  @Test
  public void returnsSpiritValue() {
    var charInfo = new CharacterInfo(
      1,
      Gender.MALE,
      13L,
      0L,
      0L,
      0L,
      1f,
      2f,
      3f,
      4f,
      0f,
      false,
      Set.of(),
      ""
    );
    var ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());

    var resolver = new SpiritResolver();
    Assertions.assertEquals(13.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
