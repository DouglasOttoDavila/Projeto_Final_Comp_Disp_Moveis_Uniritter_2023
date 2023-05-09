package com.uniritter.to100ideia.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.data.network.FirebaseService;
import com.uniritter.to100ideia.ui.menu.MenuActivity;

public class LoginPresenter implements LoginContrato.LoginPresenter {

    private LoginContrato.LoginView view;

    public LoginPresenter(LoginContrato.LoginView view) {
        this.view = view;
    }

    FirebaseAuth mAuth;
    FirebaseService mFirebase;

    //Método para verificar se o usuário já está logado no app e acessar o menu principal
    public void validaLoginAtivo(Activity activity, Boolean isUserLogged) {
        if(isUserLogged) {
            view.mostraMsg("Bem-vindo(a) de volta!");
            activity.finish();
            activity.startActivity(new Intent(activity, MenuActivity.class));
        } else {
            view.mostraMsg("Insira seus dados para login.");
        }
    }

    public void recuperaSenha(Activity activity, String email) {
        //String email = binding.emailField.getText().toString().trim();
        //Inicializa o Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseService();

        if (email.isEmpty()) {
            view.mostraMsg("Informe seu email já cadastrado.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.mostraMsg("O endereço de email é inválido.");
            return;
        } else {
            mFirebase.recuperaSenhaFirebase(activity, email, mAuth);
        }
    }
    public void validaDados(Activity activity, String email, String senha) {
        //Inicializa o Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseService();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(senha)) {
            view.mostraMsg("Informe seu email e senha.");
            return;
        }
        if (email.isEmpty()) {
            view.mostraMsg("Informe seu email.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.mostraMsg("O endereço de email é inválido.");
            return;
        }
        if (senha.isEmpty()) {
            view.mostraMsg("Informe sua senha.");
            return;
        } else {
            mFirebase.loginFirebase(activity, email, senha, mAuth);
        }
    }

}
