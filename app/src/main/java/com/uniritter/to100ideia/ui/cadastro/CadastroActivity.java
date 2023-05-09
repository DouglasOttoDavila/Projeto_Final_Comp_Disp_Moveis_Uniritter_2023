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

    //Cria o binding para a activity
    private ActivityCadastroBinding binding;


    //Declara o presenter da activity
    private CadastroContrato.CadastroPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Infla o layout da activity através do binding e seta o conteúdo da view
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Inicializa o presenter da activity
        presenter = new CadastroPresenter(this);

        //Seta o método de cadastro ao clicar no botão "cadastrar"
        binding.cadastrarBtn.setOnClickListener(v -> {
            String email = binding.emailCadastroField.getText().toString().trim();
            String senha = binding.senhaCadastroField.getText().toString().trim();
            presenter.validaDados(this,this, email, senha);
        });

        //Seta o método de abrir URL para o logo do The Movie DB
        binding.theMovieDbLogo.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }

    @Override
    public void esconderProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void mostraMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void exibirProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

}