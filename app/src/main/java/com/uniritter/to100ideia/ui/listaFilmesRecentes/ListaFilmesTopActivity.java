package com.uniritter.to100ideia.ui.listaFilmesRecentes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.unirriter.api_filmes.databinding.ActivityListaFilmesRecentesBinding;

import java.util.List;

public class ListaFilmesTopActivity
        extends AppCompatActivity
        implements ListaFilmesTopContrato.ListaFilmesRecentesView,
        ListaFilmesTopAdapter.ItemFilmeClickListener {

    ActivityListaFilmesRecentesBinding binding;
    private ListaFilmesTopAdapter filmesAdapter;
    private ListaFilmesTopContrato.ListaFilmesRecentesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityListaFilmesRecentesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        configuraAdapter();

        presenter = new ListaFilmesTopPresenter(this);
        presenter.obtemFilmesRecentes();

    }


    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesTopAdapter(this);
        int numberOfColumns = 2;
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        binding.recyclerFilmes.setLayoutManager(gridLayoutManager);
        binding.recyclerFilmes.setAdapter(filmesAdapter);
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