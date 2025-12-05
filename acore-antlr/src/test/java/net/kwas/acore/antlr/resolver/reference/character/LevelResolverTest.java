package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LevelResolverTest {

  @Test
  public void returnsCharacterLevelAsNumber() {
    var charInfo = new CharacterInfoBuilder().characterLevel(42).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();

    var resolver = new LevelResolver();
    Assertions.assertEquals(42.0, resolver.resolveNumber(ctx), 1e-9);
  }

}
