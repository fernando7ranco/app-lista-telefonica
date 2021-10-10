package com.example.applistatelefonica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

abstract class ContatoDAO {

    protected static final String TABLE_NAME = "contato";

    public boolean save(Context context) {
        Contato c = this.referenciaContato();

        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("telefone", c.getTelefone());
        valores.put("operadora", c.getOperadora());
        valores.put("email", c.getEmail());

        ConexaoBanco conexao = new ConexaoBanco(context);

        if (c.getId() == 0) {
            c.setDataCriacao(ConexaoBanco.currentDateTime());
            return conexao.insert(valores, ContatoDAO.TABLE_NAME);
        } else {
            c.setDataAlteracao(ConexaoBanco.currentDateTime());
            return conexao.update(c.getId(), valores, ContatoDAO.TABLE_NAME);
        }
    }

    public boolean delete(Context context) {
        Contato c = this.referenciaContato();
        ConexaoBanco conexao = new ConexaoBanco(context);
        return conexao.delete(c.getId(), ContatoDAO.TABLE_NAME);
    }

    public static List<Contato> getAll(Context context){
        List<Contato> lista = new ArrayList<>();

        ConexaoBanco conexao = new ConexaoBanco(context);

        Cursor cursor = conexao.runQuery( "SELECT * FROM " + ContatoDAO.TABLE_NAME + " ORDER BY nome");

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                Contato c = new Contato();
                c.setId( cursor.getInt( 0 ) );
                c.setNome( cursor.getString(1) );
                c.setTelefone( cursor.getString(2) );
                c.setOperadora( cursor.getString(3) );
                c.setEmail( cursor.getString(4) );
                c.setDataCriacao( cursor.getString(5) );
                c.setDataAlteracao( cursor.getString(6) );

                lista.add( c );
            } while ( cursor.moveToNext() );
        }
        return lista;
    }

    public boolean getById(Context context, int id){
        ConexaoBanco conexao = new ConexaoBanco(context);
        Cursor cursor = conexao.runQuery("SELECT * FROM "+ ContatoDAO.TABLE_NAME + " WHERE id = "+ id);

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            Contato c = this.referenciaContato();
            c.setId( cursor.getInt( 0 ) );
            c.setNome( cursor.getString(1) );
            c.setTelefone( cursor.getString(2) );
            c.setOperadora( cursor.getString(3) );
            c.setEmail( cursor.getString(4) );
            c.setDataCriacao( cursor.getString(5) );
            c.setDataAlteracao( cursor.getString(6) );

            return true;
        }
        return false;
    }

    abstract Contato referenciaContato();

}
