package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;

import java.util.List;

public record NumericConditionalResolver(List<ConditionBranch<NumberResolver>> branches, NumberResolver elseCase) implements NumberResolver {

    @Override
    public double resolveNumber() {
        for (var branch : branches) {
            if (branch.condition().resolveBoolean()) {
                return branch.result().resolveNumber();
            }
        }

        return elseCase.resolveNumber();
    }

}
