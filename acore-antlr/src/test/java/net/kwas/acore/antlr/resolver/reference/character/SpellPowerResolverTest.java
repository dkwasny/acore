package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpellPowerResolverTest {

  @Test
  public void returnsSpellPower() {
    var ctx = new SpellContextBuilder()
      .characterInfo(
        new CharacterInfoBuilder()
          .spellPower(77L)
          .createCharacterInfo()
      )
      .createSpellContext();

    var resolver = new SpellPowerResolver();
    Assertions.assertEquals(77.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
