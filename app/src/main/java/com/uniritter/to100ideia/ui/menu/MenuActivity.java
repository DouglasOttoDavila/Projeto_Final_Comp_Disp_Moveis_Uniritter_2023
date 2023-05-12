package com.uniritter.to100ideia.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uniritter.to100ideia.ui.listaFilmesEmBreve.ListaFilmesEmBreveActivity;
import com.uniritter.to100ideia.ui.listaFilmesPopulares.ListaFilmesPopularesActivity;
import com.uniritter.to100ideia.ui.listaFilmesTop.ListaFilmesTopActivity;
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

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding.filmesPopularesBtn.setOnClickListener(v -> acessaActivity(ListaFilmesPopularesActivity.class));
        binding.filmesTopBtn.setOnClickListener(v -> acessaActivity(ListaFilmesTopActivity.class));
        binding.filmesEmBreveBtn.setOnClickListener(v -> acessaActivity(ListaFilmesEmBreveActivity.class));

        binding.userEmail.setText(sharedPreferences.getString("email", ""));

        binding.sairBtn.setOnClickListener(v -> {
            // Clear the user session data from Shared Preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Finish the current activity and start the LoginActivity
            finish();
            acessaActivity(LoginActivity.class);
        });

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
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