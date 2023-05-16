package com.uniritter.to100ideia.ui.listaFilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityListaFilmesBinding;

import java.util.List;

public class ListaFilmesActivity
        extends AppCompatActivity
        implements ListaFilmesContrato.ListaFilmesView,
        ListaFilmesAdapter.ItemFilmeClickListener {

    ActivityListaFilmesBinding binding;

    private ListaFilmesAdapter filmesAdapter;

    private ListaFilmesContrato.ListaFilmesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityListaFilmesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String endpoint = getIntent().getStringExtra("endpoint");

        presenter = new ListaFilmesPresenter(this);

        if (endpoint.equals("populares")) {
            setTitulo("Filmes Populares");
            setDescricao("Os filmes mais populares do momento de acordo com o site TheMovieDB.");
            presenter.obtemFilmesPopulares();
        } else if (endpoint.equals("top")) {
            setTitulo("Melhores Avaliações");
            setDescricao("Os filmes mais bem-avaliados de acordo com o site TheMovieDB.");
            presenter.obtemFilmesTop();
        } else if (endpoint.equals("recentes")) {
            setTitulo("Lançamentos Recentes");
            setDescricao("Os filmes mais recentes de acordo com o site TheMovieDB.");
            presenter.obtemFilmesEmBreve();
        }

        configuraAdapter();

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesAdapter(this);
        int numberOfColumns = 2;
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        binding.recyclerFilmes.setLayoutManager(gridLayoutManager);
        binding.recyclerFilmes.setAdapter(filmesAdapter);
    }

    public void setTitulo (String titulo) {
        binding.tituloLista.setText(titulo);
    }

    public void setDescricao (String descricao) {
        binding.detalhesTxt.setText(descricao);
    }

    @Override
    public void mostraFilmes(List<Filme> filmes) {
        filmesAdapter.setFilmes(filmes);
    }

    @Override
    public void mostraErro() {
        Toast.makeText(this,"Erro ao obter filmes!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destruirView();
    }

    @Override
    public void onItemFilmeClicado(Filme filme) {
        Intent intent = new Intent(this, DetalhesFilmeActivity.class);
        intent.putExtra(DetalhesFilmeActivity.EXTRA_FILME, filme);
        startActivity(intent);
    }
}