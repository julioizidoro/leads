/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.LeadDao;
import br.com.leadws.model.Lead;

/**
 *
 * @author jizid
 */
public class LeadFacade {
    
    public Lead salvar(Lead lead)  {
        LeadDao leadDao = new LeadDao();
        return leadDao.salvar(lead);
    }
}
