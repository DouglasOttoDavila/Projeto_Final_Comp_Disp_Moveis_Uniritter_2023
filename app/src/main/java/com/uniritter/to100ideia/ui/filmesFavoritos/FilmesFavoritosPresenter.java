package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;
import java.util.List;

public class FilmesFavoritosPresenter
        implements FilmesFavoritosContrato.FilmesFavoritosPresenter {

    private FilmesFavoritosContrato.FilmesFavoritosView view; // Define a view
    private String url;
    int index;

    public FilmesFavoritosPresenter(FilmesFavoritosContrato.FilmesFavoritosView view) {
        this.view = view;
    }

    // Define a callback interface
    public interface UrlCallback {
        void onUrlReceived(String url);
        void onFailure(String errorMessage);
    }

    public int checkPosImg(String titulo) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get the current user's UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get a reference to the Firestore database for the current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("usuarios");
        Query query = colRef.whereArrayContains("filmesFavoritos", titulo);

        // Query for documents where the "actors" array contains "Tom Hanks"
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Get the index of "Tom Hanks" in the "actors" array
                        List<String> titulos = (List<String>) documentSnapshot.get("filmesFavoritos");
                        int i = titulos.indexOf(titulo);
                        Log.d(TAG, "O filme" + titulo + "está na posição " + i);
                        index = i;
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error querying Firestore", e);
                });
        return index;
    }

    public void setImgFavorito (int i, UrlCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get the current user
        if (user != null) {
            // Get the current user's UID
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user's UID
            // Get a reference to the Firestore database for the current user
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> urls = (List<String>) documentSnapshot.get("imgUrls");
                    if (urls != null && urls.size() > 0) {
                        String movieUrl = urls.get(i);
                        Log.d(TAG, "The movie at position " + i + "has URL: " + movieUrl);
                        url = movieUrl;
                        callback.onUrlReceived(movieUrl);  // Pass the URL to the callback
                    } else {
                        callback.onFailure("No URLs found");
                    }
                } else {
                    callback.onFailure("User document not found");
                }
            }).addOnFailureListener(e -> {
                // Handle any errors that occur while querying the database
                Log.e(TAG, "Error querying Firestore", e);
                callback.onFailure(e.getMessage());  // Pass the error message to the callback
            });
        } else {
            callback.onFailure("User is not authenticated");
        }
    }

    public void getUrlFavorito(int index, UrlCallback callback) {
        setImgFavorito(index, new UrlCallback() {
            @Override
            public void onUrlReceived(String url) {
                callback.onUrlReceived(url);  // Pass the URL to the outer callback
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);  // Pass the error message to the outer callback
            }
        });

        /*String favUrl = this.setImgFavorito(index);
        Log.w(TAG, "posição " + index + " é " + favUrl);
        return favUrl;*/
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
                                FilmesFavoritosAdapter filmesFavoritosAdapter = new FilmesFavoritosAdapter(favoritos, this);
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
