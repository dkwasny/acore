package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MultiplicationResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void multipliesPositiveNumbers() {
    NumberResolver a = c -> 3.0;
    NumberResolver b = c -> 4.0;
    var resolver = new MultiplicationResolver(a, b);
    Assertions.assertEquals(12.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void multipliesByZero() {
    NumberResolver a = c -> 5.0;
    NumberResolver b = c -> 0.0;
    var resolver = new MultiplicationResolver(a, b);
    Assertions.assertEquals(0.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void multipliesNegativeAndPositive() {
    NumberResolver a = c -> -2.0;
    NumberResolver b = c -> 3.0;
    var resolver = new MultiplicationResolver(a, b);
    Assertions.assertEquals(-6.0, resolver.resolveNumber(ctx), 1e-9);
  }

    @Test
  public void multipliesTwoNegatives() {
    NumberResolver a = c -> -2.0;
    NumberResolver b = c -> -3.0;
    var resolver = new MultiplicationResolver(a, b);
    Assertions.assertEquals(6.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
