package com.example.modyo;

import com.example.modyo.gateway.PokemonGatewayImpl;
import com.example.modyo.model.Detail;
import com.example.modyo.model.Pokemon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonGatewayImplTest {

    @Mock
    private PokemonApi pokemonApi;

    @InjectMocks
    private PokemonGatewayImpl pokemonGatewayImpl;


    @Test
    public void testGetAllPokemon() {

        var pokemonTest = new Pokemon(Arrays.asList(new Detail("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                , new Detail("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")));

        when(pokemonApi.getAllPokemon()).thenReturn(Flux.just(pokemonTest));

        StepVerifier.create(pokemonGatewayImpl.getAllPokemon())
                .expectNext(pokemonTest)
                .verifyComplete();

        verify(pokemonApi, times(1)).getAllPokemon();
    }

    @Test
    public void testGetAllPokemonWithLimit() {

        var pokemonTest = new Pokemon(Arrays.asList(new Detail("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                , new Detail("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")));

        when(pokemonApi.getAllPokemon(anyString())).thenReturn(Flux.just(pokemonTest));

        StepVerifier.create(pokemonGatewayImpl.getAllPokemon(anyString()))
                .expectNext(pokemonTest)
                .verifyComplete();

        verify(pokemonApi, times(1)).getAllPokemon(anyString());
    }


    @Test
    public void testGetAllPokemonEmptyList() {
        when(pokemonApi.getAllPokemon()).thenReturn(Flux.empty());

        StepVerifier.create(pokemonGatewayImpl.getAllPokemon())
                .expectNext()
                .verifyComplete();

        verify(pokemonApi, times(1)).getAllPokemon();
    }

}
