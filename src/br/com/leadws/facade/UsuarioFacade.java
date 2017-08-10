/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.UsuarioDao;
import br.com.leadws.model.Usuario;
import java.util.List;

/**
 *
 * @author wolverine
 */
public class UsuarioFacade {
    
    public List<Usuario> consultar(int unidade){
        UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.consultar(unidade);
    }
    
    public void salvar(Usuario usuario){
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.salvar(usuario);
    }
    
}
