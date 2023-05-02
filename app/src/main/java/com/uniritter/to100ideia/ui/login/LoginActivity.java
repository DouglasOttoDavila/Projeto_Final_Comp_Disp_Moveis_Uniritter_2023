package com.uniritter.to100ideia.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.data.network.FirebaseService;
import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.unirriter.api_filmes.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseService mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String senha = sharedPreferences.getString("senha", "");
        binding.emailField.setText(email);
        binding.senhaField.setText(senha);

        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseService();

        inicializar();


    }

    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    private void inicializar() {
        validaLoginAtivo();

        binding.emailField.requestFocus();
        binding.acessarBtn.setOnClickListener(v -> validaDados());
        binding.esqueciSenhaBtn.setOnClickListener(v -> recuperaSenha());
        binding.cadastraBtn.setOnClickListener(v -> acessaActivity(CadastroActivity.class));
        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
    private void validaLoginAtivo() {
        String email = binding.emailField.getText().toString().trim();
        String senha = binding.senhaField.getText().toString().trim();
        if (email.isEmpty() || senha.isEmpty()) {
            // Fields are empty, do nothing and let the user enter the login information
        } else {
            // Fields are not empty, attempt to log in with the saved email and password
            mFirebase.loginFirebase(this, email, senha, mAuth);
        }
    }
    private void recuperaSenha() {
        String email = binding.emailField.getText().toString().trim();

        if(!email.isEmpty()) {
            mFirebase.recuperaSenhaFirebase(this, email, mAuth);
        } else {
            Toast.makeText(this, "Informe seu email para recuperação.", Toast.LENGTH_SHORT).show();
        }
    }
    private void validaDados() {
        String email = binding.emailField.getText().toString().trim();
        String senha = binding.senhaField.getText().toString().trim();

        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                mFirebase.loginFirebase(this, email, senha, mAuth);
            } else {
                Toast.makeText(this, "Informe sua senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email.", Toast.LENGTH_SHORT).show();
        }
    }
}