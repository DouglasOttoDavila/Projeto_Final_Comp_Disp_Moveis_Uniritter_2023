package com.uniritter.to100ideia.ui.filmesFavoritos;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface FilmesFavoritosContrato {

    interface FilmesFavoritosView {
        void recarregaActivity();
        void exibeMsg(String msg);
        void exibeListaVazia();
    }

    interface FilmesFavoritosPresenter {
        void exibeFavoritos(RecyclerView recyclerView);
        void checkListaVazia(List<String> list);
    }
}
