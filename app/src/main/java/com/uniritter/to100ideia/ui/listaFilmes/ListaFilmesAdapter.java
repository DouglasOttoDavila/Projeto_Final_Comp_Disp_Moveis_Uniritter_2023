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

public class ListaFilmesAdapter // Classe que representa o adapter da lista de filmes
        extends RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmesViewHolder> { // Herda da classe RecyclerView.Adapter e passa o viewholder como parâmetro

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
                    if (itemFilmeClickListener != null) // Verifica se o listener do item da lista não é nulo
                        itemFilmeClickListener.onItemFilmeClicado(filme); // Chama o método que trata o evento de clique no item da lista
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
            // Pega o uid do usuário logado
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Pega a instância do Firestore e a referência do documento do usuário
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(uid);

            // Captura o documento do usuário no Firestore e verifica se o título do filme existe no array de filmes favoritos do usuário
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    List<String> filmesFavoritos = (List<String>) documentSnapshot.get("filmesFavoritos");
                    if (filmesFavoritos != null && filmesFavoritos.contains(titulo)) {
                        // Se o título do filme existir no array de filmes favoritos do usuário, atribui a imagem da estrela de favorito ao componente de imagem do item da lista
                        Log.d(TAG, "FILME É FAVORITO: " + titulo);
                        estrelaFav.setVisibility(View.VISIBLE);
                    } else {
                        // Se o título do filme não existir no array de filmes favoritos do usuário, atribui a imagem da estrela de favorito ao componente de imagem do item da lista
                        Log.d(TAG, "FILME NÃO É FAVORITO");
                        estrelaFav.setVisibility(View.INVISIBLE);
                    }
                }
            }).addOnFailureListener(e -> {
                // Se ocorrer um erro, exibe uma mensagem de erro
                Log.e(TAG, "Error querying Firestore", e);
            });
        }
    }

    public void setFilmes (List<Filme> filmes) { // Método que atribui a lista de filmes
        this.filmes = filmes; // Atribui a lista de filmes
        notifyDataSetChanged(); // Notifica o adapter que a lista de filmes foi alterada
        Log.d("SetFilmes", "Quantidade de filmes: " + filmes.size());
    }

    public interface ItemFilmeClickListener { // Interface que define o listener do item da lista
        void onItemFilmeClicado(Filme filme); // Método que define o listener do item da lista
    }


}
