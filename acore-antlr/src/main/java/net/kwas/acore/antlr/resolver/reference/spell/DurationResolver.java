package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DurationResolver(Long spellId) implements NumberResolver {

    private static final double SECOND = 1000;
    private static final double MINUTE = SECOND * 60;
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
        return Math.min(leveledDuration, maxDuration);
    }

    @Override
    public String resolveString(SpellContext ctx) {
        var number = resolveNumber(ctx);

        var newNumber = number;
        var suffix = " ms";
        if (number > HOUR) {
            newNumber = (number / HOUR);
            suffix = " hours";
        }
        else if (number > MINUTE) {
            newNumber = (number / MINUTE);
            suffix = " min";
        }
        else if (number > SECOND) {
            newNumber = (number / SECOND);
            suffix = " sec";
        }

        var renderedNumber = ResolverUtils.renderNumber(newNumber, ctx);
        return renderedNumber + suffix;
    }




}
