package com.uniritter.to100ideia.ui.login;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.uniritter.to100ideia.data.network.FirebaseService;
import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.uniritter.to100ideia.ui.menu.MenuActivity;
import com.unirriter.api_filmes.databinding.ActivityLoginBinding;
public class LoginActivity
        extends AppCompatActivity
        implements LoginContrato.LoginView {

    //Cria o binding para a activity
    ActivityLoginBinding binding;

    //Declara o presenter da activity
    private LoginContrato.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Infla o layout da activity através do binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Inicializa o presenter da activity
        presenter = new LoginPresenter(this);

        //Verifica se o usuário já está logado através do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        boolean logado = sharedPreferences.getBoolean("logado", false);

        //Inicializa a função de validação de login ativo
        presenter.validaLoginAtivo(this, logado); //Verifica se o usuário já está logado

        //Inicializa os componentes da activity
        binding.emailField.requestFocus(); //Coloca o foco no campo de email

        //Realiza a validação do login e acessa o menu principal
        binding.acessarBtn.setOnClickListener(v -> {
            //Inicializa e atribui variáveis para usuário e senha
            String email = binding.emailField.getText().toString().trim();
            String senha = binding.senhaField.getText().toString().trim();
            presenter.validaDados(this, email, senha);
        });

        //Realiza a recuperação de senha
        binding.esqueciSenhaBtn.setOnClickListener(v -> {
            //Inicializa e atribui variáveis para usuário e senha
            String email = binding.emailField.getText().toString().trim();
            presenter.recuperaSenha(this, email);
        });

        //Acessa a tela de cadastro
        binding.cadastraBtn.setOnClickListener(v -> {
            acessaActivity(CadastroActivity.class);
        }); //Acessa a tela de cadastro

        //Acessa o site do The Movie DB
        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    //Método para acessar uma nova activity
    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void mostraMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}