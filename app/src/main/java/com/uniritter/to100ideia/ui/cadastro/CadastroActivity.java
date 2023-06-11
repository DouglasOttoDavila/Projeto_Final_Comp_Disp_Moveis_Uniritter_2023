package com.uniritter.to100ideia.ui.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.unirriter.api_filmes.databinding.ActivityCadastroBinding;

public class CadastroActivity
        extends AppCompatActivity
        implements CadastroContrato.CadastroView {

    private ActivityCadastroBinding binding; //Declara o binding da activity
    private CadastroContrato.CadastroPresenter presenter; //Declara o presenter da activity

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Método chamado ao criar a activity
        super.onCreate(savedInstanceState); //Chama o método da classe pai
        binding = ActivityCadastroBinding.inflate(getLayoutInflater()); //Inicializa o binding
        View view = binding.getRoot(); //Pega a view do binding
        setContentView(view); //Seta a view da activity

        presenter = new CadastroPresenter(this); //Inicializa o presenter

        binding.cadastrarBtn.setOnClickListener(v -> { //Seta o método de cadastro para o botão
            String email = binding.emailCadastroField.getText().toString().trim(); //Pega o email digitado
            String senha = binding.senhaCadastroField.getText().toString().trim(); //Pega a senha digitada
            presenter.validaDados(this,this, email, senha); //Chama o método de validação do presenter
        });

        binding.theMovieDbLogo.setOnClickListener(v -> { //Seta o método de redirecionamento para o site do TheMovieDB
            String url = "https://www.themoviedb.org/"; //URL do site
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); //Cria um intent para abrir o navegador
            startActivity(intent); //Inicia a activity
        });
    }

    @Override
    public void esconderProgress() { //Esconde a barra de progresso
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void exibirProgress() { //Exibe a barra de progresso
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostraMsg(String message) { //Mostra uma mensagem na tela
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}