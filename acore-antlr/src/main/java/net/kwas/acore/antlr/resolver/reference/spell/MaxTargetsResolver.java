package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record MaxTargetsResolver(Long spellId) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = ResolverUtils.getSpellId(spellId, ctx);
        return ctx.getSpellInfo(mySpellId).maxTargets();
    }

}
