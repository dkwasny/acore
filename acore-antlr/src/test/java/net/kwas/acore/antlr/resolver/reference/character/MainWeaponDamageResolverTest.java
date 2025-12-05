package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainWeaponDamageResolverTest {

  private final SpellContext ctx = new SpellContextBuilder()
    .characterInfo(
      new CharacterInfoBuilder()
        .mainWeaponMinDamage(1f)
        .mainWeaponMaxDamage(2f)
        .createCharacterInfo()
    )
    .createSpellContext();

  @Test
  public void minReturnsMinDamage() {
    var resolver = new MainWeaponDamageResolver(false);
    Assertions.assertEquals(1.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxReturnsMaxDamage() {
    var resolver = new MainWeaponDamageResolver(true);
    Assertions.assertEquals(2.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
