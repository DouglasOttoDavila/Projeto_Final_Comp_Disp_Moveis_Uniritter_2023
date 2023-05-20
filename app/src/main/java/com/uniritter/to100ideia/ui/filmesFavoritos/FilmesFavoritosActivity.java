package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniritter.to100ideia.ui.detalhesfilme.DetalhesFilmePresenter;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;
import java.util.List;

public class FilmesFavoritosActivity
        extends AppCompatActivity
        implements FilmesFavoritosContrato.FilmesFavoritosView {

    ActivityFilmesFavoritosBinding binding; //Binding para o layout da activity

    private FilmesFavoritosContrato.FilmesFavoritosPresenter presenter; //Presenter da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding
        binding = ActivityFilmesFavoritosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        configuraAdapter();

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        //Inicializa o presenter da activity
        presenter = new FilmesFavoritosPresenter(this);
        presenter.exibeFavoritos(binding.recyclerFavoritos); //Exibe os filmes favoritos

    }

    @Override
    public void recarregaActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    @Override
    public void exibeMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);
    }

    private void configuraAdapter() {
        binding.recyclerFavoritos.setLayoutManager(new LinearLayoutManager(this));
    }

}