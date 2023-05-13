package com.uniritter.to100ideia.ui.listaFilmesPopulares;

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
import com.unirriter.api_filmes.databinding.ActivityListaFilmesPopularesBinding;
import java.util.List;

public class ListaFilmesPopularesActivity
        extends AppCompatActivity
        implements ListaFilmesPopularesContrato.ListaFilmesView,
        ListaFilmesPopularesAdapter.ItemFilmeClickListener {

    ActivityListaFilmesPopularesBinding binding;
    private ListaFilmesPopularesAdapter filmesAdapter;
    private ListaFilmesPopularesContrato.ListaFilmesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityListaFilmesPopularesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitulo();
        configuraAdapter();

        presenter = new ListaFilmePopularesPresenter(this);
        presenter.obtemFilmesPopulares();

    }


    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesPopularesAdapter(this);
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
        binding.popularesTxt.setText("Filmes Populares");
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