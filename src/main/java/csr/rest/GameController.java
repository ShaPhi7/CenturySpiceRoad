package csr.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import csr.cli.GameSession;

@Component(value="GameController")
@RestController
public class GameController {

    @GetMapping("/game/{id}")
    public void getGame(@PathVariable String id, GameSession gameSession) {
        //TODO - placeholder
    }
}