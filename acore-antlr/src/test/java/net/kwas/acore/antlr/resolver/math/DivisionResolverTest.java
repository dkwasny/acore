package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DivisionResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void dividesCorrectly() {
    NumberResolver a = c -> 10.0;
    NumberResolver b = c -> 2.5;
    var resolver = new DivisionResolver(a, b);
    Assertions.assertEquals(4.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void dividesNegativeNumbersCorrectly() {
    NumberResolver a = c -> -10.0;
    NumberResolver b = c -> -2.5;
    var resolver = new DivisionResolver(a, b);
    Assertions.assertEquals(4.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void dividesToFraction() {
    NumberResolver a = c -> 1.0;
    NumberResolver b = c -> 4.0;
    var resolver = new DivisionResolver(a, b);
    Assertions.assertEquals(0.25, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void divisionByZero_resultsInInfinity() {
    NumberResolver a = c -> 1.0;
    NumberResolver b = c -> 0.0;
    var resolver = new DivisionResolver(a, b);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertTrue(Double.isInfinite(actual));
    Assertions.assertTrue(actual > 0);
  }

  @Test
  public void negativeDivision_resultsInNegativeInfinity() {
    NumberResolver a = c -> -1.0;
    NumberResolver b = c -> 0.0;
    var resolver = new DivisionResolver(a, b);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertTrue(Double.isInfinite(actual));
    Assertions.assertTrue(actual < 0);
  }

}
