package com.example.applistatelefonica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConexaoBanco extends SQLiteOpenHelper {

    private static final String NAME_DATABASE = "lista_telefonica";
    private static  final int VERSION = 1;

    public ConexaoBanco(Context context) {
        super(context, NAME_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS contato(" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(60)," +
                        "telefone VARCHAR(20)," +
                        "operadora VARCHAR(8)," +
                        "email VARCHAR(65)," +
                        "data_criacao DATETIME," +
                        "data_alteracao DATETIME" +
                    ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static String currentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return  formatter.format(date);
    }

    public boolean insert(ContentValues valores, String tabela) {
        valores.put("data_criacao", ConexaoBanco.currentDateTime());
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.insert(tabela, null, valores);
            return true;
        } catch (SQLiteException e) {
            return  false;
        }
    }

    public boolean update(int id, ContentValues valores, String tabela) {
        valores.put("data_alteracao", ConexaoBanco.currentDateTime());
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(tabela, valores, " id = "+ id, null);
            return true;
        } catch (SQLiteException e) {
            return  false;
        }
    }

    public boolean delete(int id, String tabela) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tabela, " id = "+ id, null);
            return true;
        } catch (SQLiteException e) {
            return  false;
        }
    }

    public Cursor runQuery(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return  db.rawQuery(sql, null );
    }

}
