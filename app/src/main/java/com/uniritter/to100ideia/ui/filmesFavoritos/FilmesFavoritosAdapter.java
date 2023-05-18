package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;

import java.util.List;

public class FilmesFavoritosAdapter
        extends RecyclerView.Adapter<FilmesFavoritosAdapter.ViewHolder> {

    private List<String> titulos; // Define a lista de titulos

    public FilmesFavoritosAdapter(List<String> titulos) {
        this.titulos = titulos;
    } // Construtor

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Cria a view
        View view = LayoutInflater.from(parent.getContext()) // Define o layout
                .inflate(R.layout.item_favorito, parent, false); // Define o layout
        return new ViewHolder(view); // Retorna a view
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { // Define o conteúdo da view

        holder.textViewFavorito.setText(titulos.get(position));
    }

    @Override
    public int getItemCount() {
        return titulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewFavorito;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFavorito = itemView.findViewById(R.id.textViewFavorito);
        }
    }


}
