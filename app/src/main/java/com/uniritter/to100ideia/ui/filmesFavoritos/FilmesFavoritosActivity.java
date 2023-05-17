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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;
import com.unirriter.api_filmes.databinding.ActivityMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class FilmesFavoritosActivity extends AppCompatActivity {

    ActivityFilmesFavoritosBinding binding; //Binding para o layout da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding
        binding = ActivityFilmesFavoritosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        RecyclerView FavoritosRecyclerView = findViewById(R.id.recycler_favoritos);
        FavoritosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserUid = auth.getCurrentUser().getUid();

        db.collection("usuarios").document(currentUserUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot userDocument = task.getResult();
                        if (userDocument.exists()) {
                            List<String> favoritos = (List<String>) userDocument.get("filmesFavoritos");
                            if (favoritos != null) {
                                FilmesFavoritosAdapter filmesFavoritosAdapter = new FilmesFavoritosAdapter(favoritos);
                                FavoritosRecyclerView.setAdapter(filmesFavoritosAdapter);
                            }
                        } else {
                            Log.d(TAG, "User document not found.");
                        }
                    } else {
                        Log.d(TAG, "Error getting user document: ", task.getException());
                    }
                });
    }



}