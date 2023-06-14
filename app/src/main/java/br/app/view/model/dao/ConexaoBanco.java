package br.app.view.model.dao;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.app.view.model.bean.Usuario;

public class ConexaoBanco {

    private static ConexaoBanco instancia = new ConexaoBanco();
    private List<Usuario> bancoDeDados = new ArrayList<>();

    public static ConexaoBanco getInstance() {
        return instancia;
    }

    public boolean salvar (Usuario object){
        return bancoDeDados.add(object);
    }

    public Usuario remover (int indice){
        return bancoDeDados.remove(indice);
    }

    public void alterar (int indice, Usuario object){
        bancoDeDados.set(indice, object);
    }

    public List<Usuario> listar(){
        return bancoDeDados;
    }

    public Usuario getUser(int indice){
        return bancoDeDados.get(indice);
    }
}
