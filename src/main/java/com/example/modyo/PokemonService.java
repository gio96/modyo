package com.example.modyo;

import com.example.modyo.model.PokemonDetails;
import com.example.modyo.model.ResultsDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
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


    @Cacheable("bigPokemon")
    public Flux<PokemonDetails> getBigListOfPokemon(String limit) {
        final long start = System.nanoTime();

        return pokemonApi.getAllPokemon(limit)
                .flatMap(s ->
                        getAllPokemonDetails(s.getResults()))
                .cache()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }


    @Cacheable("listPokemon")
    public Flux<PokemonDetails> getListOfPokemon() {
        final long start = System.nanoTime();

        return pokemonApi.getAllPokemon()
                .flatMap(s ->
                        getAllPokemonDetails(s.getResults()))
                .cache()
                .doOnNext(i -> log.info(String.valueOf(i)))
                .doFinally(endType -> System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " miliseconds"))
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }


    @Cacheable("getPokemon")
    public Mono<PokemonDetails> getPokemon(String pokemonId) {
        return queryPokemonDetails(pokemonId)
                .cache()
                .onErrorResume(throwable -> Mono.just(new PokemonDetails()));
    }

    private ParallelFlux<PokemonDetails> getAllPokemonDetails(List<ResultsDetails> results) {
        return Flux.fromIterable(results)
                .parallel(2)// TODO  validar el parallel junto con el run
                .runOn(Schedulers.parallel())
                .flatMap(p -> queryPokemonDetails(p.getUrl())
                        .publishOn(Schedulers.boundedElastic()));
    }



    private Mono<PokemonDetails> queryPokemonDetails(String uriPokemon) {
        return this.http
                .get()
                .uri(uriPokemon)
                .retrieve()
                .bodyToMono(PokemonDetails.class);
    }
}
