package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;

public class FilmesFavoritosActivity //Activity que exibe os filmes favoritos do usuário
        extends AppCompatActivity //Herda da classe AppCompatActivity
        implements FilmesFavoritosContrato.FilmesFavoritosView { //Implementa a interface FilmesFavoritosContrato.FilmesFavoritosView

    ActivityFilmesFavoritosBinding binding; //Binding para o layout da activity
    private FilmesFavoritosContrato.FilmesFavoritosPresenter presenter; //Presenter da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Método chamado quando a activity é criada
        super.onCreate(savedInstanceState); //Chama o método da classe pai
        binding = ActivityFilmesFavoritosBinding.inflate(getLayoutInflater()); //Infla o layout da activity
        View view = binding.getRoot(); //Pega a view do binding
        setContentView(view); //Seta o layout da activity
        configuraAdapter(); //Configura o adapter do recycler view
        binding.voltarBtn.setOnClickListener(v -> { //Listener para o botão de voltar
            finish(); //Finaliza a activity
        });
        binding.theMovieDbLogo.setOnClickListener(v -> { //Listener para o logo do The Movie DB
            String url = "https://www.themoviedb.org/"; //Url do site do The Movie DB
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); //Abre o site do The Movie DB
            startActivity(intent); //Inicia a activity
        });
        presenter = new FilmesFavoritosPresenter(this); //Instancia o presenter da activity
        presenter.exibeFavoritos(binding.recyclerFavoritos); //Exibe os filmes favoritos
    }

    @Override
    public void recarregaActivity() { //Método para recarregar a activity
        Intent intent = getIntent(); //Pega a intent da activity
        finish(); //Finaliza a activity
        startActivity(intent); //Inicia a activity
    }

    @Override
    public void exibeListaVazia() { //Método para exibir a lista vazia
        binding.recyclerFavoritos.setVisibility(View.GONE); //Torna o recycler view invisível
        binding.imgFavNull.setVisibility(View.VISIBLE); //Torna a imagem de lista vazia visível
        binding.textFavNull.setVisibility(View.VISIBLE); //Torna o texto de lista vazia visível
        binding.voltarBtn.setVisibility(View.VISIBLE); //Torna o botão de voltar visível
    }

    @Override
    public void exibeMsg(String msg) { //Método para exibir uma mensagem
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show(); //Exibe a mensagem
        Log.d(TAG, msg); //Exibe a mensagem no log
    }

    private void configuraAdapter() { //Método para configurar o adapter do recycler view
        binding.recyclerFavoritos.setLayoutManager(new LinearLayoutManager(this)); //Seta o layout manager do recycler view
    }

}