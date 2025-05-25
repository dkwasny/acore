package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DurationResolver(Long spellId) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = ResolverUtils.getSpellId(spellId, ctx);
        return ctx.getSpellInfos().get(mySpellId).duration();
    }

    @Override
    public String resolveString(SpellContext ctx) {

        // TODO: Dynamically choose text based on time.
        // e.g. 10 sec vs 10 min
        return NumberResolver.super.resolveString(ctx);
    }




}
