/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.ContatoDao;
import br.com.leadws.model.Leads;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jizid
 */
public class ContatoFacade {
    
    public List<Leads> list(int idContato, int unidade){
        ContatoDao contatoDao = new ContatoDao();
        try {
            return contatoDao.list(idContato, unidade);
        } catch (SQLException ex) {
            Logger.getLogger(ContatoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public int getIdLead(){
        ContatoDao contatoDao = new ContatoDao();
        return contatoDao.getIdLead();
    }
    
    public void salvar(Leads lead){
        ContatoDao contatoDao = new ContatoDao();
        contatoDao.salvar(lead);
    }
    
}
