package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record LocalizedStringResolver(List<String> values) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        // Cast to int so we can use it as an index
        var lastRenderedNumber = (int)ctx.getLastRenderedNumber();

        if (lastRenderedNumber < 0) {
            throw new RuntimeException("Unexpected negative value: " + lastRenderedNumber);
        }

        int index;
        if (lastRenderedNumber == 0) {
            // The number zero needs a plural word in english.
            // Just use the last word in the list and pray.
            index = values.size() - 1;
        }
        else {
            // I've seen cases where there are more than two options.
            // I think this applies to non-english languages, but I'll keep the logic regardless.
            index = Math.min(lastRenderedNumber - 1, values.size() - 1);
        }

        return values.get(index);
    }

}
