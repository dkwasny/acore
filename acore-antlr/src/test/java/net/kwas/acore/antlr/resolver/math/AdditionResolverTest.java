package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdditionResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void addsPositiveNumbers() {
    NumberResolver a = c -> 2.5;
    NumberResolver b = c -> 3.5;
    var resolver = new AdditionResolver(a, b);
    Assertions.assertEquals(6.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void addsNegativeAndPositive() {
    NumberResolver a = c -> -1.0;
    NumberResolver b = c -> 4.0;
    var resolver = new AdditionResolver(a, b);
    Assertions.assertEquals(3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void addsNegativeAndNegative() {
    NumberResolver a = c -> -1.0;
    NumberResolver b = c -> -4.0;
    var resolver = new AdditionResolver(a, b);
    Assertions.assertEquals(-5.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void addsZero() {
    NumberResolver a = c -> 0.0;
    NumberResolver b = c -> 5.0;
    var resolver = new AdditionResolver(a, b);
    Assertions.assertEquals(5.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
