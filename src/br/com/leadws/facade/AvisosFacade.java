/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.AvisosDao;
import br.com.leadws.model.Avisos;
import br.com.leadws.model.Avisousuario;

/**
 *
 * @author wolverine
 */
public class AvisosFacade {
    
    public Avisos salvar(Avisos aviso) {
        AvisosDao avisosDao = new AvisosDao();
        return avisosDao.salvar(aviso);
    }
    
    public Avisousuario salvar(Avisousuario avisoUsuario) {
        AvisosDao avisosDao = new AvisosDao();
        return avisosDao.salvar(avisoUsuario);
    }
    
}
