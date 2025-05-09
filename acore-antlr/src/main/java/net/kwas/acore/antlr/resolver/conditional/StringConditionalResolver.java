package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record StringConditionalResolver(List<ConditionBranch<StringResolver>> branches, StringResolver elseCase) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        for (var branch : branches) {
            if (branch.condition().resolveBoolean(ctx)) {
                return branch.result().resolveString(ctx);
            }
        }

        return elseCase.resolveString(ctx);
    }

}
