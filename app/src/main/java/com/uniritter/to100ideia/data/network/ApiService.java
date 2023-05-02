package com.uniritter.to100ideia.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {

    static String apiKey = "461b2ec0f0dc520e20da940100aefc68";
    static String language = "pt-BR";

    public static String apiKeyConfig() {
      return apiKey;
    }

    public static String apiLanguageConfig() {
        return language;
    }

    private static FilmesService INSTANCE;
    //SINGLETON DA API
    //https://www.devmedia.com.br/padrao-de-projeto-singleton-em-java/25797
    public static FilmesService getInstance() {

        if (INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(FilmesService.class);
        }

        return INSTANCE;

    }


}
