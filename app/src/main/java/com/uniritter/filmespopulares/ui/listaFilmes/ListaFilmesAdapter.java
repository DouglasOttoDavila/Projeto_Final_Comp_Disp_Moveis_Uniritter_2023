package com.uniritter.filmespopulares.ui.listaFilmes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uniritter.filmespopulares.data.model.Filme;
import com.unirriter.api_filmes.R;

import java.util.ArrayList;
import java.util.List;

public class ListaFilmesAdapter extends RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmesViewHolder> {

    private List<Filme> filmes;
    private static ItemFilmeClickListener itemFilmeClickListener;
    public ListaFilmesAdapter (ItemFilmeClickListener itemFilmeClickListener) {
        this.itemFilmeClickListener = itemFilmeClickListener;
        filmes = new ArrayList<>();
    }

    //CRIA O VIEWHOLDER
    @NonNull
    @Override
    public ListaFilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        return new ListaFilmesViewHolder(view);
    }

    //ATRIBUI OS VALORES AOS COMPONENTES PARA CADA ITEM DA LISTA NA POSIÇÃO ESPECÍFICA
    @Override
    public void onBindViewHolder(@NonNull ListaFilmesViewHolder holder, int position) {
        holder.bind(filmes.get(position));
    }

    @Override
    public int getItemCount() {
        // Se a lista de filmes for nula ou vazia, retorna 0. Caso contrário, retorna o tamanho da lista
        return (filmes != null && filmes.size() > 0) ? filmes.size() : 0;
    }

    //CLASSE QUE REPRESENTA O ITEM DA LISTA E OS COMPONENTES QUE ELE POSSUI INCLUINDO A FONTE DE DADOS
    static class ListaFilmesViewHolder extends RecyclerView.ViewHolder {

        private TextView textTituloFilme;
        private ImageView imagePosterFilme;
        private Filme filme;
        public ListaFilmesViewHolder(View itemView) {
            super(itemView);

            textTituloFilme = itemView.findViewById(R.id.text_titulo_filme);
            imagePosterFilme = itemView.findViewById(R.id.image_poster_filme);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemFilmeClickListener != null)
                        itemFilmeClickListener.onItemFilmeClicado(filme);
                }
            });
        }

        public void bind(Filme filme) {
            this.filme = filme;

            textTituloFilme.setText(filme.getTitulo());
            String resolution = "w342";
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/"+ resolution + "/" + filme.getCaminhoPoster())
                    .into(imagePosterFilme);
        }
    }

    public void setFilmes (List<Filme> filmes) {
        this.filmes = filmes;
        notifyDataSetChanged();

    }

    public interface ItemFilmeClickListener {
        void onItemFilmeClicado(Filme filme);
    }



}
