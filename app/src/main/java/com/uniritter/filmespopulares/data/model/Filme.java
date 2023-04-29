package com.uniritter.filmespopulares.data.model;

import java.io.Serializable;

public class Filme implements Serializable {

    private final String titulo;
    private final String caminhoPoster;

    private final String descricaoFilme;

    public Filme(String titulo, String caminhoPoster, String descricaoFilme) {
        this.titulo = titulo;
        this.caminhoPoster = caminhoPoster;
        this.descricaoFilme = descricaoFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCaminhoPoster() { return caminhoPoster; }

    public String getDescricaoFilme() { return descricaoFilme; }
}
