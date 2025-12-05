package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.CharacterInfoBuilder;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.context.SpellContextBuilder;
import net.kwas.acore.antlr.resolver.context.SpellInfo;
import net.kwas.acore.antlr.resolver.context.SpellInfoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class AuraDamageResolverTest {

  private final SpellInfo spellInfo = new SpellInfoBuilder()
    .baseValues(List.of(2, 3, 4))
    .dieSides(List.of(5, 6, 7))
    .baseValuePerLevels(List.of(8f, 9f, 10f))
    .auraPeriods(List.of(10000L, 20000L, 30000L))
    .duration(400)
    .durationPerLevel(200)
    .maxDuration(50000)
    .baseLevel(2)
    .maxLevel(80)
    .createSpellInfo();

  private final SpellContext ctx = new SpellContextBuilder()
    .spellId(12345L)
    .spellInfoMap(Map.of(
      12345L, spellInfo,
      99999L, SpellInfo.EMPTY
    ))
    .characterInfo(new CharacterInfoBuilder().characterLevel(10).createCharacterInfo())
    .createSpellContext();

  @Test
  public void resolveNumber_firstIndex() {
    var resolver = new AuraDamageResolver(0, null, false);
    // (2 + (8 * 8) + 1) / (10000 / 1000) * ((400 + (200 * 10)) / 1000)
    Assertions.assertEquals(16.08, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void resolveNumber_secondIndex() {
    var resolver = new AuraDamageResolver(1, null, false);
    // (3 + (9 * 8) + 1) / (20000 / 1000) * ((400 + (200 * 10)) / 1000)
    Assertions.assertEquals(9.12, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void resolveNumber_firstIndex_isMax() {
    var resolver = new AuraDamageResolver(0, null, true);
    // (2 + (8 * 8) + 5) / (10000 / 1000) * ((400 + (200 * 10)) / 1000)
    Assertions.assertEquals(17.04, resolver.resolveNumber(ctx), 1e-9);
  }

  @Test
  public void resolveNumber_specifiedSpellId() {
    var resolver = new AuraDamageResolver(0, 9999L, false);
    // (0 + (0 * 10) + 1) / (5000 / 1000) * (0 / 1000)
    Assertions.assertEquals(0.0, resolver.resolveNumber(ctx), 1e-9);
  }

}

