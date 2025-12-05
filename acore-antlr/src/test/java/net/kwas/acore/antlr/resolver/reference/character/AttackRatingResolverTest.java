package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttackRatingResolverTest {

  @Test
  public void attackRatingIsLevelTimesFive() {
    var charInfo = new CharacterInfoBuilder().characterLevel(7).createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();

    var resolver = new AttackRatingResolver();
    var actual = resolver.resolveNumber(ctx);
    Assertions.assertEquals(35.0, actual, 1e-9);
  }

}
