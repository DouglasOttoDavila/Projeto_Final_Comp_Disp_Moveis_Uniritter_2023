package com.uniritter.to100ideia.ui.menu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.uniritter.to100ideia.ui.listaFilmesPopulares.ListaFilmesActivity;
import com.uniritter.to100ideia.ui.login.LoginActivity;
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

        binding.sairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the user session data from Shared Preferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Finish the current activity and start the LoginActivity
                finish();
                acessaActivity(LoginActivity.class);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do nothing - this will block the "back" button in this activity
    }

    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}