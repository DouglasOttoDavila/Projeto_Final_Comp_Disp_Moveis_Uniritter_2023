package com.uniritter.to100ideia.ui.listaFilmes;

import com.uniritter.to100ideia.data.mapper.FilmeMapper;
import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.data.network.ApiService;
import com.uniritter.to100ideia.data.network.response.FilmesResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesPresenter // Classe que implementa a interface ListaFilmesContrato.ListaFilmesPresenter
        implements ListaFilmesContrato.ListaFilmesPresenter { // Implementa a interface ListaFilmesContrato.ListaFilmesPresenter

    private ListaFilmesContrato.ListaFilmesView view; // Instancia a interface ListaFilmesContrato.ListaFilmesView

    public ListaFilmesPresenter(ListaFilmesContrato.ListaFilmesView view) { // Construtor da classe
        this.view = view;
    }

    @Override
    public void configuraAdapter() { // Método que configura o adapter da view
        view.configuraAdapter();
    }

    @Override
    public void obtemFilmesPopulares() { // Método que faz a chamada da API e retorna os filmes populares para a view
        ApiService.getInstance()// Instancia o serviço da API
                .obterFilmesPopulares(ApiService.apiKeyConfig(), ApiService.apiLanguageConfig())  // Chama o método que retorna os filmes populares
                .enqueue(new Callback<FilmesResult>() { // Enfileira a chamada da API
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) { // Método que trata a resposta da API
                        if (response.isSuccessful()) { // Verifica se a resposta da API foi bem sucedida
                            final List<Filme> listaFilmes = FilmeMapper // Mapeia a resposta da API para o modelo de domínio
                                    .deResponseParaDominio(response.body() // Corpo da resposta da API
                                            .getResultadoFilmes()); // Lista de filmes da resposta da API
                            view.mostraFilmes(listaFilmes); // Chama o método da view que mostra os filmes
                        } else {
                            view.mostraErro(); // Chama o método da view que mostra o erro
                        }
                    }
                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) { // Método que trata o erro da API
                        view.mostraErro(); // Chama o método da view que mostra o erro
                    }
                });
    }

    @Override public void setEndpoint(String endpoint) { // Método que configura o endpoint da API
        switch (endpoint) {
            case "populares":
                view.updateView("Filmes Populares", "Os filmes mais populares do momento de acordo com o site TheMovieDB."); // Chama o método da view que atualiza o título e a descrição
                obtemFilmesPopulares(); // Chama o método que faz a chamada da API e retorna os filmes populares para a view
                break;
            case "top":
                view.updateView("Melhores Avaliações", "Os filmes mais bem-avaliados de acordo com o site TheMovieDB."); // Chama o método da view que atualiza o título e a descrição
                obtemFilmesTop(); // Chama o método que faz a chamada da API e retorna os filmes populares para a view
                break;
            case "recentes":
                view.updateView("Lançamentos Recentes", "Os filmes mais recentes de acordo com o site TheMovieDB."); // Chama o método da view que atualiza o título e a descrição
                obtemFilmesEmBreve(); // Chama o método que faz a chamada da API e retorna os filmes populares para a view
                break;
        }
    }

    @Override
    public void obtemFilmesEmBreve() { // Método que faz a chamada da API e retorna os filmes populares para a view
        ApiService.getInstance()// Instancia o serviço da API
                .obterFilmesEmBreve(ApiService.apiKeyConfig(), ApiService.apiLanguageConfig())  // Chama o método que retorna os filmes populares
                .enqueue(new Callback<FilmesResult>() { // Enfileira a chamada da API
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) { // Método que trata a resposta da API
                        if (response.isSuccessful()) { // Verifica se a resposta da API foi bem sucedida
                            final List<Filme> listaFilmes = FilmeMapper // Mapeia a resposta da API para o modelo de domínio
                                    .deResponseParaDominio(response.body() // Corpo da resposta da API
                                            .getResultadoFilmes()); // Lista de filmes da resposta da API
                            view.mostraFilmes(listaFilmes); // Chama o método da view que mostra os filmes
                        } else {
                            view.mostraErro(); // Chama o método da view que mostra o erro
                        }
                    }
                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) { // Método que trata o erro da API
                        view.mostraErro(); // Chama o método da view que mostra o erro
                    }
                });
    }

    @Override
    public void obtemFilmesTop() { // Método que faz a chamada da API e retorna os filmes populares para a view
        ApiService.getInstance()// Instancia o serviço da API
                .obterFilmesTop(ApiService.apiKeyConfig(), ApiService.apiLanguageConfig())  // Chama o método que retorna os filmes populares
                .enqueue(new Callback<FilmesResult>() { // Enfileira a chamada da API
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) { // Método que trata a resposta da API
                        if (response.isSuccessful()) { // Verifica se a resposta da API foi bem sucedida
                            final List<Filme> listaFilmes = FilmeMapper // Mapeia a resposta da API para o modelo de domínio
                                    .deResponseParaDominio(response.body() // Corpo da resposta da API
                                            .getResultadoFilmes()); // Lista de filmes da resposta da API
                            view.mostraFilmes(listaFilmes); // Chama o método da view que mostra os filmes
                        } else {
                            view.mostraErro(); // Chama o método da view que mostra o erro
                        }
                    }
                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) { // Método que trata o erro da API
                        view.mostraErro(); // Chama o método da view que mostra o erro
                    }
                });
    }

    @Override
    public void destruirView() { // Método que destrói a view para evitar memory leak (vazamento de memória)
        view = null;
    }

}
