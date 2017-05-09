/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.dao;

import br.com.leadws.Singleton.ConexaoSingleton;
import br.com.leadws.model.Lead;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author jizid
 */
public class LeadDao {
    
    public Lead salvar(Lead lead) {
        EntityManager manager;
        manager = ConexaoSingleton.getInstanceSysTM();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        lead = manager.merge(lead);
        tx.commit();
        return lead;
    }
}
