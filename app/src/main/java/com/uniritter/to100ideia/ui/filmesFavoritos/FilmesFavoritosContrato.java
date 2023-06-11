package com.uniritter.to100ideia.ui.filmesFavoritos;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public interface FilmesFavoritosContrato { // Contrato para a tela de favoritos

    interface FilmesFavoritosView { // View
        void recarregaActivity();
        void exibeMsg(String msg);
        void exibeListaVazia();
    }

    interface FilmesFavoritosPresenter { // Presenter
        void exibeFavoritos(RecyclerView recyclerView);
        void checkListaVazia(List<String> list);
    }
}
