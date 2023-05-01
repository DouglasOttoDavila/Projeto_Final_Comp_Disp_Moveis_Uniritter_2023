package com.uniritter.to100ideia.data.mapper;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.data.network.response.FilmeResponse;
import java.util.ArrayList;
import java.util.List;

//CLASSE QUE FAZ O PARSE DA RESPOSTA DA API PARA OS DADOS DE DOMÍNIO
//METODO OBTEM A LISTA DE FILMES RESPONSE E CONVERTE EM LISTA DE FILMES
public class FilmeMapper {
    //Método que converte a lista de filmes response em lista de filmes
    //Recebe a lista de filmes response como parâmetro e retorna a lista de filmes
    public static List<Filme> deResponseParaDominio(List<FilmeResponse> listaFilmeResponse) {
        List<Filme> listaFilmes = new ArrayList<>();
        for (FilmeResponse filmeResponse : listaFilmeResponse) {
            final Filme filme = new Filme(filmeResponse.getTitulo(), filmeResponse.getCaminhoPoster(), filmeResponse.getDescricaoFilme());
            listaFilmes.add(filme);
        }
        return listaFilmes;
    }
}
