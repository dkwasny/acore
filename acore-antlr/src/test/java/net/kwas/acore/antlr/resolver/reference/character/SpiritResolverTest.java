package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpiritResolverTest {

  @Test
  public void returnsSpiritValue() {
    var ctx = new SpellContextBuilder()
      .characterInfo(
        new CharacterInfoBuilder()
          .spirit(13L)
          .createCharacterInfo()
      )
      .createSpellContext();

    var resolver = new SpiritResolver();
    Assertions.assertEquals(13.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
