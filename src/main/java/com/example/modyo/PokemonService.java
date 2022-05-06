package com.example.modyo;

import com.example.modyo.model.PokemonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PokemonService {

    private final PokemonApi pokemonApi;
    private final WebClient http;

    public PokemonService(PokemonApi pokemonApi, WebClient http) {
        this.pokemonApi = pokemonApi;
        this.http = http;
    }


    public Flux<PokemonDetails> getBigListOfPokemon(String limit) {
        final long start = System.nanoTime();

        return pokemonApi.getAllPokemon(limit)
                .flatMap(s ->
                        Flux.fromIterable(s.getResults())
                                .parallel(2)// TODO  validar el parallel junto con el run
                                .runOn(Schedulers.parallel())
                                .flatMap(p -> getPokemonDetails(p.getUrl())
                                        .publishOn(Schedulers.boundedElastic())))
                //.subscribeOn(Schedulers.boundedElastic())
                //.log()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }

    public Flux<PokemonDetails> getListOfPokemon() {
        final long start = System.nanoTime();

        return pokemonApi.getAllPokemon()
                .flatMap(s ->
                        Flux.fromIterable(s.getResults())
                                .parallel(2)// TODO  validar el parallel junto con el run
                                .runOn(Schedulers.parallel())
                                .flatMap(p -> getPokemonDetails(p.getUrl())
                                        .publishOn(Schedulers.boundedElastic())))
                //.subscribeOn(Schedulers.boundedElastic())
                //.log()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }


    public Mono<PokemonDetails> getPokemon(String pokemonId) {
        return getPokemonDetails(pokemonId)
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }

    Mono<PokemonDetails> getPokemonDetails(String uriPokemon) {
        return this.http
                .get()
                .uri(uriPokemon)
                .retrieve()
                .bodyToMono(PokemonDetails.class);
    }
}
