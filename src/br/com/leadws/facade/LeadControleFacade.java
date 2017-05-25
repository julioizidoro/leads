/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.LeadControleDao;
import br.com.leadws.model.Leadcontrole;

/**
 *
 * @author Wolverine
 */
public class LeadControleFacade {
    
    public void salvar(Leadcontrole leadControle)  {
        LeadControleDao leadControleDao = new LeadControleDao();
        leadControleDao.salvar(leadControle);
    }
    
}
