package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.context.CharacterInfo;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.common.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class ConditionalSpellRefResolverTest {

  @Test
  public void spellLearned_returnsOne() {
    CharacterInfo charInfo = new CharacterInfo(
      1, Gender.MALE, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, Set.of(12345L), ""
    );
    SpellContext ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());
    
    var resolver = new ConditionalSpellRefResolver(12345L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void spellNotLearned_returnsZero() {
    CharacterInfo charInfo = new CharacterInfo(
      1, Gender.MALE, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, Set.of(12345L), ""
    );
    SpellContext ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());
    
    var resolver = new ConditionalSpellRefResolver(99999L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void noLearnedSpells_returnsZero() {
    CharacterInfo charInfo = new CharacterInfo(
      1, Gender.MALE, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, Set.of(), ""
    );
    SpellContext ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());
    
    var resolver = new ConditionalSpellRefResolver(12345L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void multipleLearnedSpells_findsCorrectOne() {
    CharacterInfo charInfo = new CharacterInfo(
      1, Gender.MALE, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, Set.of(111L, 222L, 333L, 444L), ""
    );
    SpellContext ctx = new SpellContext(0L, Map.of(), charInfo, Map.of());
    
    var resolver = new ConditionalSpellRefResolver(333L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

}
