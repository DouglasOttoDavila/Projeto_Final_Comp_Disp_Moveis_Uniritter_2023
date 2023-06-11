package com.uniritter.to100ideia.ui.login;

import android.app.Activity;

public interface LoginContrato { // Interface de comunicação entre a View e o Presenter

        interface LoginView {
            void mostraMsg(String message);
        }

        interface LoginPresenter { // Interface de comunicação entre o Presenter e o Model
            void validaDados(Activity activity, String email, String senha);
            void recuperaSenha(Activity activity, String email);
            void validaLoginAtivo(Activity activity, Boolean isUserLogged);
        }

}
