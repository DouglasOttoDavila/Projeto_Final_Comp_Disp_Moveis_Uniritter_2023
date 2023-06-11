package com.uniritter.to100ideia.data.network;

import com.uniritter.to100ideia.data.network.response.FilmesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmesService { // Interface que define os métodos que serão utilizados para obter os dados da API
    @GET("movie/popular") // Define o endpoint que será utilizado para obter os filmes populares
    Call<FilmesResult> obterFilmesPopulares(@Query("api_key") String chaveApi, @Query("language") String idioma); // Define os parâmetros que serão utilizados na requisição

    @GET("movie/top_rated")
    Call<FilmesResult> obterFilmesTop(@Query("api_key") String chaveApi, @Query("language") String idioma);

    @GET("movie/upcoming")
    Call<FilmesResult> obterFilmesEmBreve(@Query("api_key") String chaveApi, @Query("language") String idioma);
}
