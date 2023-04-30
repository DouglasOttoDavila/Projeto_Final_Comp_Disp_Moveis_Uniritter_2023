package com.uniritter.to100ideia.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uniritter.to100ideia.ui.cadastro.CadastroActivity;
import com.uniritter.to100ideia.ui.listaFilmesPopulares.ListaFilmesActivity;
import com.uniritter.to100ideia.ui.menu.MenuActivity;
import com.unirriter.api_filmes.R;

public class LoginActivity extends AppCompatActivity {

    private Button acessarBtn;
    private Button esqueciSenhaBtn;
    private Button cadastraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializar();

    }

    private void acessaActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    private void inicializar() {
        acessarBtn = findViewById(R.id.acessarBtn);
        esqueciSenhaBtn = findViewById(R.id.esqueciSenhaBtn);
        cadastraBtn = findViewById(R.id.cadastraBtn);

        acessarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ListaFilmesActivity.class);
            }
        });

        esqueciSenhaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ListaFilmesActivity.class);
            }
        });

        cadastraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(CadastroActivity.class);
            }
        });
    }
}