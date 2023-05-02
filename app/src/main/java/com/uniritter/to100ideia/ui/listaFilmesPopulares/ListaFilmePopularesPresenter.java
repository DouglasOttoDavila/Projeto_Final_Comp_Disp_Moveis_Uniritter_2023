package com.uniritter.to100ideia.ui.listaFilmesPopulares;

import com.uniritter.to100ideia.data.mapper.FilmeMapper;
import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.data.network.ApiService;
import com.uniritter.to100ideia.data.network.response.FilmesResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmePopularesPresenter implements ListaFilmesPopularesContrato.ListaFilmesPresenter{

        private ListaFilmesPopularesContrato.ListaFilmesView view;
        public ListaFilmePopularesPresenter(ListaFilmesPopularesContrato.ListaFilmesView view) {
            this.view = view;
        }

        @Override
        public void obtemFilmesPopulares() {
                ApiService.getInstance()
                        .obterFilmesPopulares(ApiService.apiKeyConfig(), ApiService.apiLanguageConfig())
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
