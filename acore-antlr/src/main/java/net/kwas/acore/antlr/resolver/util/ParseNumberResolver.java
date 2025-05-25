package net.kwas.acore.antlr.resolver.util;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

// A particularly nasty resolver needed to reasonably handle spell variables.
// They do weird tricks like "${$m1*5}.5" to create a number like "10.5".
// It's just easier to do a string parse than add weirdo math rules to handle
// said usecase.
// Do not use this resolver unless absolutely necessary.
public record ParseNumberResolver(StringResolver child) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var string = child.resolveString(ctx);
        return Double.parseDouble(string);
    }

}
