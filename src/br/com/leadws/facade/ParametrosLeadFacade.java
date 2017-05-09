/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.ParametrosLeadDao;
import br.com.leadws.model.Parametroslead;

/**
 *
 * @author jizid
 */
public class ParametrosLeadFacade {
    
    public Parametroslead get(){
        ParametrosLeadDao parametrosLeadDao = new ParametrosLeadDao();
        return parametrosLeadDao.get();
    }
    
    public Parametroslead salvar(Parametroslead parametrosLead) {
        ParametrosLeadDao parametrosLeadDao = new ParametrosLeadDao();
        return parametrosLeadDao.salvar(parametrosLead);
    }
    
    
    
}
