package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import net.kwas.acore.antlr.resolver.context.SpellInfoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class AmplitudeResolverTest {

  @Test
  public void returnsAmplitudeFromSpellInfo() {
    var spellInfo = new SpellInfoBuilder()
      .amplitudes(List.of(1.5f, 2.5f, 3.5f))
      .createSpellInfo();

    var ctx = new SpellContextBuilder()
      .spellId(12345L)
      .spellInfoMap(Map.of(12345L, spellInfo))
      .characterInfo(new CharacterInfoBuilder().createCharacterInfo())
      .createSpellContext();

    var resolver = new AmplitudeResolver(null, 0);
    Assertions.assertEquals(1.5, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void returnsAmplitudeAtSpecificIndex() {
    var spellInfo = new SpellInfoBuilder()
      .amplitudes(List.of(1.5f, 2.5f, 3.5f))
      .createSpellInfo();

    var ctx = new SpellContextBuilder()
      .spellId(12345L)
      .spellInfoMap(Map.of(12345L, spellInfo))
      .characterInfo(new CharacterInfoBuilder().createCharacterInfo())
      .createSpellContext();

    var resolver = new AmplitudeResolver(null, 2);
    Assertions.assertEquals(3.5, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void usesProvidedSpellIdWhenAvailable() {
    var spellInfo1 = new SpellInfoBuilder()
      .amplitudes(List.of(1.0f, 2.0f, 3.0f))
      .createSpellInfo();

    var spellInfo2 = new SpellInfoBuilder()
      .amplitudes(List.of(10.0f, 20.0f, 30.0f))
      .createSpellInfo();

    var ctx = new SpellContextBuilder()
      .spellId(12345L)
      .spellInfoMap(Map.of(12345L, spellInfo1, 67890L, spellInfo2))
      .characterInfo(new CharacterInfoBuilder().createCharacterInfo())
      .createSpellContext();

    // Resolver specifies spellId 67890, so it should use spellInfo2
    var resolver = new AmplitudeResolver(67890L, 1);
    Assertions.assertEquals(20.0, resolver.resolveNumber(ctx), 1e-9);
  }

}

