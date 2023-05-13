package com.uniritter.to100ideia.ui.listaFilmesEmBreve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji2.text.EmojiCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.unirriter.api_filmes.databinding.ActivityListaFilmesEmBreveBinding;

import java.util.List;

public class ListaFilmesEmBreveActivity
        extends AppCompatActivity
        implements ListaFilmesEmBreveContrato.ListaFilmesEmBreveView,
        ListaFilmesEmBreveAdapter.ItemFilmeClickListener {

    ActivityListaFilmesEmBreveBinding binding;
    private ListaFilmesEmBreveAdapter filmesAdapter;
    private ListaFilmesEmBreveContrato.ListaFilmesEmBrevePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityListaFilmesEmBreveBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitulo();
        configuraAdapter();

        presenter = new ListaFilmesEmBrevePresenter(this);
        presenter.obtemFilmesEmBreve();

    }


    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesEmBreveAdapter(this);
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

    public void setTitulo () {
        binding.lancamentosRecentesTxt.setText("Lançamentos Recentes");
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