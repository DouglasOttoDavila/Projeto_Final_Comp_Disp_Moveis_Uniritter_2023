package com.uniritter.to100ideia.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uniritter.to100ideia.ui.listaFilmesPopulares.ListaFilmesActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityCadastroBinding;
import com.unirriter.api_filmes.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.filmesPopularesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ListaFilmesActivity.class);
            }
        });

        binding.filmesRecentesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ListaFilmesActivity.class);
            }
        });

        binding.emBreveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ListaFilmesActivity.class);
            }
        });

    }
    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}