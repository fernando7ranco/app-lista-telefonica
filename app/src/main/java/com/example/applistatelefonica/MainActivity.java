package com.example.applistatelefonica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvContatos;
    private List<Contato> listaContatos;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormContatoActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        this.lvContatos = findViewById(R.id.lvContatos);

        this.lvContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, FormContatoActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idContato", listaContatos.get(i).getId());
                startActivity(intent);
            }
        });

        this.lvContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluirContato(i);
                return true;
            }
        });

        this.carregarListaContatos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.carregarListaContatos();
    }

    private void carregarListaContatos() {

        this.listaContatos = Contato.getAll(this);

        if ( this.listaContatos.size() == 0) {
            Contato contatoVazio = new Contato();
            contatoVazio.vazio();

            this.listaContatos.add(contatoVazio);

            this.lvContatos.setEnabled(false);
        } else {
            this.lvContatos.setEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.listaContatos);
        this.lvContatos.setAdapter(adapter);
    }

    private void excluirContato(int index) {

        Contato contato = this.listaContatos.get(index);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Excluir contato");
        alert.setIcon(android.R.drawable.ic_delete);
        alert.setMessage("Você deseja excluir permanentemente o contato: "+ contato.getNome() + " - " + contato.getTelefone() + "?");
        alert.setNeutralButton("Cancelar", null);
        alert.setPositiveButton("Sim excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                contato.delete(MainActivity.this);
                listaContatos.remove(index);
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listaContatos);
                lvContatos.setAdapter(adapter);
            }
        });
        alert.show();
    }
}