package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record DurationResolver(Long spellId) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = spellId != null ? spellId : ctx.getSpellId();
        return ctx.getSpellInfos().get(mySpellId).duration();
    }

    @Override
    public String resolveString(SpellContext ctx) {

        // TODO: Dynamically choose text based on time.
        // e.g. 10 sec vs 10 min
        return NumberResolver.super.resolveString(ctx);
    }




}
