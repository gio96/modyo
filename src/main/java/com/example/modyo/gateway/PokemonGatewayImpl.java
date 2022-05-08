package com.example.modyo.gateway;

import com.example.modyo.PokemonApi;
import com.example.modyo.model.Pokemon;
import com.example.modyo.model.PokemonDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public record PokemonGatewayImpl(PokemonApi pokemonApi, WebClient http) implements PokemonGateway {

    @Override
    public Flux<Pokemon> getAllPokemon(String limit) {
        return this.pokemonApi.getAllPokemon(limit);
    }

    @Override
    public Flux<Pokemon> getAllPokemon() {
        return this.pokemonApi.getAllPokemon();
    }

    @Override
    public Mono<PokemonDetails> queryPokemonDetails(String uriPokemon) {
        return this.http
                .get()
                .uri(uriPokemon)
                .retrieve()
                .bodyToMono(PokemonDetails.class);
    }
}
