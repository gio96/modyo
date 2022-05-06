package com.example.modyo;

import com.example.modyo.model.Pokemon;
import com.example.modyo.model.PokemonDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "pokemon-service", url = "${pokemon.service.url}")
@Component
public interface PokemonApi {

    @GetMapping("/")
    Flux<Pokemon> getAllPokemon(@RequestParam(name = "limit") String limit);

    @GetMapping("")
    Flux<Pokemon> getAllPokemon();

    @GetMapping("/{pokemonId}")
    Mono<PokemonDetails> getPokemonDetails(@PathVariable("pokemonId") String pokemondId);
}
