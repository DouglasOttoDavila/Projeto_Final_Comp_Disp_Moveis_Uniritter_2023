package com.uniritter.to100ideia.ui.listaFilmesPopulares;

import com.uniritter.to100ideia.data.model.Filme;
import java.util.List;

// Contrato de interface para a ListaFilmesPopularesActivity
public interface ListaFilmesPopularesContrato { // Interface do contrato

    interface ListaFilmesView { // Interface da view

        void mostraFilmes(List<Filme> filmes); // Método que mostra os filmes
        void mostraErro(); // Método que mostra o erro

    }

    interface ListaFilmesPresenter { // Interface do presenter

        void obtemFilmesPopulares(); // Método que obtém os filmes populares
        void destruirView(); // Método que destrói a view para evitar memory leak (vazamento de memória)

    }
}
