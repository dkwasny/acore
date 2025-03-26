package net.kwas.acore.character;

import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<SqlCharacter, Long> {
}
