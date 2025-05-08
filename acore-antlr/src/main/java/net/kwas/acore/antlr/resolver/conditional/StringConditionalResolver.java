package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record StringConditionalResolver(List<ConditionBranch<StringResolver>> branches, StringResolver elseCase) implements StringResolver {

    @Override
    public String resolveString() {
        for (var branch : branches) {
            if (branch.condition().resolveBoolean()) {
                return branch.result().resolveString();
            }
        }

        return elseCase.resolveString();
    }

}
