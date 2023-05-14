package com.uniritter.to100ideia.ui.detalhesfilme;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.uniritter.to100ideia.data.model.Filme;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

import java.util.List;

public class DetalhesFilmePresenter implements DetalhesFilmeContrato.DetalhesFilmePresenter {

    private DetalhesFilmeContrato.DetalhesFilmeView view;
    private ImageView estrelaFav; // Instancia o componente da imagem da estrela de favorito do item da lista

    //Cria o binding para a activity
    ActivityDetalhesFilmeBinding binding;

    public static final String EXTRA_FILME = "EXTRA FILME";

    public DetalhesFilmePresenter(DetalhesFilmeContrato.DetalhesFilmeView view) {
        this.view = view;
    }

    /*public DetalhesFilmePresenter (View view) {
        estrelaFav = view.findViewById(R.id.estrelaDetalhe); // Atribui o componente da imagem da estrela de favorito do item da lista
    }*/

    public void carregarDetalhes(Intent intent, String extra, String resolucao, ImageView imagePoster) {
        final Filme filme = (Filme) intent.getSerializableExtra(extra);
        view.mostrarDetalhes(filme);
        /*String resolution = "w500";*/
        Picasso.get()
                .load("https://image.tmdb.org/t/p/"+ resolucao + "/" + filme.getCaminhoPoster())
                .into(imagePoster); //binding.imagePosterFilme

        /*view.setBtn("\u2b50", " Adicionar aos favoritos");*/


        checkFilmeFavorito(filme.getTitulo(),estrelaFav);

    }

    public void checkFilmeFavorito(String titulo, ImageView estrela) { // Método que verifica se o filme é favorito e atribui a imagem da estrela de favorito ao componente de imagem do item da lista
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Get the current user's UID
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Get a reference to the Firestore database for the current user
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);

            // Check if the given movie title exists in the user's favorite movies array
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> filmesFavoritos = (List<String>) documentSnapshot.get("filmesFavoritos");
                    if (filmesFavoritos != null && filmesFavoritos.contains(titulo)) {
                        // If the given movie title exists in the user's favorite movies array,
                        // set the star image view to the filled star icon to indicate that the movie is a favorite
                        Log.d(TAG, "FILME É FAVORITO: " + titulo);
                        view.mostraFav(true);
                        view.atualizaFavBtn(true);
                    } else {
                        // If the given movie title does not exist in the user's favorite movies array,
                        // set the star image view to the empty star icon to indicate that the movie is not a favorite
                        Log.d(TAG, "FILME NÃO É FAVORITO");
                        view.mostraFav(false);
                        view.atualizaFavBtn(false);
                    }
                }
            }).addOnFailureListener(e -> {
                // Handle any errors that occur while querying the database
                Log.e(TAG, "Error querying Firestore", e);
            });
        }

    }

    public void addFilmeAosFavoritos(Context context, Button botao, Filme filme) {
        botao.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            String usuario = sharedPreferences.getString("email", "");

            // Get a reference to the Firestore collection
            CollectionReference colRef = FirebaseFirestore.getInstance().collection("usuarios");

            // Create a query to find the document with the matching email field
            Query query = colRef.whereEqualTo("email", usuario);

            // Get the document reference for the document
            query.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            // Get the document reference from the query snapshot
                            DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();

                            // Update the array field in the document
                            docRef.update("filmesFavoritos", FieldValue.arrayUnion(filme.getTitulo()))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            view.mostraMsg("Filme adicionado aos favoritos!");
                                            Log.d(TAG, "Document updated successfully");
                                            view.atualizaFavBtn(true);
                                            view.recarregaActivity();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            view.mostraMsg("Erro ao adicionar filme aos favoritos.");
                                            Log.w(TAG, "Error updating document", e);
                                            view.atualizaFavBtn(false);
                                            view.recarregaActivity();
                                        }
                                    });
                            docRef.update("hasFilmesFavoritos", true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error querying document", e);
                        }
                    });



        });
    }
}
