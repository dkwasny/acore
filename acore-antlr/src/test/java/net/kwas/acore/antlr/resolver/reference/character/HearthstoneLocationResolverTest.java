package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HearthstoneLocationResolverTest {

  @Test
  public void returnsHearthstoneLocation() {
    var charInfo = new CharacterInfoBuilder().hearthstoneLocation("Stormwind").createCharacterInfo();
    var ctx = new SpellContextBuilder().characterInfo(charInfo).createSpellContext();

    var resolver = new HearthstoneLocationResolver();
    Assertions.assertEquals("Stormwind", resolver.resolveString(ctx));
  }

}
