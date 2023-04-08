package com.uniritter.filmespopulares.ui.listaFilmes;
import com.uniritter.filmespopulares.data.model.Filme;
import java.util.List;

// Contrato de interface para a ListaFilmesActivity
public interface ListaFilmesContrato {

    interface ListaFilmesView {

            void mostraFilmes(List<Filme> filmes);
            void mostraErro();

    }

    interface ListaFilmesPresenter {

        void obtemFilmes();

        void destruirView();
    }
}
