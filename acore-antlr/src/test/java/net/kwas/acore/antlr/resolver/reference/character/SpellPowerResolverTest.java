package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class SpellPowerResolverTest {

  @Test
  public void returnsSpellPower() {
    var charInfo = new CharacterInfo(
      1,
      Gender.MALE,
      0L,
      0L,
      0L,
      77L,
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

    var resolver = new SpellPowerResolver();
    Assertions.assertEquals(77.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
