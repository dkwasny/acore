package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record MainWeaponHandednessResolver() implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        return ctx.getCharacterInfo().isMainWeaponTwoHanded() ? 2.0 : 1.0;
    }

}
