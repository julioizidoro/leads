/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.dao;

import br.com.leadws.Singleton.ConexaoSingleton;
import br.com.leadws.model.Leads;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jizid
 */
public class ContatoDao {


    public List<Leads> list(int idContato, int unidade) throws SQLException {
        EntityManager manager = ConexaoSingleton.getInstanceLead();
        Query q = manager.createQuery("select c from Leads c where c.id>" + idContato + " and c.unidade=" + unidade
                                      + " and c.nome<>'' and c.capturada=0");
        if (q.getResultList().size() > 0) {
            return q.getResultList();
        }
        return null;
    }
    
    public int getIdLead(){
        EntityManager manager = ConexaoSingleton.getInstanceLead();
        Query q = manager.createQuery("SELECT MAX(id) FROM Leads");
        if (q.getResultList().size() > 0) {
            return (int) q.getResultList().get(0);
        }
        return 0;
    }
    
    public void salvar(Leads lead){
        EntityManager manager = ConexaoSingleton.getInstanceLead();
        manager.getTransaction().begin();
        lead.setCapturada(true);
        manager.merge(lead);
        manager.getTransaction().commit();
        manager.close();
    }

}
