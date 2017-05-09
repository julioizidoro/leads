/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.UnidadeDao;

/**
 *
 * @author jizid
 */
public class UnidadeFacade {
    
    public int getUsuarioResponsavel(int idUnidade){
        UnidadeDao unidadeDao = new UnidadeDao();
        return unidadeDao.getUsuarioResponsavel(idUnidade);
    }
    
}
