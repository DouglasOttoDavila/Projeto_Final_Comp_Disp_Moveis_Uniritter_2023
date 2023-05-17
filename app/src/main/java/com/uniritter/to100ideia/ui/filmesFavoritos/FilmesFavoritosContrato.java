package com.uniritter.to100ideia.ui.filmesFavoritos;

import androidx.recyclerview.widget.RecyclerView;

public interface FilmesFavoritosContrato {

    interface FilmesFavoritosView {

    }

    interface FilmesFavoritosPresenter {
        void exibeFavoritos(RecyclerView recyclerView);
    }
}
