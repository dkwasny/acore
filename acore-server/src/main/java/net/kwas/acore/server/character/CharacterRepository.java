package net.kwas.acore.server.character;

import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<SqlCharacter, Long> {
}
