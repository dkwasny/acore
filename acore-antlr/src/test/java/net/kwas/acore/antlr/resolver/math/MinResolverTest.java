package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MinResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void minReturnsSmallerValue() {
    NumberResolver a = c -> 1.0;
    NumberResolver b = c -> 4.0;
    var resolver = new MinResolver(a, b);
    Assertions.assertEquals(1.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void minWithEqualValues_returnsSame() {
    NumberResolver a = c -> 7.0;
    NumberResolver b = c -> 7.0;
    var resolver = new MinResolver(a, b);
    Assertions.assertEquals(7.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void minWithNegativeAndPositive() {
    NumberResolver a = c -> -5.0;
    NumberResolver b = c -> 2.0;
    var resolver = new MinResolver(a, b);
    Assertions.assertEquals(-5.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void minWithTwoNegatives() {
    NumberResolver a = c -> -5.0;
    NumberResolver b = c -> -2.0;
    var resolver = new MinResolver(a, b);
    Assertions.assertEquals(-5.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
