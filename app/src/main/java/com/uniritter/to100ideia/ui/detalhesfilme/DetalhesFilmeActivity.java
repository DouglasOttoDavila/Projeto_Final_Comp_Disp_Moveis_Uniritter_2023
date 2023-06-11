package com.uniritter.to100ideia.ui.detalhesfilme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji2.text.EmojiCompat;
import com.uniritter.to100ideia.data.model.Filme;
import com.uniritter.to100ideia.ui.listaFilmes.ListaFilmesFragment;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

public class DetalhesFilmeActivity //Classe da activity de detalhes do filme
        extends AppCompatActivity //Classe pai da activity
        implements DetalhesFilmeContrato.DetalhesFilmeView { //Interface da view da activity

    public static final String EXTRA_FILME = "EXTRA FILME"; //Chave para o objeto filme passado para a activity; usado para recuperar o objeto na activity
    ActivityDetalhesFilmeBinding binding; //Binding da activity
    private DetalhesFilmeContrato.DetalhesFilmePresenter presenter; //Presenter da activity
    private ImageView favorito; //Ícone de favorito
    ListaFilmesFragment listaFilmesFragment = new ListaFilmesFragment(); //Fragmento da lista de filmes

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Método chamado quando a activity é criada
        super.onCreate(savedInstanceState); //Chama o método da classe pai
        binding = ActivityDetalhesFilmeBinding.inflate(getLayoutInflater()); //Inicializa o binding da activity
        View view = binding.getRoot(); //Inicializa a view da activity
        setContentView(view); //Seta a view da activity
        favorito = view.findViewById(R.id.estrelaDetalhe); //Inicializa o ícone de favorito
        presenter = new DetalhesFilmePresenter(this); //Inicializa o presenter da activity
        Intent intent = getIntent(); //Inicializa o intent da activity
        presenter.carregarDetalhes(intent, EXTRA_FILME, "w500", binding.imagePosterFilme); //Chama o método para carregar os detalhes do filme
    }

    @Override
    public void mostrarDetalhes(Filme filme) { //Método para mostrar os detalhes do filme na activity
        binding.textTituloFilme.setText(filme.getTitulo()); //Seta o título do filme
        binding.textDescricaoFilme.setText(filme.getDescricaoFilme()); //Seta a descrição do filme
    }

    @Override
    public void mostraMsg(String msg) { //Método para mostrar uma mensagem na activity
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostraFav(boolean fav) { //Método para mostrar o ícone de favorito
        if (!fav) { //Se o filme não estiver nos favoritos, o ícone de favorito fica invisível
            favorito.setVisibility(View.INVISIBLE);
            favorito.invalidate();
        }
        else { //Se o filme estiver nos favoritos, o ícone de favorito fica visível
            favorito.setVisibility(View.VISIBLE);
            favorito.invalidate();
        }
    }

    @Override
    public void recarregaActivity() { //Método para recarregar a activity
        Intent intent = getIntent(); //Inicializa o intent da activity
        finish(); //Finaliza a activity
        startActivity(intent); //Inicia a activity
    }

    @Override
    public void atualizaFavBtn(boolean fav) { //Método para atualizar o botão de favoritos
        if (fav) { //Se o filme estiver nos favoritos, o botão de favoritos fica com o texto "Remover dos favoritos"
            binding.addFavoritos.setText(EmojiCompat.get().process("\u2b50") + "Remover dos favoritos"); //Seta o texto do botão de favoritos
            binding.addFavoritos.setOnClickListener(v -> { //Quando o botão de favoritos é clicado, o filme é removido dos favoritos
                presenter.removeFilmeDosFavoritos(this, binding.addFavoritos, (Filme) getIntent().getSerializableExtra(EXTRA_FILME)); //Chama o método para remover o filme dos favoritos
            });
        }
        else { //Se o filme não estiver nos favoritos, o botão de favoritos fica com o texto "Adicionar aos favoritos"
            binding.addFavoritos.setText(EmojiCompat.get().process("\u2b50") + "Adicionar aos favoritos"); //Seta o texto do botão de favoritos
            binding.addFavoritos.setOnClickListener(v -> { //Quando o botão de favoritos é clicado, o filme é adicionado aos favoritos
                presenter.addFilmeAosFavoritos(this, binding.addFavoritos, (Filme) getIntent().getSerializableExtra(EXTRA_FILME)); //Chama o método para adicionar o filme aos favoritos
            });
        }
    }

}