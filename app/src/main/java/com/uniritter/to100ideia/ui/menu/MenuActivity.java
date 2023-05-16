package com.uniritter.to100ideia.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.listaFilmes.ListaFilmesActivity;
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

        binding.filmesPopularesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class);
            intent.putExtra("endpoint", "populares");
            startActivity(intent);
        });

        binding.filmesTopBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class);
            intent.putExtra("endpoint", "top");
            startActivity(intent);
        });

        binding.filmesEmBreveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class);
            intent.putExtra("endpoint", "recentes");
            startActivity(intent);
        });

        binding.userEmail.setText(sharedPreferences.getString("email", ""));

        binding.sairBtn.setOnClickListener(v -> {
            // Clear the user session data from Shared Preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            // Finish the current activity and start the LoginActivity
            finish();
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
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

}