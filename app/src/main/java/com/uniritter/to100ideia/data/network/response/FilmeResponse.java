package com.uniritter.to100ideia.data.network.response;

import com.squareup.moshi.Json;

//CLASSE QUE MAPEIA O RESPONSE DA API
public class FilmeResponse {

    @Json(name = "poster_path") //nome do campo no json
    private final String caminhoPoster;

    @Json(name = "title")
    private final String titulo;

    @Json(name = "overview")
    private final String descricaoFilme;

    public FilmeResponse(String caminhoPoster, String titulo, String descricaoFilme) {
        this.caminhoPoster = caminhoPoster;
        this.titulo = titulo;
        this.descricaoFilme = descricaoFilme;
    }

    public String getCaminhoPoster() {
        return caminhoPoster;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricaoFilme() { return descricaoFilme; }
}
