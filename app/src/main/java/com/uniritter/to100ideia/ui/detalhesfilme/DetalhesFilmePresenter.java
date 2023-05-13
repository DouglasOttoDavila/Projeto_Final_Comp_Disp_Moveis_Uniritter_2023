package com.uniritter.to100ideia.ui.detalhesfilme;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.uniritter.to100ideia.data.model.Filme;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

public class DetalhesFilmePresenter implements DetalhesFilmeContrato.DetalhesFilmePresenter {

    private DetalhesFilmeContrato.DetalhesFilmeView view;

    //Cria o binding para a activity
    ActivityDetalhesFilmeBinding binding;

    public static final String EXTRA_FILME = "EXTRA FILME";

    public DetalhesFilmePresenter(DetalhesFilmeContrato.DetalhesFilmeView view) {
        this.view = view;
    }

    public void carregarDetalhes(Intent intent, String extra, String resolucao, ImageView imagePoster) {
        final Filme filme = (Filme) intent.getSerializableExtra(extra);
        view.mostrarDetalhes(filme);
        /*String resolution = "w500";*/
        Picasso.get()
                .load("https://image.tmdb.org/t/p/"+ resolucao + "/" + filme.getCaminhoPoster())
                .into(imagePoster); //binding.imagePosterFilme

        view.setBtn("\u2b50", " Adicionar aos favoritos");

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
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            view.mostraMsg("Erro ao adicionar filme aos favoritos.");
                                            Log.w(TAG, "Error updating document", e);
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
