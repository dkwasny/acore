package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainWeaponSpeedResolverTest {

  @Test
  public void returnsMainWeaponSpeed() {
    var ctx = new SpellContextBuilder()
      .characterInfo(
        new CharacterInfoBuilder()
          .mainWeaponSpeed(2.75f)
          .createCharacterInfo()
      )
      .createSpellContext();

    var resolver = new MainWeaponSpeedResolver();
    Assertions.assertEquals(2.75, resolver.resolveNumber(ctx), 1e-9);
  }

}
