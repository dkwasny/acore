package net.kwas.acore.antlr.resolver.util;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

// A particularly nasty resolver needed to reasonably handle spell variables.
// Most of them leverage conditionals which inherently return strings instead
// of numbers.
// I could create a numeric conditional type for use in variables, but one
// variable set (1) leverages the "${formula}.decimalPlaces" syntax, which
// is inherently tied to the string return type.
// In the end I _could_ have better typing, but just adding a parser resolver
// is simpler both in the grammar and the codebase itself.
// Maybe consider revisiting if number parsing is low performance (unlikely).
public record ParseNumberResolver(StringResolver child) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var string = child.resolveString(ctx);
    return Double.parseDouble(string);
  }

}
