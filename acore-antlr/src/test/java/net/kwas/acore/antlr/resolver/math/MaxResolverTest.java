package net.kwas.acore.antlr.resolver.math;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaxResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void maxReturnsLargerValue() {
    NumberResolver a = c -> 2.0;
    NumberResolver b = c -> 5.0;
    var resolver = new MaxResolver(a, b);
    Assertions.assertEquals(5.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxWithEqualValues_returnsSame() {
    NumberResolver a = c -> 3.0;
    NumberResolver b = c -> 3.0;
    var resolver = new MaxResolver(a, b);
    Assertions.assertEquals(3.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxWithNegativeAndPositive() {
    NumberResolver a = c -> -2.0;
    NumberResolver b = c -> 1.0;
    var resolver = new MaxResolver(a, b);
    Assertions.assertEquals(1.0, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void maxWithTwoNegatives() {
    NumberResolver a = c -> -2.0;
    NumberResolver b = c -> -1.0;
    var resolver = new MaxResolver(a, b);
    Assertions.assertEquals(-1.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
