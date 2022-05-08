package com.example.modyo;

import com.example.modyo.dto.PokemonDetailsDto;
import com.example.modyo.model.PokemonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/pokemon")
@Slf4j
public record PokemonController(PokemonService pokemonService) {


    @GetMapping("bigList")
    public Flux<PokemonDetails> getBigListOfPokemon(@RequestParam(name = "limit") String limit) {
        return pokemonService.getBigListOfPokemon(limit);
    }

    @GetMapping("")
    public Flux<PokemonDetails> getListOfPokemon() {
        return pokemonService.getListOfPokemon();
    }

    @GetMapping("/specific")
    public Mono<PokemonDetails> getPokemonDetails(@RequestBody PokemonDetailsDto pokemonDetailsDto) {
        return pokemonService.getPokemon(pokemonDetailsDto.url());
    }

}
