package com.uniritter.to100ideia.ui.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.login.LoginActivity;
import com.unirriter.api_filmes.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {

    //Cria o binding para a activity
    private ActivityCadastroBinding binding;

    //Declara o objeto de autenticação do Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Inicializa o objeto de autenticação do Firebase
        mAuth = FirebaseAuth.getInstance();

        //Recupera os componentes da tela
        binding.cadastrarBtn.setOnClickListener(v -> validaDados());
    }

    private void validaDados() {
        String email = binding.emailCadastroField.getText().toString().trim();
        String senha = binding.senhaCadastroField.getText().toString().trim();

        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                criarContaFirebase(email, senha);
            } else {
                Toast.makeText(this, "Informe sua senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFirebase(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}