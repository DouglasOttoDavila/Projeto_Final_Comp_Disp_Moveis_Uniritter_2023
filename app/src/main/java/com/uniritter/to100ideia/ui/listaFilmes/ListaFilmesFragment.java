package com.uniritter.to100ideia.ui.listaFilmes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.uniritter.to100ideia.ui.menu.MenuActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.FragmentListaFilmesBinding;

import java.util.List;

public class ListaFilmesFragment
        extends Fragment
        implements ListaFilmesContrato.ListaFilmesView,
        ListaFilmesAdapter.ItemFilmeClickListener {

    private ListaFilmesAdapter filmesAdapter;
    private ListaFilmesContrato.ListaFilmesPresenter presenter;

    FragmentListaFilmesBinding binding;

    MenuActivity menuActivity = new MenuActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout da activity através do binding
        Log.d("onCreateView", "onCreateView!");
        return inflater.inflate(R.layout.fragment_lista_filmes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ListaFilmesPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "onStart!");
        String endpoint = getArguments().getString("endpoint");
        presenter.setEndpoint(endpoint);
        configuraAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "onResume!");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "onPause!");
    }

    private void configuraAdapter() {
        filmesAdapter = new ListaFilmesAdapter(this);
        int numberOfColumns = 2;
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), numberOfColumns);
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_filmes);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(filmesAdapter);
        Log.e("configuraAdapter", "configuraAdapter!");
    }

    @Override
    public void updateView(String titulo, String descricao) {
        TextView tituloTextView = getView().findViewById(R.id.tituloLista);
        TextView descricaoTextView = getView().findViewById(R.id.detalhesTxt);
        tituloTextView.setText(titulo);
        descricaoTextView.setText(descricao);
    }

    @Override
    public void mostraFilmes(List<Filme> filmes) {
        filmesAdapter.setFilmes(filmes);
    }

    @Override
    public void mostraErro() {
        Toast.makeText(getContext(), "Erro ao obter filmes!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destruirView();
    }

    @Override
    public void onItemFilmeClicado(Filme filme) {
        Intent intent = new Intent(getContext(), DetalhesFilmeActivity.class);
        intent.putExtra(DetalhesFilmeActivity.EXTRA_FILME, filme);
        startActivity(intent);
    }

}
