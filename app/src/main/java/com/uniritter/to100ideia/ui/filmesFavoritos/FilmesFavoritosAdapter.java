package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.unirriter.api_filmes.R;
import java.util.List;

public class FilmesFavoritosAdapter // Define o adapter
        extends RecyclerView.Adapter<FilmesFavoritosAdapter.ViewHolder> {

    private List<String> titulos; // Define a lista de titulos
    private FilmesFavoritosPresenter presenter; // Define o presenter

    public FilmesFavoritosAdapter(List<String> titulos, FilmesFavoritosPresenter presenter) { // Construtor do adapter
        this.titulos = titulos;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Cria a view
        View view = LayoutInflater.from(parent.getContext()) // Define o layout
                .inflate(R.layout.item_favorito, parent, false); // Define o layout
        return new ViewHolder(view); // Retorna a view
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { // Define o conteúdo da view
        holder.textViewFavorito.setText(titulos.get(position)); // Define o texto do item da lista
        presenter.getUrlFavorito(position, new FilmesFavoritosPresenter.UrlCallback() { // Carrega a imagem do filme
            @Override
            public void onUrlReceived(String url) { // Atribui a imagem ao componente de imagem do item da lista
                setItemImage(holder.imgFavorito, url);
            }

            @Override
            public void onFailure(String errorMessage) { // Caso ocorra um erro
                Log.e(TAG, errorMessage); // Exibe o erro no log
            }
        });

        holder.imgRemove.setOnClickListener(v -> { // Define o evento de click no botão de remover
            presenter.removeFavorito(position); // Remove o filme da lista de favoritos
        });
    }

    public void setItemImage(ImageView imageView, String imageUrl) { // Define a imagem do item da lista
        String resolution = "w342"; // Define a resolução da imagem
        Picasso.get() // Carrega a imagem do filme
                .load("https://image.tmdb.org/t/p/"+ resolution + "/" + imageUrl) // Define a URL da imagem
                .resize(50, 50) // Define o tamanho da imagem
                .centerCrop() // Define o tipo de corte da imagem
                .into(imageView); // Atribui a imagem ao componente de imagem do item da lista
        Log.w(TAG, "https://image.tmdb.org/t/p/"+ resolution + "/" + imageUrl); // Exibe a URL da imagem no log
    }

    @Override
    public int getItemCount() {
        return titulos.size();
    } // Retorna a quantidade de itens da lista

    public static class ViewHolder extends RecyclerView.ViewHolder { // Define a view
        public TextView textViewFavorito; // Define o componente de texto do item da lista
        public ImageView imgFavorito; // Define o componente de imagem do item da lista
        public ImageView imgRemove; // Define o componente de imagem do botão de remover

        public ViewHolder(View itemView) { // Construtor da view
            super(itemView); // Define a view
            textViewFavorito = itemView.findViewById(R.id.textViewFavorito); // Define o componente de texto do item da lista
            imgFavorito = itemView.findViewById(R.id.imgFavorito); // Define o componente de imagem do item da lista
            imgRemove = itemView.findViewById(R.id.imgRemove); // Define o componente de imagem do botão de remover
        }
    }

}
