package com.uniritter.to100ideia.ui.listaFilmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmeActivity;
import com.uniritter.to100ideia.ui.menu.MenuActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.FragmentListaFilmesBinding;
import java.util.List;

public class ListaFilmesFragment // Fragmento que exibe a lista de filmes
        extends Fragment // Herda de Fragment
        implements ListaFilmesContrato.ListaFilmesView, // Implementa a interface ListaFilmesContrato.ListaFilmesView
        ListaFilmesAdapter.ItemFilmeClickListener { // Implementa a interface ListaFilmesAdapter.ItemFilmeClickListener

    private ListaFilmesAdapter filmesAdapter; // Adapter que exibe a lista de filmes
    private ListaFilmesContrato.ListaFilmesPresenter presenter; // Presenter que controla a lógica da lista de filmes
    FragmentListaFilmesBinding binding; // Binding do fragmento
    MenuActivity menuActivity = new MenuActivity(); // Activity do menu

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Método chamado quando o fragmento é criado
        Log.d("onCreateView", "onCreateView!"); // Log
        return inflater.inflate(R.layout.fragment_lista_filmes, container, false); // Retorna o layout inflado
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) { // Método chamado quando a view é criada
        super.onViewCreated(view, savedInstanceState); // Chama o método da superclasse
        presenter = new ListaFilmesPresenter(this); // Instancia o presenter
    }

    @Override
    public void onStart() { // Método chamado quando o fragmento é iniciado
        super.onStart(); // Chama o método da superclasse
        Log.d("onStart", "onStart!"); // Log
        String endpoint = getArguments().getString("endpoint"); // Obtém o endpoint passado como argumento
        presenter.setEndpoint(endpoint); // Configura o endpoint no presenter
        presenter.configuraAdapter(); // Configura o adapter
    }

    @Override
    public void onResume() { // Método chamado quando o fragmento é retomado
        super.onResume(); // Chama o método da superclasse
        Log.d("onResume", "onResume!"); // Log
    }

    @Override
    public void onPause() { // Método chamado quando o fragmento é pausado
        super.onPause(); // Chama o método da superclasse
        Log.d("onPause", "onPause!"); // Log
    }

    @Override
    public void configuraAdapter() { // Método que configura o adapter
        filmesAdapter = new ListaFilmesAdapter(this); // Instancia o adapter
        int numberOfColumns = 2; // Número de colunas
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), numberOfColumns); // Layout manager
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_filmes); // RecyclerView
        recyclerView.setLayoutManager(gridLayoutManager); // Configura o layout manager
        recyclerView.setAdapter(filmesAdapter); // Configura o adapter
        Log.d("configuraAdapter()", "Adapter Configurado!"); // Log
    }

    @Override
    public void updateView(String titulo, String descricao) { // Método que atualiza a view
        TextView tituloTextView = getView().findViewById(R.id.tituloLista); // TextView do título
        TextView descricaoTextView = getView().findViewById(R.id.detalhesTxt); // TextView da descrição
        tituloTextView.setText(titulo); // Configura o título
        descricaoTextView.setText(descricao); // Configura a descrição
    }

    @Override
    public void mostraFilmes(List<Filme> filmes) { // Método que exibe os filmes
        filmesAdapter.setFilmes(filmes);
    }

    @Override
    public void mostraErro() { // Método que exibe uma mensagem de erro
        Toast.makeText(getContext(), "Erro ao obter filmes!", Toast.LENGTH_LONG).show(); // Exibe a mensagem de erro
    }

    @Override
    public void onDestroy() { // Método chamado quando o fragmento é destruído
        super.onDestroy(); // Chama o método da superclasse
        presenter.destruirView(); // Destrói a view
    }

    @Override
    public void onItemFilmeClicado(Filme filme) { // Método chamado quando um item da lista de filmes é clicado
        Intent intent = new Intent(getContext(), DetalhesFilmeActivity.class); // Intent para a activity de detalhes do filme
        intent.putExtra(DetalhesFilmeActivity.EXTRA_FILME, filme); // Passa o filme como extra
        startActivity(intent); // Inicia a activity
    }

}
