package com.uniritter.to100ideia.data.network.response;

import com.squareup.moshi.Json;
import java.util.List;

public class FilmesResult { // Classe que representa o resultado da requisição
    @Json(name = "results") // Nome do atributo no JSON
    private final List<FilmeResponse> resultadoFilmes; // Lista de filmes

    public FilmesResult(List<FilmeResponse> resultadoFilmes) { // Construtor da classe
        this.resultadoFilmes = resultadoFilmes; // Atribui a lista de filmes
    }

    public List<FilmeResponse> getResultadoFilmes() { // Método que retorna a lista de filmes
        return resultadoFilmes;
    }
}
