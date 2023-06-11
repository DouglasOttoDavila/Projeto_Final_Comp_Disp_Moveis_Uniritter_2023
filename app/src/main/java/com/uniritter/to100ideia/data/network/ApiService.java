package com.uniritter.to100ideia.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService { //CLASSE QUE FAZ A CONEXÃO COM A API
    static String apiKey = "461b2ec0f0dc520e20da940100aefc68"; //API KEY
    static String language = "pt-BR"; //LINGUAGEM
    public static String apiKeyConfig() {
      return apiKey;
    }
    public static String apiLanguageConfig() {
        return language;
    }
    private static FilmesService INSTANCE; //INSTANCIA DA API PARA RETROFIT

    //SINGLETON DA API
    //https://www.devmedia.com.br/padrao-de-projeto-singleton-em-java/25797
    public static FilmesService getInstance() { //RETORNA A INSTANCIA DA API

        if (INSTANCE == null) { //Se a instancia for nula, cria uma nova instancia
            Retrofit retrofit = new Retrofit.Builder() //Cria uma nova instancia do retrofit
                    .baseUrl("https://api.themoviedb.org/3/") //URL BASE DA API
                    .addConverterFactory(MoshiConverterFactory.create()) //CONVERSOR DE JSON PARA OBJETO
                    .build(); //CRIA A INSTANCIA DO RETROFIT

            INSTANCE = retrofit.create(FilmesService.class); //CRIA A INSTANCIA DA API
        }
        return INSTANCE; //RETORNA A INSTANCIA DA API
    }
}
