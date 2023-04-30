package com.uniritter.to100ideia.ui.listaFilmesPopulares;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityListaFilmesBinding;
import com.unirriter.api_filmes.databinding.ActivityMenuBinding;

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

        configuraAdapter();

        presenter = new ListaFilmesPresenter(this);
        presenter.obtemFilmes();

    }


    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesAdapter(this);
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