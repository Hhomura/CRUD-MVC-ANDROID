package br.app.view.model.bean;

import java.io.Serializable;

public class Usuario implements Serializable, Comparable {

    private String nome;
    private String email;
    private int senha;

    public Usuario(String nome, String email, int senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
