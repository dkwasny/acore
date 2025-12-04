package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class StringConditionalResolverTest {

  private final SpellContext ctx = new SpellContext(0L, Map.of(), null, Map.of());

  @Test
  public void firstConditionTrue_returnsFirstResult() {
    NumberResolver condition1 = c -> 1.0;
    StringResolver result1 = c -> "first";
    StringResolver result2 = c -> "second";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition1, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("first", actual);
  }

  @Test
  public void firstConditionFalse_returnsSecondResult() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 1.0;
    StringResolver result1 = c -> "first";
    StringResolver result2 = c -> "second";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("second", actual);
  }

  @Test
  public void allConditionsFalse_returnsElseCase() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 0.0;
    StringResolver result1 = c -> "first";
    StringResolver result2 = c -> "second";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2)
      ),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("else", actual);
  }

  @Test
  public void emptyBranchList_returnsElseCase() {
    StringResolver elseCase = c -> "default";
    
    var resolver = new StringConditionalResolver(
      List.of(),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("default", actual);
  }

  @Test
  public void nonZeroValueTreatedAsTrue() {
    NumberResolver condition1 = c -> 5.0; // Non-zero is true
    StringResolver result1 = c -> "true branch";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(new ConditionBranch<>(condition1, result1)),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("true branch", actual);
  }

  @Test
  public void negativeValueTreatedAsTrue() {
    NumberResolver condition1 = c -> -3.0; // Negative is true
    StringResolver result1 = c -> "true branch";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(new ConditionBranch<>(condition1, result1)),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("true branch", actual);
  }

  @Test
  public void multipleConditionsStopsAtFirstTrue() {
    NumberResolver condition1 = c -> 0.0;
    NumberResolver condition2 = c -> 1.0;
    NumberResolver condition3 = c -> 1.0; // Also true, but should not reach
    StringResolver result1 = c -> "first";
    StringResolver result2 = c -> "second";
    StringResolver result3 = c -> "third";
    StringResolver elseCase = c -> "else";
    
    var resolver = new StringConditionalResolver(
      List.of(
        new ConditionBranch<>(condition1, result1),
        new ConditionBranch<>(condition2, result2),
        new ConditionBranch<>(condition3, result3)
      ),
      elseCase
    );
    
    var actual = resolver.resolveString(ctx);
    Assertions.assertEquals("second", actual);
  }

}
