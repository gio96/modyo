package com.example.modyo;

import com.example.modyo.model.PokemonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PokemonService {

    private final PokemonApi pokemonApi;

    public PokemonService(PokemonApi pokemonApi) {
        this.pokemonApi = pokemonApi;
    }


    public Flux<PokemonDetails> getBigListOfPokemon() {
        final long start = System.nanoTime();

        return pokemonApi.getAllPokemon()
                .flatMap(s ->
                        Flux.fromIterable(s.getResults())
                                .parallel(2)// TODO  validar el parallel junto con el run
                                .runOn(Schedulers.parallel())
                                .flatMap(p -> pokemonApi.getPokemonDetails(
                                        p.getUrl().split("https://pokeapi.co/api/v2/pokemon/")[1])
                                        .publishOn(Schedulers.boundedElastic())))
                //.subscribeOn(Schedulers.boundedElastic())
                //.log()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }
}
