package net.kwas.acore.server.character;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "acore_characters", value = "characters")
public record SqlCharacter(
    @Id
    long guid,
    long account,
    String name,
    int race,
    @Column("class")
    int chrClass,
    int gender,
    int level,
    long xp,
    long money,
    int online,
    long totaltime,
    int zone
) {
}
