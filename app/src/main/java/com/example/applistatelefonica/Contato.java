package com.example.applistatelefonica;

import android.content.Context;

import java.util.Arrays;

public class Contato extends ContatoDAO{

    private int id = 0;
    private String nome;
    private String telefone;
    private String operadora;
    private String email;
    private String dataCriacao;
    private String dataAlteracao;

    public Contato() {
        this.setId(0);
    }

    public Contato(int id, String nome, String telefone, String operadora, String email, String dataCriacao, String dataAlteracao) {
        this.setId(id);
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setOperadora(operadora);
        this.setEmail(email);
        this.setDataCriacao(dataCriacao);
        this.setDataAlteracao(dataAlteracao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public int getIndexOperadora(String[] operadoras) {
        int index = Arrays.binarySearch(operadoras, this.getOperadora());
        return index;
    }

    public void vazio() {
        this.setNome("vazio");
        this.setTelefone("");
        this.setOperadora("");
        this.setDataCriacao("");
        this.setDataAlteracao("");
    }
    @Override
    public String toString() {
        String data = this.dataAlteracao == null ? this.dataCriacao : this.dataAlteracao;
        return this.nome +" "+ this.operadora + " " + data;
    }

    Contato referenciaContato() {
        return this;
    }
}
