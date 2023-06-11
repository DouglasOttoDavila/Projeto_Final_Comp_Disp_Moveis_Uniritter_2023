package com.uniritter.to100ideia.data.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.menu.MenuActivity;

public class FirebaseService {

    private FirebaseAuth mAuth; // Firebase Authentication
    public void Firebase() { mAuth = FirebaseAuth.getInstance(); } // Construtor do FirebaseService

    public void loginFirebase(Activity activity, String email, String senha, FirebaseAuth firebaseAuth) { // Método para realizar o login no Firebase

        firebaseAuth.signInWithEmailAndPassword(email, senha) // Realiza o login no Firebase
                .addOnCompleteListener(task -> { // Listener para verificar se o login foi realizado com sucesso
                    if(task.isSuccessful()) { // Caso o login tenha sido realizado com sucesso

                        //SHARED PREFERENCES
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("UserSession", Context.MODE_PRIVATE); // Cria um SharedPreferences para armazenar o email do usuário
                        SharedPreferences.Editor editor = sharedPreferences.edit(); // Cria um editor para o SharedPreferences
                        editor.putBoolean("logado", true); // Armazena o valor true para a chave logado
                        editor.putString("email", email); // Armazena o email do usuário para a chave email
                        editor.apply(); // Aplica as alterações no SharedPreferences

                        Toast.makeText(activity, "Logado com sucesso!", Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de sucesso
                        activity.finish(); // Finaliza a activity de login
                        activity.startActivity(new Intent(activity, MenuActivity.class)); // Inicia a activity do menu
                    } else {
                        Toast.makeText(activity, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de erro
                    }
                });
    }

    public void recuperaSenhaFirebase(Context context, String email, FirebaseAuth firebaseAuth) { // Método para recuperar a senha do usuário

        firebaseAuth.sendPasswordResetEmail(email) // Envia um email para o usuário com um link para redefinir a senha
                .addOnCompleteListener(task -> { // Listener para verificar se o email foi enviado com sucesso
                    if(task.isSuccessful()) { // Caso o email tenha sido enviado com sucesso
                        Toast.makeText(context, "O link para recuperação de senha foi enviado para o email informado.", Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de sucesso
                    } else { // Caso o email não tenha sido enviado com sucesso
                        Toast.makeText(context, "Usuário não encontrado.", Toast.LENGTH_SHORT).show(); // Exibe uma mensagem de erro
                    }
                });
    }

}
