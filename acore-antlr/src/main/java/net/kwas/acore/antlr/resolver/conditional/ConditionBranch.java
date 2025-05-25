package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.StringResolver;

public record ConditionBranch<T extends StringResolver>(NumberResolver condition, T result) {
}
