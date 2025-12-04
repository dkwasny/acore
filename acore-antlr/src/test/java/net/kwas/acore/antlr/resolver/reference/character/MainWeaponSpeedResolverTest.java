package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class MainWeaponSpeedResolverTest {

  @Test
  public void returnsMainWeaponSpeed() {
    var charInfo = new CharacterInfo(1, Gender.MALE,0L,0L,0L,0L,1f,2f,3f,4f,2.75f,false, Set.of(), "");
    var ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());

    var resolver = new MainWeaponSpeedResolver();
    Assertions.assertEquals(2.75, resolver.resolveNumber(ctx), 1e-9);
  }

}
