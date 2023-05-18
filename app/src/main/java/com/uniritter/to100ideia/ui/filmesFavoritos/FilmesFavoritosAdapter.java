package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityFilmesFavoritosBinding;

import java.util.List;

public class FilmesFavoritosAdapter
        extends RecyclerView.Adapter<FilmesFavoritosAdapter.ViewHolder> {

    private List<String> titulos; // Define a lista de titulos
    private FilmesFavoritosPresenter presenter;

    public FilmesFavoritosAdapter(List<String> titulos, FilmesFavoritosPresenter presenter) {
        this.titulos = titulos;
        this.presenter = presenter;
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
        presenter.getUrlFavorito(position, new FilmesFavoritosPresenter.UrlCallback() {
            @Override
            public void onUrlReceived(String url) {
                setItemImage(holder.imgFavorito, url);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the failure, e.g., display a placeholder image or show an error message
            }
        });
    }

    public void setItemImage(ImageView imageView, String imageUrl) {

        String resolution = "w342"; // Define a resolução da imagem
        Picasso.get() // Carrega a imagem do filme
                .load("https://image.tmdb.org/t/p/"+ resolution + "/" + imageUrl) // Define a URL da imagem
                .resize(50, 50)
                .centerCrop()
                .into(imageView); // Atribui a imagem ao componente de imagem do item da lista
        Log.w(TAG, "https://image.tmdb.org/t/p/"+ resolution + "/" + imageUrl);
    }

    @Override
    public int getItemCount() {
        return titulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewFavorito;
        public ImageView imgFavorito;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFavorito = itemView.findViewById(R.id.textViewFavorito);
            imgFavorito = itemView.findViewById(R.id.imgFavorito);
        }
    }


}
