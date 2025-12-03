package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

import java.util.List;

// TODO: Figure out how to merge this with StringConditionalResolver.
// The types at play make it tedious...maybe a redesign is in order.
public record NumericConditionalResolver(
  List<ConditionBranch<NumberResolver>> branches,
  NumberResolver elseCase
) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    for (var branch : branches) {
      var boolNumber = branch.condition().resolveNumber(ctx);
      if (ResolverUtils.toBool(boolNumber)) {
        return branch.result().resolveNumber(ctx);
      }
    }

    return elseCase.resolveNumber(ctx);
  }

}
