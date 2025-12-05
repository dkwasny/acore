package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttackPowerResolverTest {

  @Test
  public void returnsAttackPowerFromCharacterInfo() {
    var charInfo = new CharacterInfoBuilder().attackPower(123L).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();

    var resolver = new AttackPowerResolver();
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(123.0, actual, 1e-9);
  }

}
