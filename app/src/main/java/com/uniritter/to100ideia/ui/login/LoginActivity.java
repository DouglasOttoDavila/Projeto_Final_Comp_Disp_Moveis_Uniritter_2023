package com.uniritter.to100ideia.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.uniritter.to100ideia.ui.listaFilmesPopulares.ListaFilmesActivity;
import com.uniritter.to100ideia.ui.menu.MenuActivity;
import com.unirriter.api_filmes.R;
import com.unirriter.api_filmes.databinding.ActivityLoginBinding;
import com.unirriter.api_filmes.databinding.ActivityMenuBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    //Declara o objeto de autenticação do Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        inicializar();

        //Inicializa o objeto de autenticação do Firebase
        mAuth = FirebaseAuth.getInstance();

    }

    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    private void inicializar() {
        binding.acessarBtn.setOnClickListener(v -> validaDados());
        binding.esqueciSenhaBtn.setOnClickListener(v -> recuperaSenha());
        binding.cadastraBtn.setOnClickListener(v -> acessaActivity(CadastroActivity.class));
    }
    private void loginFirebase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(this, MenuActivity.class));
                    } else {
                        Toast.makeText(this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void recuperaSenhaFirebase(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "O link para recuperação de senha foi enviado para o email informado.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void recuperaSenha() {
        String email = binding.emailField.getText().toString().trim();

        if(!email.isEmpty()) {
            recuperaSenhaFirebase(email);
        } else {
            Toast.makeText(this, "Informe seu email para recuperação.", Toast.LENGTH_SHORT).show();
        }
    }
    private void validaDados() {
        String email = binding.emailField.getText().toString().trim();
        String senha = binding.senhaField.getText().toString().trim();

        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                loginFirebase(email, senha);
            } else {
                Toast.makeText(this, "Informe sua senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email.", Toast.LENGTH_SHORT).show();
        }
    }
}