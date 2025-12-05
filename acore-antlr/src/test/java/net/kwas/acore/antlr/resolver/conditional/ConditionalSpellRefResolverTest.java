package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ConditionalSpellRefResolverTest {

  @Test
  public void spellLearned_returnsOne() {
    var charInfo = new CharacterInfoBuilder().learnedSpellIds(Set.of(12345L)).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();
    
    var resolver = new ConditionalSpellRefResolver(12345L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

  @Test
  public void spellNotLearned_returnsZero() {
    var charInfo = new CharacterInfoBuilder().learnedSpellIds(Set.of(12345L)).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();
    
    var resolver = new ConditionalSpellRefResolver(99999L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void noLearnedSpells_returnsZero() {
    var charInfo = new CharacterInfoBuilder().learnedSpellIds(Set.of()).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();
    
    var resolver = new ConditionalSpellRefResolver(12345L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(0.0, actual, 1e-9);
  }

  @Test
  public void multipleLearnedSpells_findsCorrectOne() {
    var charInfo = new CharacterInfoBuilder().learnedSpellIds(Set.of(111L, 222L, 333L, 444L)).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();
    
    var resolver = new ConditionalSpellRefResolver(333L);
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(1.0, actual, 1e-9);
  }

}
