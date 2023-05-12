package com.uniritter.to100ideia.ui.detalhesfilme;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji2.text.EmojiCompat;

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
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

public class DetalhesFilmeActivity extends AppCompatActivity {

    public static final String EXTRA_FILME = "EXTRA FILME";

    ActivityDetalhesFilmeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityDetalhesFilmeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setBtn(binding.addFavoritos, "\u2b50", " Adicionar aos favoritos");

        carregarDetalhes(EXTRA_FILME, "w500");

        addFilmeAosFavoritos(binding.addFavoritos, (Filme) getIntent().getSerializableExtra(EXTRA_FILME));

    }

    public void carregarDetalhes(String extra, String resolucao) {
        final Filme filme = (Filme) getIntent().getSerializableExtra(extra);
        binding.textTituloFilme.setText(filme.getTitulo());
        binding.textDescricaoFilme.setText(filme.getDescricaoFilme());
        String resolution = "w500";
        Picasso.get()
                .load("https://image.tmdb.org/t/p/"+ resolution + "/" + filme.getCaminhoPoster())
                .into(binding.imagePosterFilme);
    }

    public void setBtn (Button button, String emojiCode, String text) {
        button.setText(EmojiCompat.get().process(emojiCode) + text);
    }

    public void addFilmeAosFavoritos(Button botao, Filme filme) {
        botao.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
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
                            docRef.update("filmesFavoritos", FieldValue.arrayUnion("", filme.getTitulo()))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Document updated successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
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