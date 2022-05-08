package com.example.modyo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDetails {

    private Integer weight;
    private List<PokemonAbility> abilities;
    private List<PokemonType> types;
    private PokemonSprite sprites;
    private List<Detail> forms;
 }
