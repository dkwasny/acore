package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

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
    return new SpellContext(
      0L,
      Map.of(),
      new CharacterInfo(
        1,
        Gender.MALE,
        0L,
        0L,
        0L,
        0L,
        1f,
        2f,
        3f,
        4f,
        5f,
        isTwoHanded,
        Set.of(),
        ""
      ),
      Map.of()
    );
  }
}
