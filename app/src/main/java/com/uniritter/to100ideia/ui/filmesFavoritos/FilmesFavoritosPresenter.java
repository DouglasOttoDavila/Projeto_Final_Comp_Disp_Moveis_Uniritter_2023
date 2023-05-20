package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

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

    // Define a interface para o callback
    public interface UrlCallback {
        void onUrlReceived(String url);
        void onFailure(String errorMessage);
    }

    public void setImgFavorito (int i, UrlCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Adquire o usuário atual
        if (user != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Adquire o UID do usuário atual
            // Adquire uma referência para o documento do usuário atual
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> urls = (List<String>) documentSnapshot.get("imgUrls");
                    if (urls != null && urls.size() > 0) {
                        String movieUrl = urls.get(i);
                        Log.d(TAG, "O filme na posição " + i + " tem URL " + movieUrl);
                        callback.onUrlReceived(movieUrl);  // Passa a URL para o callback
                    } else {
                        callback.onFailure("Nenhuma URL encontrada.");
                    }
                } else {
                    callback.onFailure("Documento não existe.");
                }
            }).addOnFailureListener(e -> {
                // Lida com os erros de leitura do Firestore
                Log.e(TAG, "Erro ao buscar no Firestore", e);
                callback.onFailure(e.getMessage());  // Informa o erro ao callback
            });
        } else {
            callback.onFailure("Usuário não está autenticado.");
        }
    }

    public void removeFavorito(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> titulos = (List<String>) documentSnapshot.get("filmesFavoritos");
                    List<String> urls = (List<String>) documentSnapshot.get("imgUrls");
                    if (titulos != null && titulos.size() > position) {
                        // Remove the movie URL from the list
                        titulos.remove(position);
                        urls.remove(position);
                        // Update the "imgUrls" field in the Firestore document
                        docRef.update("imgUrls", urls, "filmesFavoritos", titulos)
                                .addOnSuccessListener(aVoid -> Log.d(TAG, "Movie removed from Firestore"))
                                .addOnFailureListener(e -> Log.e(TAG, "Error updating document", e));
                    }
                }
            }).addOnFailureListener(e -> Log.e(TAG, "Error getting document", e));
        }
    }

    public void getUrlFavorito(int index, UrlCallback callback) {
        setImgFavorito(index, new UrlCallback() {
            @Override
            public void onUrlReceived(String url) {
                callback.onUrlReceived(url);  // Passa a URL para o callback
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);  // Passa a mensagem de erro para o callback
            }
        });
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
                            Log.d(TAG, "Documento não encontrado.");
                        }
                    } else {
                        Log.d(TAG, "Erro ao acessar documento: ", task.getException());
                    }
                });
    }

}
