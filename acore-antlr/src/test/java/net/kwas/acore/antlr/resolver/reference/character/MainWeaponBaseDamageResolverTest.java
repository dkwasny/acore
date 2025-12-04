package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class MainWeaponBaseDamageResolverTest {

  @Test
  public void minReturnsBaseMinDamage() {
    var charInfo = new CharacterInfo(1, Gender.MALE,0L,0L,0L,0L,1f,2f,3f,4f,0f,false, Set.of(), "");
    var ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());

    var resolver = new MainWeaponBaseDamageResolver(false);
    Assertions.assertEquals(3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxReturnsBaseMaxDamage() {
    var charInfo = new CharacterInfo(1, Gender.MALE,0L,0L,0L,0L,1f,2f,3f,4f,0f,false, Set.of(), "");
    var ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());

    var resolver = new MainWeaponBaseDamageResolver(true);
    Assertions.assertEquals(4.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
