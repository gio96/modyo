package com.example.modyo;

import com.example.modyo.dto.PokemonDetailsDto;
import com.example.modyo.exception.PokemonException;
import com.example.modyo.model.PokemonDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController pokemonController;


    @Test
    public void testGetAllBigListPokemon() {

        var pokemonDetail = new PokemonDetails();

        when(pokemonService.getBigListOfPokemon(anyString())).thenReturn(Flux.just(pokemonDetail));

        StepVerifier.create(pokemonController.getBigListOfPokemon(anyString()))
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonService, times(1)).getBigListOfPokemon(anyString());
    }

    @Test
    public void testGetListPokemon() {

        var pokemonDetail = new PokemonDetails();

        when(pokemonService.getListOfPokemon())
                .thenReturn(Flux.just(pokemonDetail));

        StepVerifier.create(pokemonController.getListOfPokemon())
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonService, times(1)).getListOfPokemon();
    }

    @Test
    public void testGetPokemon() {

        var pokemonDetail = new PokemonDetails();

        var dto = new PokemonDetailsDto("test");

        when(pokemonService.getPokemon(anyString())).thenReturn(Mono.just(pokemonDetail));

        StepVerifier.create(pokemonController.getPokemonDetails(dto))
                .expectNext(pokemonDetail)
                .verifyComplete();

        verify(pokemonService, times(1)).getPokemon(anyString());
    }

    @Test
    public void testGetPokemonErrorDtoUrlNull() {
        var dto = new PokemonDetailsDto(null);

        StepVerifier.create(pokemonController.getPokemonDetails(dto))
                .expectErrorMessage(PokemonException.Type.POKEMON_NOT_ANSWER.getMessage())
                .verify();

    }

    @Test
    public void testGetPokemonErrorDtoUrlBlank() {
        var dto = new PokemonDetailsDto("");

        StepVerifier.create(pokemonController.getPokemonDetails(dto))
                .expectErrorMessage(PokemonException.Type.POKEMON_NOT_ANSWER.getMessage())
                .verify();

    }

}
