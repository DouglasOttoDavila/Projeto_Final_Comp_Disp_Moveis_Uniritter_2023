package com.uniritter.to100ideia.ui.detalhesfilme;

import static android.content.ContentValues.TAG;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.uniritter.to100ideia.data.model.Filme;
import com.unirriter.api_filmes.databinding.ActivityDetalhesFilmeBinding;

import java.util.List;

public class DetalhesFilmePresenter // Classe que implementa a interface DetalhesFilmeContrato.DetalhesFilmePresenter
        implements DetalhesFilmeContrato.DetalhesFilmePresenter { // Classe que implementa a interface DetalhesFilmeContrato.DetalhesFilmePresenter

    private DetalhesFilmeContrato.DetalhesFilmeView view; // Instancia a interface DetalhesFilmeContrato.DetalhesFilmeView
    private ImageView estrelaFav; // Instancia o componente da imagem da estrela de favorito do item da lista
    ActivityDetalhesFilmeBinding binding; // Instancia a classe ActivityDetalhesFilmeBinding
    public static final String EXTRA_FILME = "EXTRA FILME"; // Atribui o valor "EXTRA FILME" a constante EXTRA_FILME

    public DetalhesFilmePresenter(DetalhesFilmeContrato.DetalhesFilmeView view) { // Construtor da classe DetalhesFilmePresenter
        this.view = view;
    }

    public void carregarDetalhes(Intent intent, String extra, String resolucao, ImageView imagePoster) { // Método que carrega os detalhes do filme
        final Filme filme = (Filme) intent.getSerializableExtra(extra); // Atribui a variável filme o valor do extra passado por parâmetro
        view.mostrarDetalhes(filme); // Chama o método mostrarDetalhes da interface DetalhesFilmeContrato.DetalhesFilmeView
        Picasso.get() // Instancia a biblioteca Picasso
                .load("https://image.tmdb.org/t/p/" + resolucao + "/" + filme.getCaminhoPoster()) // Carrega a imagem do poster do filme
                .into(imagePoster); // Atribui a imagem do poster do filme ao componente de imagem do item da lista
        checkFilmeFavorito(filme.getTitulo(), estrelaFav); // Chama o método checkFilmeFavorito passando o título do filme e o componente de imagem da estrela de favorito do item da lista
    }

    public void checkFilmeFavorito(String titulo, ImageView estrela) { // Método que verifica se o filme é favorito e atribui a imagem da estrela de favorito ao componente de imagem do item da lista
        //FIREBASE
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Instancia o usuário atual do Firebase
        if (user != null) { // Se o usuário for diferente de nulo
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Atribui a variável uid o valor do UID do usuário atual
            FirebaseFirestore db = FirebaseFirestore.getInstance(); // Instancia o banco de dados Firestore
            DocumentReference docRef = db.collection("usuarios").document(uid); // Atribui a variável docRef a referência do documento do usuário atual
            docRef.get().addOnSuccessListener(documentSnapshot -> { // Adiciona um listener para o documento do usuário atual
                if (documentSnapshot.exists()) { // Se o documento existir
                    List<String> filmesFavoritos = (List<String>) documentSnapshot.get("filmesFavoritos"); // Atribui a variável filmesFavoritos a lista de filmes favoritos do usuário atual
                    if (filmesFavoritos != null && filmesFavoritos.contains(titulo)) { // Se a lista de filmes favoritos for diferente de nulo e contiver o título do filme
                        Log.d(TAG, "FILME " + titulo + " É FAVORITO"); // Loga no console que o filme é favorito
                        view.mostraFav(true); // Mostra a estrela de favorito
                        view.atualizaFavBtn(true); // Atualiza o botão de favorito
                    } else { // Se a lista de filmes favoritos for nula ou não contiver o título do filme
                        Log.d(TAG, "FILME " + titulo + "NÃO É FAVORITO"); // Loga no console que o filme não é favorito
                        view.mostraFav(false); // Esconde a estrela de favorito
                        view.atualizaFavBtn(false); // Atualiza o botão de favorito
                    }
                }
            }).addOnFailureListener(e -> { // Adiciona um listener de falha para o documento do usuário atual
                Log.e(TAG, "Error querying Firestore", e); // Loga no console o erro
            });
        }
    }

    public void removeFilmeDosFavoritos(Context context, Button botao, Filme filme) { // Método que remove o filme dos favoritos
        //SHARED PREFERENCES
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE); // Instancia o SharedPreferences
        String usuario = sharedPreferences.getString("email", ""); // Atribui a variável usuario o valor do email do usuário

        //FIREBASE
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Atribui a variável uid o valor do UID do usuário atual
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Instancia o banco de dados Firestore
        DocumentReference docRef = db.collection("usuarios").document(uid); // Atribui a variável docRef a referência do documento do usuário atual

        docRef.get().addOnSuccessListener(documentSnapshot -> { // Adiciona um listener para o documento do usuário atual
            if (documentSnapshot.exists()) { // Se o documento existir
                List<String> filmesFavoritos = (List<String>) documentSnapshot.get("filmesFavoritos"); // Atribui a variável filmesFavoritos a lista de filmes favoritos do usuário atual
                List<String> imgUrls = (List<String>) documentSnapshot.get("imgUrls"); // Atribui a variável imgUrls a lista de URLs das imagens dos filmes favoritos do usuário atual
                Log.d(TAG, "FILMES FAVORITOS: " + filmesFavoritos); // Loga no console a lista de filmes favoritos do usuário atual
                Log.d(TAG, "URL: " + imgUrls); // Loga no console a lista de URLs das imagens dos filmes favoritos do usuário atual
                if (filmesFavoritos != null && filmesFavoritos.contains(filme.getTitulo())) { // Se a lista de filmes favoritos for diferente de nulo e contiver o título do filme
                    filmesFavoritos.remove(filme.getTitulo()); // Remove o título do filme da lista de filmes favoritos
                    imgUrls.remove(filme.getCaminhoPoster()); // Remove a URL da imagem do filme da lista de URLs das imagens dos filmes favoritos
                    Log.d(TAG, "FILME REMOVIDO DOS FAVORITOS: " + filme.getTitulo()); // Loga no console o título do filme removido dos favoritos
                    Log.d(TAG, "URL REMOVIDA DOS FAVORITOS: " + filme.getCaminhoPoster()); // Loga no console a URL da imagem do filme removida dos favoritos

                    docRef.update("filmesFavoritos", filmesFavoritos) // Atualiza a lista de filmes favoritos do usuário atual no banco de dados
                            .addOnSuccessListener(aVoid -> { // Adiciona um listener de sucesso para a atualização da lista de filmes favoritos do usuário atual no banco de dados
                                docRef.update("imgUrls", imgUrls); // Atualiza a lista de URLs das imagens dos filmes favoritos do usuário atual no banco de dados
                                view.mostraMsg(filme.getTitulo() + " removido dos favoritos!"); // Chama o método mostraMsg da interface DetalhesFilmeContrato.DetalhesFilmeView
                                view.mostraFav(false); // Chama o método mostraFav da interface DetalhesFilmeContrato.DetalhesFilmeView
                                view.atualizaFavBtn(false); // Chama o método atualizaFavBtn da interface DetalhesFilmeContrato.DetalhesFilmeView
                                if (filmesFavoritos.isEmpty()) { // Se a lista de filmes favoritos estiver vazia
                                    db.collection("usuarios").document(uid).update("hasFilmesFavoritos", false); // Atualiza o campo hasFilmesFavoritos do usuário atual no banco de dados
                                }
                            }).addOnFailureListener(e -> { // Adiciona um listener de falha para a atualização da lista de filmes favoritos do usuário atual no banco de dados
                                Log.e(TAG, "Erro ao atualizar Firestore", e); // Loga no console o erro
                            });
                }
            }
        }).addOnFailureListener(e -> { // Adiciona um listener de falha para o documento do usuário atual
            Log.e(TAG, "Erro ao consultar Firestore", e); // Loga no console o erro
        });
    }

    public void addFilmeAosFavoritos(Context context, Button botao, Filme filme) { // Método que adiciona o filme aos favoritos
        //SHARED PREFERENCES
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE); // Instancia o SharedPreferences
        String usuario = sharedPreferences.getString("email", ""); // Atribui a variável usuario o valor do email do usuário

        //FIREBASE
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("usuarios"); // Atribui a variável colRef a referência da coleção de usuários do banco de dados Firestore
        Query query = colRef.whereEqualTo("email", usuario); // Atribui a variável query a consulta de usuários com o email igual ao email do usuário atual
        query.get() // Executa a consulta
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() { // Adiciona um listener de sucesso para a consulta
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) { // Se a consulta for bem sucedida
                        DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference(); // Atribui a variável docRef a referência do documento do usuário atual
                        docRef.update("filmesFavoritos", FieldValue.arrayUnion(filme.getTitulo())) // Atualiza a lista de filmes favoritos do usuário atual no banco de dados
                                .addOnSuccessListener(new OnSuccessListener<Void>() { // Adiciona um listener de sucesso para a atualização da lista de filmes favoritos do usuário atual no banco de dados
                                    @Override
                                    public void onSuccess(Void aVoid) { // Se a atualização for bem sucedida
                                        view.mostraMsg(filme.getTitulo() + " adicionado aos favoritos!"); // Chama o método mostraMsg da interface DetalhesFilmeContrato.DetalhesFilmeView
                                        Log.d(TAG, "Documento atualizado com sucesso!"); // Loga no console a mensagem
                                        view.atualizaFavBtn(true); // Chama o método atualizaFavBtn da interface DetalhesFilmeContrato.DetalhesFilmeView
                                        view.recarregaActivity(); // Chama o método recarregaActivity da interface DetalhesFilmeContrato.DetalhesFilmeView
                                        docRef.update("imgUrls", FieldValue.arrayUnion(filme.getCaminhoPoster())); // Atualiza a lista de URLs das imagens dos filmes favoritos do usuário atual no banco de dados
                                    }
                                }).addOnFailureListener(new OnFailureListener() { // Adiciona um listener de falha para a atualização da lista de filmes favoritos do usuário atual no banco de dados
                                    @Override
                                    public void onFailure(@NonNull Exception e) { // Se a atualização falhar
                                        view.mostraMsg("Erro ao adicionar filme aos favoritos."); // Chama o método mostraMsg da interface DetalhesFilmeContrato.DetalhesFilmeView
                                        Log.w(TAG, "Erro ao adicionar filme ao Firestore", e); // Loga no console o erro
                                        //(REVISAR)view.atualizaFavBtn(false); // Chama o método atualizaFavBtn da interface DetalhesFilmeContrato.DetalhesFilmeView
                                        view.recarregaActivity(); // Chama o método recarregaActivity da interface DetalhesFilmeContrato.DetalhesFilmeView
                                    }
                                });
                        docRef.update("hasFilmesFavoritos", true); // Atualiza o campo hasFilmesFavoritos do usuário atual no banco de dados
                    }
                }).addOnFailureListener(new OnFailureListener() { // Adiciona um listener de falha para a consulta
                    @Override
                    public void onFailure(@NonNull Exception e) { // Se a consulta falhar
                        Log.w(TAG, "Erro ao consultar Firestore", e); // Loga no console o erro
                    }
                });
    }
}
