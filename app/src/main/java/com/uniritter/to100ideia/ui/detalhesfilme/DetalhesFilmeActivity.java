package com.uniritter.to100ideia.ui.detalhesfilme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji2.text.EmojiCompat;

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

    public void addFilmeAosFavoritos(Button botao) {
        botao.setOnClickListener(v -> {


        });
    }
}