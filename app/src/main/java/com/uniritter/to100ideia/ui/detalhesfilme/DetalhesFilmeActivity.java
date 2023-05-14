package com.uniritter.to100ideia.ui.detalhesfilme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji2.text.EmojiCompat;

import com.uniritter.to100ideia.data.model.Filme;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

public class DetalhesFilmeActivity
        extends AppCompatActivity
        implements DetalhesFilmeContrato.DetalhesFilmeView {

    public static final String EXTRA_FILME = "EXTRA FILME"; //Chave para o objeto filme passado para a activity; usado para recuperar o objeto na activity

    //Cria o binding para a activity
    ActivityDetalhesFilmeBinding binding;

    //Declara o presenter da activity
    private DetalhesFilmeContrato.DetalhesFilmePresenter presenter;

    private ImageView favorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityDetalhesFilmeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        favorito = view.findViewById(R.id.estrelaDetalhe);

        //Inicializa o presenter da activity
        presenter = new DetalhesFilmePresenter(this);
        Intent intent = getIntent();
        presenter.carregarDetalhes(intent, EXTRA_FILME, "w500", binding.imagePosterFilme);
        presenter.addFilmeAosFavoritos(this, binding.addFavoritos, (Filme) getIntent().getSerializableExtra(EXTRA_FILME));

    }

    @Override
    public void mostrarDetalhes(Filme filme) {
        binding.textTituloFilme.setText(filme.getTitulo());
        binding.textDescricaoFilme.setText(filme.getDescricaoFilme());
    }

    @Override
    public void mostraMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostraFav(boolean fav) {

        if (!fav) {
            favorito.setVisibility(View.INVISIBLE);
            favorito.invalidate();
        }
        else {
            favorito.setVisibility(View.VISIBLE);
            favorito.invalidate();
        }
    }

    @Override
    public void recarregaActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void atualizaFavBtn(boolean fav) {
        if (fav) {
            binding.addFavoritos.setText(EmojiCompat.get().process("\u2b50") + "Remover dos favoritos");
        }
        else {
            binding.addFavoritos.setText(EmojiCompat.get().process("\u2b50") + "Adicionar aos favoritos");
        }
    }


}