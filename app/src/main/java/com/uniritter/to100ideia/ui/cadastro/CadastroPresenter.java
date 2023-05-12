package com.uniritter.to100ideia.ui.cadastro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniritter.to100ideia.ui.login.LoginActivity;
import com.unirriter.api_filmes.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CadastroPresenter implements CadastroContrato.CadastroPresenter {

    private CadastroContrato.CadastroView view;

    public CadastroPresenter(CadastroContrato.CadastroView view) {
        this.view = view;
    }

    public void cadastroFirestore(String email) {
        // Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("email", email);
        usuario.put("hasFilmesFavoritos", false);
        usuario.put("filmesFavoritos", Arrays.asList(""));

        db.collection("usuarios")
                .add(usuario);
    }

    public void criarContaFirebase(Activity activity, String email, String senha) {
        //Declara o objeto de autenticação do Firebase
        FirebaseAuth mAuth;

        //Inicializa o objeto de autenticação do Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, task -> {
                    if(task.isSuccessful()) {
                        view.mostraMsg("Usuário cadastrado com sucesso!");
                        //Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        activity.finish();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    } else {
                        view.esconderProgress();
                        //binding.progressBar.setVisibility(View.GONE);
                        view.mostraMsg("Erro ao cadastrar usuário.");
                        //Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT).show();
                    }
                });
        cadastroFirestore(email);
    }

    public void validaDados(Activity activity, Context context, String email, String senha) {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(senha)) {
            view.mostraMsg("Informe seu email e uma senha para criação da conta.");
            //Toast.makeText(this, "Informe seu email e uma senha para criação da conta.", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty()) {
            view.mostraMsg("Informe um email para criação da conta.");
            //Toast.makeText(this, "Informe um email para criação da conta.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.mostraMsg("O endereço de email informado é inválido.");
            //Toast.makeText(this, "O endereço de email informado é inválido.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            view.mostraMsg("Preencha sua senha desejada.");
            //Toast.makeText(this, "Preencha sua senha desejada.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.length() < 8 || !senha.matches(".*[A-Z].*") || !senha.matches(".*\\d.*") || !senha.matches(".*[!@#$%^&*?].*")) {
            /*Toast.makeText(this, "A senha deve conter ao menos 8 caracteres" + "\n e incluir ao menos uma letra maiúscula, um número \n" + "e um caracter especial.", Toast.LENGTH_LONG).show();*/
            Dialog popup = new Dialog(context);
            popup.setContentView(R.layout.popup);

            TextView tituloPop = popup.findViewById(R.id.popup_titulo);
            TextView textoPop = popup.findViewById(R.id.popup_texto);
            tituloPop.setText("Senha Inválida");
            textoPop.setText("A senha deve conter ao menos 8 caracteres, incluir ao menos uma letra maiúscula, um número e um caractere especial.");

            popup.show();
            return;
        }
        view.exibirProgress();
        //binding.progressBar.setVisibility(View.VISIBLE);
        criarContaFirebase(activity, email, senha);
    }
}
