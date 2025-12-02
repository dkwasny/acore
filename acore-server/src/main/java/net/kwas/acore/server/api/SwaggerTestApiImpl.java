package net.kwas.acore.server.api;

import net.kwas.acore.server.model.Character;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SwaggerTestApiImpl implements SwaggerTestApi {

    @Override
    public List<Character> swaggerTestCharacterGet() {

        var retVal = List.of(
            new Character()
                .race(Character.RaceEnum.BLOOD_ELF)
                .id(123L)
                .accountId(12334L)
                .zone("The swagzone"),
            new Character()
                .race(Character.RaceEnum.DRANEI)
                .id(321L)
                .accountId(12334L)
                .zone("Home home"),
            new Character()
                .race(Character.RaceEnum.ORC)
                .id(1L)
                .accountId(12334L)
                .zone("Somewhere")
        );

        return retVal;
    }

}
