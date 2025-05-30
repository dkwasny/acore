package net.kwas.acore.antlr.resolver.reference.character;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record MainWeaponBaseDamageResolver(boolean isMax) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var charInfo = ctx.getCharacterInfo();
        return isMax
            ? charInfo.mainWeaponBaseMaxDamage()
            : charInfo.mainWeaponBaseMinDamage();
    }

}

