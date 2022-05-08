package com.example.modyo.gateway;

import com.example.modyo.model.Pokemon;
import com.example.modyo.model.PokemonDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonGateway {

    Flux<Pokemon> getAllPokemon(String limit);

    Flux<Pokemon> getAllPokemon();

    Mono<PokemonDetails> queryPokemonDetails(String uriPokemon);
}
