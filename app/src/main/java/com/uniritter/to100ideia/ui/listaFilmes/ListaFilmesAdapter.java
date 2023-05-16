package com.uniritter.to100ideia.ui.listaFilmes;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.uniritter.to100ideia.data.model.Filme;
import com.unirriter.api_filmes.R;

import java.util.ArrayList;
import java.util.List;

public class ListaFilmesAdapter
        extends RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmesViewHolder> {

    private List<Filme> filmes;

    private static ItemFilmeClickListener itemFilmeClickListener;

    public ListaFilmesAdapter(ItemFilmeClickListener itemFilmeClickListener) { // Construtor que recebe o listener do item da lista
        this.itemFilmeClickListener = itemFilmeClickListener; // Atribui o listener do item da lista
        filmes = new ArrayList<>(); // Instancia a lista de filmes
    }

    //CRIA O VIEWHOLDER
    @NonNull
    @Override
    public ListaFilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Método que cria o viewholder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false); // Infla o layout do item da lista
        return new ListaFilmesViewHolder(view); // Retorna o viewholder
    }

    //ATRIBUI OS VALORES AOS COMPONENTES PARA CADA ITEM DA LISTA NA POSIÇÃO ESPECÍFICA
    @Override
    public void onBindViewHolder(@NonNull ListaFilmesViewHolder holder, int position) { // Método que atribui os valores aos componentes para cada item da lista na posição específica
        holder.bind(filmes.get(position)); // Chama o método que atribui os valores aos componentes para cada item da lista na posição específica
    }

    @Override
    public int getItemCount() { // Método que retorna a quantidade de itens da lista
        // Se a lista de filmes for nula ou vazia, retorna 0. Caso contrário, retorna o tamanho da lista
        return (filmes != null && filmes.size() > 0) ? filmes.size() : 0;
    }

    //CLASSE QUE REPRESENTA O ITEM DA LISTA E OS COMPONENTES QUE ELE POSSUI INCLUINDO A FONTE DE DADOS
    static class ListaFilmesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView textTituloFilme; // Instancia o componente de texto do item da lista
        private ImageView imagePosterFilme; // Instancia o componente de imagem do item da lista

        private ImageView estrelaFav; // Instancia o componente da imagem da estrela de favorito do item da lista
        private Filme filme; // Instancia o filme
        public ListaFilmesViewHolder(View itemView) { // Construtor que recebe o item da lista
            super(itemView); // Chama o construtor da superclasse
            textTituloFilme = itemView.findViewById(R.id.text_titulo_filme); // Atribui o componente de texto do item da lista
            imagePosterFilme = itemView.findViewById(R.id.image_poster_filme); // Atribui o componente de imagem do item da lista
            estrelaFav = itemView.findViewById(R.id.estrelaFav); // Atribui o componente da imagem da estrela de favorito do item da lista
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemFilmeClickListener != null)
                        itemFilmeClickListener.onItemFilmeClicado(filme);
                }
            });
        }

        public void bind(Filme filme) { // Método que atribui os valores aos componentes para cada item da lista na posição específica
            this.filme = filme; // Atribui o filme

            String titulo = filme.getTitulo(); // Atribui o título do filme a uma variável
            textTituloFilme.setText(titulo); // Atribui o título do filme ao componente de texto do item da lista
            String resolution = "w342"; // Define a resolução da imagem
            Picasso.get() // Carrega a imagem do filme
                    .load("https://image.tmdb.org/t/p/"+ resolution + "/" + filme.getCaminhoPoster()) // Define a URL da imagem
                    .into(imagePosterFilme); // Atribui a imagem ao componente de imagem do item da lista

            checkFilmeFavorito(titulo, estrelaFav); // Chama o método que verifica se o filme é favorito
        }
    }

    public static void checkFilmeFavorito(String titulo, ImageView estrelaFav) { // Método que verifica se o filme é favorito e atribui a imagem da estrela de favorito ao componente de imagem do item da lista
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Get the current user's UID
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Get a reference to the Firestore database for the current user
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);

            // Check if the given movie title exists in the user's favorite movies array
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> filmesFavoritos = (List<String>) documentSnapshot.get("filmesFavoritos");
                    if (filmesFavoritos != null && filmesFavoritos.contains(titulo)) {
                        // If the given movie title exists in the user's favorite movies array,
                        // set the star image view to the filled star icon to indicate that the movie is a favorite
                        Log.d(TAG, "FILME É FAVORITO: " + titulo);
                        estrelaFav.setVisibility(View.VISIBLE);
                    } else {
                        // If the given movie title does not exist in the user's favorite movies array,
                        // set the star image view to the empty star icon to indicate that the movie is not a favorite
                        Log.d(TAG, "FILME NÃO É FAVORITO");
                        estrelaFav.setVisibility(View.INVISIBLE);
                    }
                }
            }).addOnFailureListener(e -> {
                // Handle any errors that occur while querying the database
                Log.e(TAG, "Error querying Firestore", e);
            });
        }

    }

    public void setFilmes (List<Filme> filmes) { // Método que atribui a lista de filmes
        this.filmes = filmes; // Atribui a lista de filmes
        notifyDataSetChanged(); // Notifica o adapter que a lista de filmes foi alterada
    }

    public interface ItemFilmeClickListener { // Interface que define o listener do item da lista
        void onItemFilmeClicado(Filme filme); // Método que define o listener do item da lista
    }


}
