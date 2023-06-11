package com.uniritter.to100ideia.ui.cadastro;

import android.app.Activity;
import android.content.Context;

public interface CadastroContrato { // Interface para o cadastro de usuário

    interface CadastroView { // Interface para a view do cadastro
        void mostraMsg(String message);
        void exibirProgress();
        void esconderProgress();
    }

    interface CadastroPresenter { // Interface para o presenter do cadastro
        void validaDados(Activity activity, Context context, String email, String senha); // Valida os dados do cadastro
        void criarContaFirebase(Activity activity, String email, String senha); // Cria a conta no firebase
    }
}
