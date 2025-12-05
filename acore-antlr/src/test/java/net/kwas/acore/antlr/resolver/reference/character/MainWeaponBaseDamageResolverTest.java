package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainWeaponBaseDamageResolverTest {

  private final SpellContext ctx = new SpellContextBuilder()
    .characterInfo(
      new CharacterInfoBuilder()
        .mainWeaponBaseMinDamage(3f)
        .mainWeaponBaseMaxDamage(4f)
        .createCharacterInfo()
    )
    .createSpellContext();

  @Test
  public void minReturnsBaseMinDamage() {
    var resolver = new MainWeaponBaseDamageResolver(false);
    Assertions.assertEquals(3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxReturnsBaseMaxDamage() {
    var resolver = new MainWeaponBaseDamageResolver(true);
    Assertions.assertEquals(4.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
