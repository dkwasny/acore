package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class NumericConditionalResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void firstConditionTrue_returnsFirstResult() {
    NumberResolver condition1 = c -> 1.0;
    NumberResolver result1 = c -> 42.0;
    NumberResolver result2 = c -> 99.0;
    NumberResolver elseCase = c -> 0.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition1, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(42.0, actual, 1e-9);
  }

  @Test
  public void firstConditionFalse_returnsSecondResult() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 1.0;
    NumberResolver result1 = c -> 42.0;
    NumberResolver result2 = c -> 99.0;
    NumberResolver elseCase = c -> 0.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(99.0, actual, 1e-9);
  }

  @Test
  public void allConditionsFalse_returnsElseCase() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 0.0;
    NumberResolver result1 = c -> 42.0;
    NumberResolver result2 = c -> 99.0;
    NumberResolver elseCase = c -> 777.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(777.0, actual, 1e-9);
  }

  @Test
  public void emptyBranchList_returnsElseCase() {
    NumberResolver elseCase = c -> 555.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(555.0, actual, 1e-9);
  }

  @Test
  public void nonZeroValueTreatedAsTrue() {
    NumberResolver condition1 = c -> 5.0; // Non-zero is true
    NumberResolver result1 = c -> 42.0;
    NumberResolver elseCase = c -> 0.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(new ConditionBranch<>(condition1, result1)),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(42.0, actual, 1e-9);
  }

  @Test
  public void negativeValueTreatedAsTrue() {
    NumberResolver condition1 = c -> -3.0; // Negative is true
    NumberResolver result1 = c -> 42.0;
    NumberResolver elseCase = c -> 0.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(new ConditionBranch<>(condition1, result1)),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(42.0, actual, 1e-9);
  }

  @Test
  public void multipleConditionsStopsAtFirstTrue() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 1.0;
    NumberResolver condition3 = c -> 1.0; // Also true, but should not reach
    NumberResolver result1 = c -> 123.0;
    NumberResolver result2 = c -> 234.0;
    NumberResolver result3 = c -> 345.0;
    NumberResolver elseCase = c -> 9999.0;
    
    var resolver = new NumericConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2),
        new ConditionBranch<>(condition3, result3)
      ),
      elseCase
    );
    
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(234.0, actual, 1e-9);
  }

}
