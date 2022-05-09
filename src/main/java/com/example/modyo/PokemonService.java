package com.example.modyo;

import com.example.modyo.exception.PokemonException;
import com.example.modyo.gateway.PokemonGateway;
import com.example.modyo.model.Detail;
import com.example.modyo.model.Pokemon;
import com.example.modyo.model.PokemonDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonService {

    private final PokemonGateway pokemonGateway;

    @Cacheable("bigPokemon")
    public Flux<PokemonDetails> getBigListOfPokemon(String limit) {
        return getFluxPokemonDetails(pokemonGateway.getAllPokemon(limit));
    }


    @Cacheable("listPokemon")
    public Flux<PokemonDetails> getListOfPokemon() {
        return getFluxPokemonDetails(pokemonGateway.getAllPokemon());
    }


    @Cacheable("getPokemon")
    public Mono<PokemonDetails> getPokemon(String pokemonId) {
        return pokemonGateway.queryPokemonDetails(pokemonId)
                .cache()
                .switchIfEmpty(Mono.error(PokemonException.Type.POKEMON_NOT_FOUND.build()));
    }

    private Flux<PokemonDetails> getFluxPokemonDetails(Flux<Pokemon> pokemon) {
        final long start = System.nanoTime();

        return pokemon
                .flatMap(s ->
                        getRequestPokemonDetails(s.getResults()))
                .cache()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }

    private ParallelFlux<PokemonDetails> getRequestPokemonDetails(List<Detail> results) {
        return Flux.fromIterable(results)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .flatMap(p -> pokemonGateway.queryPokemonDetails(p.getUrl())
                        .publishOn(Schedulers.boundedElastic()));
    }

}
