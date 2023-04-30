package com.uniritter.to100ideia.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.data.network.FirebaseService;
import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.unirriter.api_filmes.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseService mFirebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseService = new FirebaseService();

        inicializar();



    }

    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    private void inicializar() {
        binding.emailField.requestFocus();
        binding.acessarBtn.setOnClickListener(v -> validaDados());
        binding.esqueciSenhaBtn.setOnClickListener(v -> recuperaSenha());
        binding.cadastraBtn.setOnClickListener(v -> acessaActivity(CadastroActivity.class));
    }

    private void recuperaSenha() {
        String email = binding.emailField.getText().toString().trim();

        if(!email.isEmpty()) {
            mFirebaseService.recuperaSenhaFirebase(this, email, mAuth);
        } else {
            Toast.makeText(this, "Informe seu email para recuperação.", Toast.LENGTH_SHORT).show();
        }
    }
    private void validaDados() {
        String email = binding.emailField.getText().toString().trim();
        String senha = binding.senhaField.getText().toString().trim();

        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                mFirebaseService.loginFirebase(this, email, senha, mAuth);
            } else {
                Toast.makeText(this, "Informe sua senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email.", Toast.LENGTH_SHORT).show();
        }
    }
}