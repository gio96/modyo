package com.example.modyo.exception;

public class PokemonException extends RuntimeException {
    public enum Type {
        POKEMON_NOT_FOUND("La lista de pokemon no existe", 404);

        private final String message;
        private final Integer status;

        public PokemonException build() {
            return new PokemonException(this);
        }

        Type(String message, Integer status) {
            this.message = message;
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public Integer getStatus() {
            return status;
        }
    }

    private final PokemonException.Type type;

    private PokemonException(PokemonException.Type type) {
        super(type.message);
        this.type = type;
    }

    public PokemonException.Type getType() {
        return type;
    }
}
