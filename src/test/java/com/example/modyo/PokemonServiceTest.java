package com.example.modyo;

import com.example.modyo.exception.PokemonException;
import com.example.modyo.gateway.PokemonGateway;
import com.example.modyo.model.Detail;
import com.example.modyo.model.Pokemon;
import com.example.modyo.model.PokemonDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonGateway pokemonGateway;

    @InjectMocks
    private PokemonService pokemonService;


    @Test
    public void testGetAllBigListPokemon() {

        var pokemonTest = new Pokemon(List.of(new Detail("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")));

        var pokemonDetail = new PokemonDetails();

        when(pokemonGateway.getAllPokemon(anyString())).thenReturn(Flux.just(pokemonTest));

        when(pokemonGateway.queryPokemonDetails(anyString())).thenReturn(Mono.just(pokemonDetail));

        StepVerifier.create(pokemonService.getBigListOfPokemon(anyString()))
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonGateway, times(1)).getAllPokemon(anyString());
        verify(pokemonGateway, times(1)).queryPokemonDetails(anyString());
    }

    @Test
    public void testGetListPokemon() {

        var pokemonTest = new Pokemon(List.of(new Detail("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")));

        var pokemonDetail = new PokemonDetails();

        when(pokemonGateway.getAllPokemon()).thenReturn(Flux.just(pokemonTest));

        when(pokemonGateway.queryPokemonDetails(anyString())).thenReturn(Mono.just(pokemonDetail));

        StepVerifier.create(pokemonService.getListOfPokemon())
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonGateway, times(1)).getAllPokemon();
        verify(pokemonGateway, times(1)).queryPokemonDetails(anyString());
    }

    @Test
    public void testGetPokemon() {

        var pokemonDetail = new PokemonDetails();

        when(pokemonGateway.queryPokemonDetails(anyString())).thenReturn(Mono.just(pokemonDetail));

        StepVerifier.create(pokemonService.getPokemon(anyString()))
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonGateway, times(1)).queryPokemonDetails(anyString());
    }

    @Test
    public void testGetPokemonError() {

        when(pokemonGateway.queryPokemonDetails(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(pokemonService.getPokemon(anyString()))
                .expectErrorMessage(PokemonException.Type.POKEMON_NOT_FOUND.getMessage())
                .verify();

    }

}
