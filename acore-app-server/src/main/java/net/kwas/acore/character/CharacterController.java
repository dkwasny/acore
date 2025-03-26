package net.kwas.acore.character;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CharacterController {

    private final CharacterDatabase db;

    public CharacterController(CharacterDatabase db) {
        this.db = db;
    }

    @GetMapping("/api/character")
    public Collection<Character> getCharacters() {
        return db.getCharacters();
    }

    @GetMapping("/api/character/{id}")
    public Character getCharacter(@PathVariable long id) {
        return db.getCharacter(id);
    }

}
