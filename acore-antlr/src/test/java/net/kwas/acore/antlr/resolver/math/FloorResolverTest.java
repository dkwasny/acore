package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FloorResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

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
