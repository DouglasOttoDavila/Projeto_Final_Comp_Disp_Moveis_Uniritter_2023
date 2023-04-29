package com.uniritter.filmespopulares.data.network;

import com.uniritter.filmespopulares.data.network.response.FilmesResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmesService {
    @GET("movie/popular")
    Call<FilmesResult> obterFilmesPopulares(@Query("api_key") String chaveApi, @Query("language") String idioma);
}
