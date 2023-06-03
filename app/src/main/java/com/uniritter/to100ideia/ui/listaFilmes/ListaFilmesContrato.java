package com.uniritter.to100ideia.ui.listaFilmes;

import com.uniritter.to100ideia.data.model.Filme;

import java.util.List;

public interface ListaFilmesContrato {

    interface ListaFilmesView {

        void mostraFilmes(List<Filme> filmes);
        void mostraErro();
        void updateView(String titulo, String descricao);
    }

    interface ListaFilmesPresenter {
        void obtemFilmesPopulares();
        void obtemFilmesTop();
        void obtemFilmesEmBreve();
        void destruirView();
        void setEndpoint(String endpoint);

    }
}
