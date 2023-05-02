package com.uniritter.to100ideia.ui.listaFilmesPopulares;

import com.uniritter.to100ideia.data.model.Filme;
import java.util.List;

// Contrato de interface para a ListaFilmesPopularesActivity
public interface ListaFilmesPopularesContrato {

    interface ListaFilmesView {

            void mostraFilmes(List<Filme> filmes);
            void mostraErro();

    }

    interface ListaFilmesPresenter {

        void obtemFilmesPopulares();

        void destruirView();
    }
}
