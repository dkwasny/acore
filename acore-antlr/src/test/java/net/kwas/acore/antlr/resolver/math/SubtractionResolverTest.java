package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SubtractionResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void subtractsCorrectly() {
    NumberResolver a = c -> 10.0;
    NumberResolver b = c -> 3.5;
    var resolver = new SubtractionResolver(a, b);
    Assertions.assertEquals(6.5, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void subtractsToNegative() {
    NumberResolver a = c -> 2.0;
    NumberResolver b = c -> 5.0;
    var resolver = new SubtractionResolver(a, b);
    Assertions.assertEquals(-3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void subtractsTwoNegativeNumbers() {
    NumberResolver a = c -> -10.0;
    NumberResolver b = c -> -3.5;
    var resolver = new SubtractionResolver(a, b);
    Assertions.assertEquals(-6.5, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void subtractsZero() {
    NumberResolver a = c -> 7.0;
    NumberResolver b = c -> 0.0;
    var resolver = new SubtractionResolver(a, b);
    Assertions.assertEquals(7.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
