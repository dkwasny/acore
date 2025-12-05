package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComparisonResolverTest {

  private final SpellContext ctx = new SpellContextBuilder().createSpellContext();

  @Test
  public void equalComparison_whenEqual_returnsOne() {
    NumberResolver left = c -> 5.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.EQUAL);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void equalComparison_whenNotEqual_returnsZero() {
    NumberResolver left = c -> 5.0;
    NumberResolver right = c -> 3.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.EQUAL);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void greaterThanComparison_whenLeftGreater_returnsOne() {
    NumberResolver left = c -> 10.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void greaterThanComparison_whenLeftSmaller_returnsZero() {
    NumberResolver left = c -> 3.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void greaterThanComparison_whenEqual_returnsZero() {
    NumberResolver left = c -> 5.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void greaterThanOrEqualComparison_whenLeftGreater_returnsOne() {
    NumberResolver left = c -> 10.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN_OR_EQUAL);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void greaterThanOrEqualComparison_whenEqual_returnsOne() {
    NumberResolver left = c -> 5.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN_OR_EQUAL);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void greaterThanOrEqualComparison_whenLeftSmaller_returnsZero() {
    NumberResolver left = c -> 3.0;
    NumberResolver right = c -> 5.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN_OR_EQUAL);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void comparisonWithNegativeNumbers() {
    NumberResolver left = c -> -5.0;
    NumberResolver right = c -> -10.0;
    var resolver = new ComparisonResolver(left, right, ComparisonType.GREATER_THAN);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

}
