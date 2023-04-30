package com.uniritter.to100ideia.data.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.menu.MenuActivity;

public class FirebaseService {

    private FirebaseAuth mAuth;

    public FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginFirebase(Activity activity, String email, String senha, FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(activity, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                        activity.finish();
                        activity.startActivity(new Intent(activity, MenuActivity.class));
                    } else {
                        Toast.makeText(activity, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void recuperaSenhaFirebase(Context context, String email, FirebaseAuth firebaseAuth) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(context, "O link para recuperação de senha foi enviado para o email informado.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}