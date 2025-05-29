package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

import java.util.Set;

public record AuraPeriodResolver(Long spellId, int index) implements NumberResolver {

    private static final long IMPLICIT_PERIOD = 5000L;

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = ResolverUtils.getSpellId(spellId, ctx);
        var spellInfo = ctx.getSpellInfo(mySpellId);
        var msValue = spellInfo.auraPeriods().get(index);

        // Various spells seem to directly or indirectly reference aura periods
        // with a value of zero.
        // A common usecase for this is spells with regen auras (e.g. 1129).
        // There are also spells that reference aura period while having no
        // aura at all (e.g. 52579).
        // In nearly all cases I've investigated, Wowhead seems to always use
        // a period of five seconds.
        // I think five seconds is some default period used by a multitude of
        // spells.
        // I'm just going to use five if the spell has a period of zero.
        // If it's good enough for Wowhead, it's good enough for me.
        //
        // This default value is necessary for AuraDamageResolver which
        // divides by the aura's period.  We don't want that to be zero.
        if (Double.compare(msValue, 0.0) == 0) {
            msValue = IMPLICIT_PERIOD;
        }

        // Spells seem to assume that periods are always displayed in seconds
        // while the internal value is always milliseconds.
        // Do the division inline.
        return msValue / 1000.0;
    }

}
