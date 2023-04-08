package com.uniritter.filmespopulares.data.mapper;

import com.uniritter.filmespopulares.data.model.Filme;
import com.uniritter.filmespopulares.data.network.response.FilmeResponse;

import java.util.ArrayList;
import java.util.List;

//CLASSE QUE FAZ O PARSE DA RESPOSTA DA API PARA OS DADOS DE DOMÍNIO
//METODO OBTEM A LISTA DE FILMES RESPONSE E CONVERTE EM LISTA DE FILMES
public class FilmeMapper {
    public static List<Filme> deResponseParaDominio(List<FilmeResponse> listaFilmeResponse) {
        List<Filme> listaFilmes = new ArrayList<>();
        for (FilmeResponse filmeResponse : listaFilmeResponse) {
            final Filme filme = new Filme(filmeResponse.getTituloOriginal(), filmeResponse.getCaminhoPoster());
            listaFilmes.add(filme);
        }
        return listaFilmes;
    }
}
