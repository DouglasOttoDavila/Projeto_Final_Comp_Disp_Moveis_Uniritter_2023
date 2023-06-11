package com.uniritter.to100ideia.ui.cadastro;

import static android.content.ContentValues.TAG;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniritter.to100ideia.ui.login.LoginActivity;
import com.unirriter.api_filmes.R;
import java.util.HashMap;
import java.util.Map;

public class CadastroPresenter implements CadastroContrato.CadastroPresenter { // Classe que implementa a interface CadastroContrato.CadastroPresenter

    private CadastroContrato.CadastroView view;

    public CadastroPresenter(CadastroContrato.CadastroView view) { // Construtor da classe
        this.view = view;
    }

    public void cadastroFirestore(String email, String uid) { // Método que cadastra o usuário no Firestore

        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Instancia o Firestore

        Map<String, Object> usuario = new HashMap<>(); // Cria um HashMap para armazenar os dados do usuário
        usuario.put("email", email); // Adiciona o email do usuário no HashMap
        usuario.put("hasFilmesFavoritos", false); // Adiciona a informação de que o usuário não possui filmes favoritos no HashMap
        db.collection("usuarios").document(uid).set(usuario); // Cria um documento no Firestore com o uid do usuário e adiciona o HashMap com os dados do usuário
    }

    public void criarContaFirebase(Activity activity, String email, String senha) { // Método que cria a conta do usuário no Firebase

        FirebaseAuth mAuth; // Objeto de autenticação do Firebase

        mAuth = FirebaseAuth.getInstance(); // Instancia o objeto de autenticação do Firebase

        mAuth.createUserWithEmailAndPassword(email, senha) // Cria o usuário no Firebase
                .addOnCompleteListener(activity, task -> { // Adiciona um listener para verificar se a tarefa foi concluída
                    if(task.isSuccessful()) { // Se a tarefa foi concluída com sucesso
                        view.mostraMsg("Usuário cadastrado com sucesso!"); // Exibe uma mensagem de sucesso
                        FirebaseUser user = mAuth.getCurrentUser(); // Recupera o usuário atual
                        String uid = user.getUid(); // Recupera o uid do usuário atual
                        Log.d(TAG, "User added with ID: " + uid); // Exibe o uid do usuário atual no log
                        cadastroFirestore(email, uid); // Chama o método que cadastra o usuário no Firestore
                        activity.finish(); // Finaliza a activity atual
                        activity.startActivity(new Intent(activity, LoginActivity.class)); // Inicia a activity de login
                    } else { // Se a tarefa não foi concluída com sucesso
                        view.esconderProgress(); // Esconde a barra de progresso
                        view.mostraMsg("Erro ao cadastrar usuário."); // Exibe uma mensagem de erro
                    }
                });
    }

    public void validaDados(Activity activity, Context context, String email, String senha) { // Método que valida os dados informados pelo usuário
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(senha)) { // Se o email e a senha estiverem vazios
            view.mostraMsg("Informe seu email e uma senha para criação da conta.");  // Exibe uma mensagem de erro
            return;
        }
        if (email.isEmpty()) { // Se o email estiver vazio
            view.mostraMsg("Informe um email para criação da conta."); // Exibe uma mensagem de erro
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Se o email não for válido
            view.mostraMsg("O endereço de email informado é inválido."); // Exibe uma mensagem de erro
            return;
        }
        if (senha.isEmpty()) { // Se a senha estiver vazia
            view.mostraMsg("Preencha sua senha desejada."); // Exibe uma mensagem de erro
            return;
        }
        if (senha.length() < 8 || !senha.matches(".*[A-Z].*") || !senha.matches(".*\\d.*") || !senha.matches(".*[!@#$%^&*?].*")) { // Se a senha não atender aos requisitos
            Dialog popup = new Dialog(context); // Cria um popup
            popup.setContentView(R.layout.popup); // Define o layout do popup
            TextView tituloPop = popup.findViewById(R.id.popup_titulo); // Recupera o TextView do título do popup
            TextView textoPop = popup.findViewById(R.id.popup_texto); // Recupera o TextView do texto do popup
            tituloPop.setText("Senha Inválida"); // Define o título do popup
            textoPop.setText("A senha deve conter ao menos 8 caracteres, incluir ao menos uma letra maiúscula, um número e um caractere especial."); // Define o texto do popup
            popup.show(); // Exibe o popup
            return;
        }
        view.exibirProgress(); // Exibe a barra de progresso
        criarContaFirebase(activity, email, senha); // Chama o método que cria a conta do usuário no Firebase
    }
}
