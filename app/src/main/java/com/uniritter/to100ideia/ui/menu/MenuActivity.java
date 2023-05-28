package com.uniritter.to100ideia.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.filmesFavoritos.FilmesFavoritosActivity;
import com.uniritter.to100ideia.ui.listaFilmes.ListaFilmesActivity;
import com.uniritter.to100ideia.ui.login.LoginActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityMenuBinding;

public class MenuActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMenuBinding binding; //Binding para o layout da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.navView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);


        // Inflate the navigation drawer menu layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, binding.navView.getMenu());

        // Set click listener for the FAB
        binding.fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open/close the navigation drawer
                if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                    binding.drawerLayout.closeDrawer(binding.navView);
                } else {
                    binding.drawerLayout.openDrawer(binding.navView);
                }
            }
        });

        binding.filmesPopularesBtn.setOnClickListener(v -> { //Ao clicar no botão de filmes populares, inicia a activity de lista de filmes
            /*Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class); //Cria um intent para a activity de lista de filmes
            intent.putExtra("endpoint", "populares"); //Passa o endpoint para a activity de lista de filmes
            startActivity(intent);*/
            openNewActivity("populares");
        });

        binding.filmesTopBtn.setOnClickListener(v -> {
            /*Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class);
            intent.putExtra("endpoint", "top");
            startActivity(intent);*/
            openNewActivity("top");
        });

        binding.filmesEmBreveBtn.setOnClickListener(v -> {
            /*Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class);
            intent.putExtra("endpoint", "recentes");
            startActivity(intent);*/
            openNewActivity("recentes");
        });

        binding.meusFavoritosTxt.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, FilmesFavoritosActivity.class);
            /*intent.putExtra("endpoint", "recentes");*/
            startActivity(intent);
        });

        binding.userEmail.setText(sharedPreferences.getString("email", ""));

        binding.sairBtn.setOnClickListener(v -> {
            exitApp();
        });

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    public void openNewActivity(String endpoint) {
        Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class); //Cria um intent para a activity de lista de filmes
        intent.putExtra("endpoint", endpoint); //Passa o endpoint para a activity de lista de filmes
        startActivity(intent);
    }

    public void exitApp() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        // Clear the user session data from Shared Preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        // Finish the current activity and start the LoginActivity
        finish();
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        // Do nothing - this will block the "back" button in this activity
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation menu item selection
        int itemId = item.getItemId();

        // Handle each menu item click based on its ID
        switch (itemId) {
            case R.id.menu_item1:
                // Handle menu item 1 click
                // For example, start a new activity
                startActivity(new Intent(MenuActivity.this, FilmesFavoritosActivity.class));
                break;

            case R.id.menu_item2:
                // Handle menu item 2 click
                // For example, show a fragment
            /*Intent intent = new Intent(MenuActivity.this, ListaFilmesActivity.class); //Cria um intent para a activity de lista de filmes
            intent.putExtra("endpoint", "populares"); //Passa o endpoint para a activity de lista de filmes
            startActivity(intent);*/
                openNewActivity("populares");
                break;

            case R.id.menu_item3:
                // Handle menu item 3 click
                // For example, display a toast message
                openNewActivity("top");
                break;

            case R.id.menu_item4:
                // Handle menu item 3 click
                // For example, display a toast message
                openNewActivity("recentes");
                break;

            case R.id.menu_item5:
                // Handle menu item 3 click
                // For example, display a toast message
                exitApp();
                break;
        }

        // Close the navigation drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}