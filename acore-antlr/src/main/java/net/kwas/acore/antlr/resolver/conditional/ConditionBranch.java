package net.kwas.acore.antlr.resolver.conditional;

import net.kwas.acore.antlr.resolver.BooleanResolver;
import net.kwas.acore.antlr.resolver.StringResolver;

public record ConditionBranch<T extends StringResolver>(BooleanResolver condition, T result) {
}
