package com.uniritter.to100ideia.ui.listaFilmesRecentes;

import com.uniritter.to100ideia.data.model.Filme;

import java.util.List;

// Contrato de interface para a ListaFilmesTopActivity
public interface ListaFilmesTopContrato {

    interface ListaFilmesRecentesView {

            void mostraFilmes(List<Filme> filmes);
            void mostraErro();

    }

    interface ListaFilmesRecentesPresenter {

        void obtemFilmesRecentes();

        void destruirView();
    }
}
