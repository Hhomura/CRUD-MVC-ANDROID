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
    public Usuario alterar(Usuario userOld, Usuario userAlter){
        List<Usuario> listaUsuarios = listar();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getSenha()==userOld.getSenha()&&listaUsuarios.get(i).getEmail().equalsIgnoreCase(userOld.getEmail())){
                ConexaoBanco.getInstance().alterar(i, userAlter);
            }
        }
        return null;
    }

    public Usuario getUser(Usuario user){
        List<Usuario> listaUsuarios = listar();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getSenha()==user.getSenha()&&listaUsuarios.get(i).getEmail().equalsIgnoreCase(user.getEmail())){
                return ConexaoBanco.getInstance().getUser(i);
            }
        }
        return null;
    }

    public List<Usuario> listar(){
        List<Usuario> listaUsuarios = ConexaoBanco.getInstance().listar();
        Collections.sort(listaUsuarios);
        return listaUsuarios;
    }

    public Usuario logar(String email, int senha){
        List<Usuario> listaUsuarios = listar();
        for (Usuario usuarioDoBanco:listaUsuarios) {
            if (usuarioDoBanco.getSenha()==senha &&usuarioDoBanco.getEmail().equalsIgnoreCase(email)) {
                return usuarioDoBanco;
            }
        }
        return null;
    }

    public boolean cadastrarUsuario(Usuario user) {
        if (!user.getEmail().isEmpty() && !user.getNome().isEmpty() && user.getSenha() > -1 ) {
            return ConexaoBanco.getInstance().salvar(user);
        }
        return false;
    }
}
