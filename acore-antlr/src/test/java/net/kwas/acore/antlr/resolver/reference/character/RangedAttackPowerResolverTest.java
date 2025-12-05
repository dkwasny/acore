package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RangedAttackPowerResolverTest {

  @Test
  public void returnsRangedAttackPower() {
    var ctx = new SpellContextBuilder()
      .characterInfo(
        new CharacterInfoBuilder()
          .rangedAttackPower(321L)
          .createCharacterInfo()
      )
      .createSpellContext();

    var resolver = new RangedAttackPowerResolver();
    Assertions.assertEquals(321.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
