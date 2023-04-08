package com.uniritter.filmespopulares.data.network.response;

import com.squareup.moshi.Json;

//CLASSE QUE MAPEIA O RESPONSE DA API
public class FilmeResponse {

    @Json(name = "poster_path") //nome do campo no json
    private final String caminhoPoster;
    @Json(name = "original_title")
    private final String tituloOriginal;

    public FilmeResponse(String caminhoPoster, String tituloOriginal) {
        this.caminhoPoster = caminhoPoster;
        this.tituloOriginal = tituloOriginal;
    }

    public String getCaminhoPoster() {
        return caminhoPoster;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }
}
