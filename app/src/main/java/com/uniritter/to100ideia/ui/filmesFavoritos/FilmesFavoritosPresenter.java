package com.uniritter.to100ideia.ui.filmesFavoritos;

import static android.content.ContentValues.TAG;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import java.util.Objects;

public class FilmesFavoritosPresenter // Implementa a interface do contrato
        implements FilmesFavoritosContrato.FilmesFavoritosPresenter { // Define o presenter

    private FilmesFavoritosContrato.FilmesFavoritosView view; // Define a view
    private String url; // Define a URL
    int index; // Define o índice

    public FilmesFavoritosPresenter(FilmesFavoritosContrato.FilmesFavoritosView view) { // Construtor
        this.view = view;
    }

    public interface UrlCallback { // Define a interface do callback
        void onUrlReceived(String url); // Define o método de sucesso
        void onFailure(String errorMessage); // Define o método de falha
    }

    public void setImgFavorito (int i, UrlCallback callback) { // Define o método de setar a imagem
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Adquire o usuário atual
        if (user != null) { // Se o usuário não for nulo
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Adquire o UID do usuário atual
            FirebaseFirestore db = FirebaseFirestore.getInstance(); // Adquire uma instância do Firestore
            DocumentReference docRef = db.collection("usuarios").document(uid); // Adquire a referência do documento do usuário atual
            docRef.get().addOnSuccessListener(documentSnapshot -> { // Adiciona um listener de sucesso
                if (documentSnapshot.exists()) { // Se o documento existir
                    List<String> urls = (List<String>) documentSnapshot.get("imgUrls"); // Adquire a lista de URLs
                    if (urls != null && urls.size() > 0) { // Se a lista de URLs não for nula e tiver tamanho maior que 0
                        String movieUrl = urls.get(i); // Adquire a URL do filme na posição i
                        Log.d(TAG, "O filme na posição " + i + " tem URL " + movieUrl); // Loga a URL do filme
                        callback.onUrlReceived(movieUrl);  // Passa a URL para o callback
                    } else { // Se a lista de URLs for nula ou tiver tamanho 0
                        callback.onFailure("Nenhuma URL encontrada."); // Passa a mensagem de erro para o callback
                    }
                } else {
                    callback.onFailure("Documento não existe."); // Passa a mensagem de erro para o callback
                }
            }).addOnFailureListener(e -> { // Adiciona um listener de falha
                Log.e(TAG, "Erro ao buscar no Firestore", e); // Loga o erro
                callback.onFailure(e.getMessage());  // Informa o erro ao callback
            });
        } else {
            callback.onFailure("Usuário não está autenticado."); // Passa a mensagem de erro para o callback
        }
    }

    public void removeFavorito(int position) { // Define o método de remover favorito
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Adquire o usuário atual
        if (user != null) { // Se o usuário não for nulo
            String uid = user.getUid(); // Adquire o UID do usuário atual
            FirebaseFirestore db = FirebaseFirestore.getInstance(); // Adquire uma instância do Firestore
            DocumentReference docRef = db.collection("usuarios").document(uid); // Adquire a referência do documento do usuário atual
            docRef.get().addOnSuccessListener(documentSnapshot -> { // Adiciona um listener de sucesso
                if (documentSnapshot.exists()) { // Se o documento existir
                    List<String> titulos = (List<String>) documentSnapshot.get("filmesFavoritos"); // Adquire a lista de títulos
                    List<String> urls = (List<String>) documentSnapshot.get("imgUrls"); // Adquire a lista de URLs
                    if (titulos != null && titulos.size() > position && urls != null && urls.size() > position) { // Se as listas não forem nulas e tiverem tamanho maior que a posição
                        String titulo = titulos.get(position); // Adquire o título do filme na posição
                        titulos.remove(position); // Remove o título da lista
                        urls.remove(position); // Remove a URL da lista
                        if (titulos.isEmpty() && urls.isEmpty()) { // Se as listas estiverem vazias
                            db.collection("usuarios").document(uid).update("hasFilmesFavoritos", false); // Atualiza o documento no Firestore
                        }
                        docRef.update("imgUrls", urls, "filmesFavoritos", titulos) // Atualiza as listas no Firestore
                                .addOnSuccessListener(aVoid -> Log.d(TAG, "Movie removed from Firestore")) // Adiciona um listener de sucesso
                                .addOnFailureListener(e -> Log.e(TAG, "Error updating document", e)); // Adiciona um listener de falha
                        if (titulos.isEmpty()) { // Se a lista de títulos estiver vazia
                            view.exibeMsg("Você não tem filmes favoritos."); // Exibe a mensagem
                            view.exibeListaVazia(); // Exibe a lista vazia
                        } else { // Se a lista de títulos não estiver vazia
                            view.exibeMsg(titulo + " foi removido dos favoritos."); // Exibe a mensagem
                            view.recarregaActivity(); // Recarrega a activity
                        }
                    }
                    checkListaVazia(Objects.requireNonNull(titulos)); // Verifica se a lista está vazia
                }
            }).addOnFailureListener(e -> { // Adiciona um listener de falha
                Log.e(TAG, "Error getting document", e); // Loga o erro
            });
        }
    }

    public void checkListaVazia(List<String> titulos) { // Define o método de checar lista vazia
        if (titulos == null) { // Se a lista de títulos for nula
            view.exibeMsg("Você não tem filmes favoritos."); // Exibe a mensagem
            view.exibeListaVazia(); // Exibe a lista vazia
        } else if (titulos.isEmpty()) { // Se a lista de títulos estiver vazia
            view.exibeMsg("Você não tem filmes favoritos."); // Exibe a mensagem
            view.exibeListaVazia(); // Exibe a lista vazia
        }
    }

    public void getUrlFavorito(int index, UrlCallback callback) { // Define o método de obter URL do favorito
        setImgFavorito(index, new UrlCallback() { // Define o callback
            @Override
            public void onUrlReceived(String url) { // Define o método de URL recebida
                callback.onUrlReceived(url);  // Passa a URL para o callback
            }

            @Override
            public void onFailure(String errorMessage) { // Define o método de falha
                callback.onFailure(errorMessage);  // Passa a mensagem de erro para o callback
            }
        });
    }

    public void exibeFavoritos(RecyclerView recyclerView) { // Define o método de exibir favoritos
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Adquire uma instância do Firestore
        FirebaseAuth auth = FirebaseAuth.getInstance(); // Adquire uma instância do FirebaseAuth
        String currentUserUid = auth.getCurrentUser().getUid(); // Adquire o UID do usuário atual
        db.collection("usuarios").document(currentUserUid) // Adquire a referência do documento do usuário atual
                .get() // Obtém o documento
                .addOnCompleteListener(task -> { // Adiciona um listener de sucesso
                    if (task.isSuccessful()) { // Se a tarefa for bem sucedida
                        DocumentSnapshot userDocument = task.getResult(); // Adquire o documento
                        if (userDocument.exists()) { // Se o documento existir
                            List<String> favoritos = (List<String>) userDocument.get("filmesFavoritos"); // Adquire a lista de favoritos
                            checkListaVazia(favoritos); // Verifica se a lista está vazia
                            if (favoritos != null) { // Se a lista de favoritos não for nula
                                FilmesFavoritosAdapter filmesFavoritosAdapter = new FilmesFavoritosAdapter(favoritos, this); // Cria um adapter
                                recyclerView.setAdapter(filmesFavoritosAdapter); // Define o adapter
                            }
                        } else { // Se o documento não existir
                            Log.d(TAG, "Documento não encontrado."); // Loga a mensagem
                        }
                    } else { // Se a tarefa não for bem sucedida
                        Log.d(TAG, "Erro ao acessar documento: ", task.getException()); // Loga a mensagem
                    }
                });
    }
}
