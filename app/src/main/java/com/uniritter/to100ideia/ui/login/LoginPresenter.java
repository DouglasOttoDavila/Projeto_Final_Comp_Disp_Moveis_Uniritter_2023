package com.uniritter.to100ideia.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.data.network.FirebaseService;
import com.uniritter.to100ideia.ui.menu.MenuActivity;

public class LoginPresenter // Implementa a interface LoginContrato.LoginPresenter
        implements LoginContrato.LoginPresenter { //Implementa a interface LoginContrato.LoginPresenter

    private LoginContrato.LoginView view; //Declara a variável view do tipo LoginContrato.LoginView

    public LoginPresenter(LoginContrato.LoginView view) { //Construtor da classe LoginPresenter
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

    public void recuperaSenha(Activity activity, String email) { //Método para recuperar a senha do usuário
        //Inicializa o Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseService();

        if (email.isEmpty()) { //Verifica se o campo de email está vazio
            view.mostraMsg("Informe seu email já cadastrado.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { //Verifica se o email é válido
            view.mostraMsg("O endereço de email é inválido.");

        } else { //Se o email for válido, chama o método recuperaSenhaFirebase da classe FirebaseService
            mFirebase.recuperaSenhaFirebase(activity, email, mAuth);
        }
    }
    public void validaDados(Activity activity, String email, String senha) { //Método para validar os dados de login do usuário
        //Inicializa o Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseService();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(senha)) { //Verifica se os campos de email e senha estão vazios
            view.mostraMsg("Informe seu email e senha.");
            return;
        }
        if (email.isEmpty()) { //Verifica se o campo de email está vazio
            view.mostraMsg("Informe seu email.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { //Verifica se o email é válido
            view.mostraMsg("O endereço de email é inválido.");
            return;
        }
        if (senha.isEmpty()) { //Verifica se o campo de senha está vazio
            view.mostraMsg("Informe sua senha.");
        } else {
            mFirebase.loginFirebase(activity, email, senha, mAuth); //Se os campos de email e senha estiverem preenchidos, chama o método loginFirebase da classe FirebaseService
        }
    }

}
