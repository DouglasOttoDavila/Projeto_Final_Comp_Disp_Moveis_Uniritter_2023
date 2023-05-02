package com.uniritter.to100ideia.ui.listaFilmesRecentes;

import com.uniritter.to100ideia.data.mapper.FilmeMapper;
import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.data.network.ApiService;
import com.uniritter.to100ideia.data.network.response.FilmesResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesTopPresenter implements ListaFilmesTopContrato.ListaFilmesRecentesPresenter{

        private ListaFilmesTopContrato.ListaFilmesRecentesView view;
        public ListaFilmesTopPresenter(ListaFilmesTopContrato.ListaFilmesRecentesView view) {
            this.view = view;
        }

        @Override
        public void obtemFilmesRecentes() {
                ApiService.getInstance()
                        .obterFilmesRecentes("461b2ec0f0dc520e20da940100aefc68", "pt-BR")
                        .enqueue(new Callback<FilmesResult>() {
                                @Override
                                public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                                        if (response.isSuccessful()) {
                                                final List<Filme> listaFilmes = FilmeMapper
                                                        .deResponseParaDominio(response.body()
                                                                .getResultadoFilmes());
                                                view.mostraFilmes(listaFilmes);
                                        } else {
                                                view.mostraErro();
                                        }
                                }

                                @Override
                                public void onFailure(Call<FilmesResult> call, Throwable t) {
                                    view.mostraErro();
                                }
                        });
        }

    @Override
    public void destruirView() {
        view = null;
    }
}
