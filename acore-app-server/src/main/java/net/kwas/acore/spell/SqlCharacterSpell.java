package net.kwas.acore.spell;

import org.springframework.data.relational.core.mapping.Column;

public record SqlCharacterSpell(
    long guid,
    long spell,
    @Column("specMask")
    long specMask
) {
}
