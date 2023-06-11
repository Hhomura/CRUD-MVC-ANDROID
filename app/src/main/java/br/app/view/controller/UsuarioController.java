package br.app.view.controller;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import br.app.view.model.bean.Usuario;
import br.app.view.model.dao.ConexaoBanco;

public class UsuarioController {

    public boolean salvar(Usuario usuario) {
        return ConexaoBanco.getInstance().salvar(usuario);
    }

    public Usuario remover(Usuario usuario) {
        List<Usuario> listaUsuarios = listar();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getSenha()==usuario.getSenha()&&listaUsuarios.get(i).getEmail().equalsIgnoreCase(usuario.getEmail())){
                Usuario usuarioAntigo = (Usuario) ConexaoBanco.getInstance().remover(i);
                return usuarioAntigo;
            }
        }
        return null;
    }
    public Usuario alterar(Usuario usuario){
        List<Usuario> listaUsuarios = listar();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getSenha()==usuario.getSenha()&&listaUsuarios.get(i).getEmail().equalsIgnoreCase(usuario.getEmail())){
                Usuario usuarioAntigo = ConexaoBanco.getInstance().alterar(i, usuario);
                return usuarioAntigo;
            }
        }
        return null;
    }

    public List<Usuario> listar(){
        List<Usuario> listaUsuarios = ConexaoBanco.getInstance().listar();
        Collections.sort(listaUsuarios);
        return listaUsuarios;
    }

    public Usuario logar(Usuario usuario){
        List<Usuario> listaUsuarios = listar();
        for (Usuario usuarioDoBanco:listaUsuarios) {
            if (usuarioDoBanco.getSenha()==usuario.getSenha()&&usuarioDoBanco.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                return usuarioDoBanco;
            }
        }
        return null;
    }

    public boolean logarUsuario(Usuario usuario){
        List<Usuario> listaUsuarios = listar();
        for(Usuario user : listaUsuarios){
            if(user == usuario){
                return true;
            }
        }
        return false;
    }

    public boolean cadastrarUsuario(Usuario user) {
        if (!user.getEmail().isEmpty() && !user.getNome().isEmpty() && user.getSenha() > -1 ) {
            Log.e("Entrou", "Entrou");
            return ConexaoBanco.getInstance().salvar(user);
        }else{
            Log.e("Erro", "Erro");
        }
        return false;
    }
}
