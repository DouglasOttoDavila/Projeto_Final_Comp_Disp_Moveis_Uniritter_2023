package com.uniritter.to100ideia.ui.login;

import android.app.Activity;

public interface LoginContrato {

        interface LoginView {
            void mostraMsg(String message);
        }

        interface LoginPresenter {
            void validaDados(Activity activity, String email, String senha);
            void recuperaSenha(Activity activity, String email);
            void validaLoginAtivo(Activity activity, Boolean isUserLogged);
        }

}
