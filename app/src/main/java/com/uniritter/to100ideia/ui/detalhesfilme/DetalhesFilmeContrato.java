package com.uniritter.to100ideia.ui.detalhesfilme;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import com.uniritter.to100ideia.data.model.Filme;

public interface DetalhesFilmeContrato { // Contrato para a tela de detalhes do filme
    interface DetalhesFilmeView { // Interface para a view
        void mostrarDetalhes(Filme filme);
        void mostraMsg(String msg);
        void mostraFav(boolean fav);
        void recarregaActivity();
        void atualizaFavBtn(boolean fav);
    }

    interface DetalhesFilmePresenter { // Interface para o presenter
        void checkFilmeFavorito(String titulo, ImageView estrela);
        void addFilmeAosFavoritos(Context context, Button botao, Filme filme);
        void removeFilmeDosFavoritos(Context context, Button botao, Filme filme);
        void carregarDetalhes(Intent intent, String extra, String resolucao, ImageView imagePoster);
    }
}
