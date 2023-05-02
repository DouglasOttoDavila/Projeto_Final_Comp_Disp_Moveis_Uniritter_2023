package com.uniritter.to100ideia.ui.listaFilmesEmBreve;

import com.uniritter.to100ideia.data.model.Filme;

import java.util.List;

// Contrato de interface para a ListaFilmesEmBreveActivity
public interface ListaFilmesEmBreveContrato {

    interface ListaFilmesEmBreveView {

            void mostraFilmes(List<Filme> filmes);
            void mostraErro();

    }

    interface ListaFilmesEmBrevePresenter {

        void obtemFilmesEmBreve();

        void destruirView();
    }
}
