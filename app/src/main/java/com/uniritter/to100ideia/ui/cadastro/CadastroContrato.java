package com.uniritter.to100ideia.ui.cadastro;

import android.app.Activity;
import android.content.Context;

public interface CadastroContrato {

    interface CadastroView {
        void mostraMsg(String message);
        void exibirProgress();
        void esconderProgress();
    }

    interface CadastroPresenter {
        void validaDados(Activity activity, Context context, String email, String senha);
        void criarContaFirebase(Activity activity, String email, String senha);
    }
}
