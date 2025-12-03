package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DurationResolver(Long spellId) implements NumberResolver {

  private static final double MINUTE = 60;
  private static final double HOUR = MINUTE * 60;

  @Override
  public double resolveNumber(SpellContext ctx) {
    var mySpellId = ResolverUtils.getSpellId(spellId, ctx);
    var spellInfo = ctx.getSpellInfo(mySpellId);

    var baseDuration = spellInfo.duration();
    var durationPerLevel = spellInfo.durationPerLevel();
    var maxDuration = spellInfo.maxDuration();
    var characterLevel = ctx.getCharacterInfo().characterLevel();

    var leveledDuration = baseDuration + (durationPerLevel * characterLevel);

    var actualDuration = Math.min(leveledDuration, maxDuration);
    return actualDuration / 1000.0;
  }

  @Override
  public String resolveString(SpellContext ctx) {
    var number = resolveNumber(ctx);

    var newNumber = number;
    var suffix = " sec";
    if (number > HOUR) {
      newNumber = (number / HOUR);
      suffix = " hours";
    } else if (number > MINUTE) {
      newNumber = (number / MINUTE);
      suffix = " min";
    }

    var renderedNumber = ResolverUtils.renderNumber(newNumber, ctx);
    return renderedNumber + suffix;
  }


}
