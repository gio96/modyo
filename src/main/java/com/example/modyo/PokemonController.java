package com.example.modyo;

import com.example.modyo.model.PokemonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/pokemon")
@Slf4j
public record PokemonController(PokemonService pokemonService) {


    @GetMapping("")
    public Flux<PokemonDetails> getBigListOfPokemon() {
        return pokemonService.getBigListOfPokemon();
    }

    /*@GetMapping("/{pokemonId}")
    public Mono<PokemonDetails> getPokemonDetails(@PathVariable("pokemonId") String pokemondId) {
        return pokemonService.getPokemon(pokemondId);
    }*/

    @GetMapping("details")
    public Mono<PokemonDetails> getPokemonDetails(@RequestParam(name = "url") String url) {
        return pokemonService.getPokemon(url);
    }
}
