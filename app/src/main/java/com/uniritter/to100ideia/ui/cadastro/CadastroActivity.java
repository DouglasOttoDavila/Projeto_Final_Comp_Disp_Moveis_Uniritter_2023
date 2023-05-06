package com.uniritter.to100ideia.ui.cadastro;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.ui.login.LoginActivity;
import com.unirriter.api_filmes.R;
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

        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    private void validaDados() {
        String email = binding.emailCadastroField.getText().toString().trim();
        String senha = binding.senhaCadastroField.getText().toString().trim();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Informe seu email e uma senha para criação da conta.", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Informe um email para criação da conta.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "O endereço de email informado é inválido.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha sua senha desejada.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.length() < 8 || !senha.matches(".*[A-Z].*") || !senha.matches(".*\\d.*") || !senha.matches(".*[!@#$%^&*?].*")) {
            /*Toast.makeText(this, "A senha deve conter ao menos 8 caracteres" + "\n e incluir ao menos uma letra maiúscula, um número \n" + "e um caracter especial.", Toast.LENGTH_LONG).show();*/
            Dialog popup = new Dialog(this);
            popup.setContentView(R.layout.popup);

            TextView tituloPop = popup.findViewById(R.id.popup_titulo);
            TextView textoPop = popup.findViewById(R.id.popup_texto);
            tituloPop.setText("Senha Inválida");
            textoPop.setText("A senha deve conter ao menos 8 caracteres, incluir ao menos uma letra maiúscula, um número e um caractere especial.");

            popup.show();
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        criarContaFirebase(email, senha);


        /*if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                criarContaFirebase(email, senha);
            } else {
                Toast.makeText(this, "Informe sua senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email.", Toast.LENGTH_SHORT).show();
        }*/
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