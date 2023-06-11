package com.uniritter.to100ideia.data.model;

import java.io.Serializable;

public class Filme implements Serializable { // Serializable é uma interface que permite que o objeto seja convertido em uma sequência de bytes

    private final String titulo;
    private final String caminhoPoster;
    private final String descricaoFilme;

    public Filme(String titulo, String caminhoPoster, String descricaoFilme) { // Construtor da classe Filme
        this.titulo = titulo;
        this.caminhoPoster = caminhoPoster;
        this.descricaoFilme = descricaoFilme;
    }

    public String getTitulo() { return titulo; } // Método que retorna o título do filme

    public String getCaminhoPoster() { return caminhoPoster; } // Método que retorna o caminho do poster do filme

    public String getDescricaoFilme() { return descricaoFilme; } // Método que retorna a descrição do filme
}
