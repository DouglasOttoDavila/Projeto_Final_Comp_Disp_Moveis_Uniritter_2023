package com.uniritter.to100ideia.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.unirriter.api_filmes.databinding.ActivityLoginBinding;

public class LoginActivity //Classe da activity de login
        extends AppCompatActivity //Extende a classe AppCompatActivity
        implements LoginContrato.LoginView { //Implementa a interface LoginView

    ActivityLoginBinding binding; //Declara o binding da activity
    private LoginContrato.LoginPresenter presenter; //Declara o presenter da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Método de criação da activity
        super.onCreate(savedInstanceState); //Chama o método onCreate da classe AppCompatActivity
        binding = ActivityLoginBinding.inflate(getLayoutInflater()); //Inicializa o binding da activity
        View view = binding.getRoot(); //Inicializa a view da activity
        setContentView(view); //Seta o conteúdo da activity

        presenter = new LoginPresenter(this); //Inicializa o presenter da activity

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