package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void notTrue_returnsFalse() {
    NumberResolver t = c -> 1.0;
    var resolver = new NotResolver(t);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void notFalse_returnsTrue() {
    NumberResolver f = c -> 0.0;
    var resolver = new NotResolver(f);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void notNonZeroValue_returnsFalse() {
    NumberResolver nonZero = c -> 5.0;
    var resolver = new NotResolver(nonZero);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void notNegativeValue_returnsFalse() {
    NumberResolver negative = c -> -3.5;
    var resolver = new NotResolver(negative);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

}
