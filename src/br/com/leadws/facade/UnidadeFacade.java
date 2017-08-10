/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.UnidadeDao;
import br.com.leadws.model.Unidadenegocio;

/**
 *
 * @author jizid
 */
public class UnidadeFacade {
    
    public Unidadenegocio getUsuarioResponsavel(int idUnidade){
        UnidadeDao unidadeDao = new UnidadeDao();
        return unidadeDao.getUsuarioResponsavel(idUnidade);
    }
    
}
