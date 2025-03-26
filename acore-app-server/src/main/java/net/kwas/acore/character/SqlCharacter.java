package net.kwas.acore.character;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "acore_characters", value = "characters")
public record SqlCharacter(
    @Id
    long guid,
    long account,
    String name,
    long race,
    @Column("class")
    long chrClass,
    long gender,
    long level,
    long xp,
    long money,
    long online,
    long totaltime,
    long zone
) {
}
