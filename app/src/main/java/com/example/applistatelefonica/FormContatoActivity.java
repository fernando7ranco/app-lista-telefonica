package com.example.applistatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormContatoActivity extends AppCompatActivity {

    private EditText nomeContato;
    private Spinner spOperadoraContato;
    private EditText telefoneContato;
    private EditText emailContato;
    private Button btnSalvarContato;
    private String acao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contato);

        this.acao = getIntent().getStringExtra("acao");

        this.nomeContato = findViewById(R.id.nomeContato);
        this.spOperadoraContato = findViewById(R.id.spOperadoraContato);
        this.telefoneContato = findViewById(R.id.telefoneContato);
        this.emailContato = findViewById(R.id.emailContato);
        this.btnSalvarContato = findViewById(R.id.btnSalvarContato);

        this.btnSalvarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        this.inicializarForumalrio();
    }

    private void setDadosFormulario(String n, int o, String t, String e) {
        this.nomeContato.setText(n);
        this.spOperadoraContato.setSelection(o, true);
        this.telefoneContato.setText(t);
        this.emailContato.setText(e);
    }

    private void inicializarForumalrio() {
        this.contato = new Contato();

        if (this.acao.equals("editar")) {
            int idContato = getIntent().getIntExtra("idContato", 0);
            this.contato.getById(this, idContato);
            String[] operadoras = this.getResources().getStringArray(R.array.operadoras);

            this.setDadosFormulario(
                    contato.getNome(),
                    contato.getIndexOperadora(operadoras),
                    contato.getTelefone(),
                    contato.getEmail()
            );
        } else {
            this.setDadosFormulario("", 0, "", "");
        }
    }

    private void salvar() {
        String nome = this.nomeContato.getText().toString();
        String operadora = this.spOperadoraContato.getSelectedItem().toString();
        String telefone = this.telefoneContato.getText().toString();
        String email = this.emailContato.getText().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Você não pode criar um contato sem nome", Toast.LENGTH_LONG).show();
        } else if(this.spOperadoraContato.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Você não pode criar um contato sem selecionar uma operadora", Toast.LENGTH_LONG).show();
        } else if(telefone.isEmpty()) {
            Toast.makeText(this, "Você não pode criar um contato sem informar o número de telefone", Toast.LENGTH_LONG).show();
        } else {
            this.contato.setNome(nome);
            this.contato.setOperadora(operadora);
            this.contato.setTelefone(telefone);
            this.contato.setEmail(email);
            this.contato.save(this);
            this.setDadosFormulario("", 0, "", "");
            finish();
        }
    }

}