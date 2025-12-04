package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class AndResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void bothTrue_returnsOne() {
    NumberResolver t = c -> 1.0;
    var resolver = new AndResolver(t, t);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void leftTrueRightFalse_returnsZero() {
    NumberResolver t = c -> 1.0;
    NumberResolver f = c -> 0.0;
    var resolver = new AndResolver(t, f);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void leftFalseRightTrue_returnsZero() {
    NumberResolver t = c -> 1.0;
    NumberResolver f = c -> 0.0;
    var resolver = new AndResolver(f, t);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void bothFalse_returnsZero() {
    NumberResolver f = c -> 0.0;
    var resolver = new AndResolver(f, f);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

}
