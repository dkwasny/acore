package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class AttackPowerResolverTest {

  @Test
  public void returnsAttackPowerFromCharacterInfo() {
    var charInfo = new CharacterInfo(10, Gender.MALE, 0L, 123L, 0L, 0L, 0f, 0f, 0f, 0f, 0f, false, Set.of(), "");
    var ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());

    var resolver = new AttackPowerResolver();
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(123.0, actual, 1e-9);
  }

}
