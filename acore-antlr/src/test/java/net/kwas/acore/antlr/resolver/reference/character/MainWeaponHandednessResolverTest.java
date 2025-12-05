package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainWeaponHandednessResolverTest {

  @Test
  public void twoHandedReturnsTwo() {
    var ctx = getCtx(true);
    var resolver = new MainWeaponHandednessResolver();
    Assertions.assertEquals(2.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void oneHandedReturnsOne() {
    var ctx = getCtx(false);
    var resolver = new MainWeaponHandednessResolver();
    Assertions.assertEquals(1.0, resolver.resolveNumber(ctx), 1e-9);
  }

  private SpellContext getCtx(boolean isTwoHanded) {
    return new SpellContextBuilder()
      .characterInfo(
        new CharacterInfoBuilder()
          .isMainWeaponTwoHanded(isTwoHanded)
          .createCharacterInfo()
      )
      .createSpellContext();
  }
}
