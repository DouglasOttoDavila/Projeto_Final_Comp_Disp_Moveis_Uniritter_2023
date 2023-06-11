package com.uniritter.to100ideia.data.network.response;

import androidx.emoji2.text.EmojiCompat;
import com.squareup.moshi.Json;
import java.util.Objects;

//CLASSE QUE MAPEIA O RESPONSE DA API
public class FilmeResponse {

    @Json(name = "poster_path") //nome do campo no json
    private final String caminhoPoster; //nome do atributo na classe

    @Json(name = "title") //nome do campo no json
    private final String titulo; //nome do atributo na classe

    @Json(name = "overview") //nome do campo no json
    private final String descricaoFilme; //nome do atributo na classe

    public FilmeResponse(String caminhoPoster, String titulo, String descricaoFilme) { // Método construtor da classe
        this.caminhoPoster = caminhoPoster;
        this.titulo = titulo;
        this.descricaoFilme = descricaoFilme;
    }

    public String getCaminhoPoster() { return caminhoPoster; } // Método get para o atributo caminhoPoster

    public String getTitulo() {
        return titulo;
    } // Método get para o atributo titulo

    public String getDescricaoFilme() { // Método get para o atributo descricaoFilme
        if (Objects.equals(descricaoFilme, "")) { // Verifica se a descrição do filme está vazia
            return "Ops!" + EmojiCompat.get().process("\ud83d\ude05") +"\nAinda não temos a descrição para este filme.";
        }
        return descricaoFilme;
    }
}
