package com.uniritter.to100ideia.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.filmesFavoritos.FilmesFavoritosActivity;

import com.uniritter.to100ideia.ui.listaFilmes.ListaFilmesFragment;
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

        binding.meusFavoritosTxt.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, FilmesFavoritosActivity.class);
            startActivity(intent);
        });

        binding.userEmail.setText(sharedPreferences.getString("email", ""));

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    public void openListaFilmesFragment(String endpoint) {

        Bundle bundle = new Bundle();//Cria um bundle para passar o endpoint para o fragment
        bundle.putString("endpoint", endpoint); //Passa o endpoint para o bundle

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("endpoint", endpoint);
        editor.apply();

        ListaFilmesFragment fragment = new ListaFilmesFragment(); //Cria um fragment para a lista de filmes
        fragment.setArguments(bundle); //Passa o bundle para o fragment

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); //Cria uma transação para o fragment. É necessário para que o fragment seja exibido
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit(); //Substitui o conteúdo do frameLayout pelo fragment
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
                startActivity(new Intent(MenuActivity.this, FilmesFavoritosActivity.class));
                break;

            case R.id.menu_item2:
                openListaFilmesFragment("populares");
                break;

            case R.id.menu_item3:
                openListaFilmesFragment("top");
                break;

            case R.id.menu_item4:
                openListaFilmesFragment("recentes");
                break;

            case R.id.menu_item5:
                exitApp();
                break;
        }

        // Close the navigation drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}