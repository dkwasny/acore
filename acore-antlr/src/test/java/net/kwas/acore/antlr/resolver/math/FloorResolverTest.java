package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class FloorResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void floorsPositiveDecimal() {
    NumberResolver child = c -> 3.9;
    var resolver = new FloorResolver(child);
    Assertions.assertEquals(3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void floorsNegativeDecimal() {
    NumberResolver child = c -> -2.1;
    var resolver = new FloorResolver(child);
    Assertions.assertEquals(-3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void floorsIntegerLeavesSame() {
    NumberResolver child = c -> 5.0;
    var resolver = new FloorResolver(child);
    Assertions.assertEquals(5.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
