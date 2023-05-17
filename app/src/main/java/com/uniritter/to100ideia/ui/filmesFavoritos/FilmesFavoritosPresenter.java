package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;
import java.util.List;

public class FilmesFavoritosPresenter
        implements FilmesFavoritosContrato.FilmesFavoritosPresenter {

    /*ActivityFilmesFavoritosBinding binding; // Define o binding para a view*/
    private FilmesFavoritosContrato.FilmesFavoritosView view; // Define a view

    public FilmesFavoritosPresenter(FilmesFavoritosContrato.FilmesFavoritosView view) {
        this.view = view;
    }

    public void exibeFavoritos(RecyclerView recyclerView) {
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
                                recyclerView.setAdapter(filmesFavoritosAdapter);
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
