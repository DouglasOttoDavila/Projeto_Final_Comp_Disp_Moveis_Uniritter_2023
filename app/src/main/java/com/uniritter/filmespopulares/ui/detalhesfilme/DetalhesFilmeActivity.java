package com.uniritter.filmespopulares.ui.detalhesfilme;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uniritter.filmespopulares.data.model.Filme;
import com.unirriter.api_filmes.R;

public class DetalhesFilmeActivity extends AppCompatActivity {

    public static final String EXTRA_FILME = "EXTRA FILME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);

        TextView textTituloFilme = findViewById(R.id.text_titulo_filme);
        TextView textDescricaoFilme = findViewById(R.id.text_descricao_filme);
        ImageView imagePosterFilme = findViewById(R.id.image_poster_filme);


        final Filme filme = (Filme) getIntent().getSerializableExtra(EXTRA_FILME);
        textTituloFilme.setText(filme.getTitulo());
        textDescricaoFilme.setText(filme.getDescricaoFilme());
        String resolution = "w500";
        Picasso.get()
                .load("https://image.tmdb.org/t/p/"+ resolution + "/" + filme.getCaminhoPoster())
                .into(imagePosterFilme);

        //getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }
}