/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.dao;

import br.com.leadws.Singleton.ConexaoSingleton;
import br.com.leadws.model.Avisos;
import br.com.leadws.model.Avisousuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author wolverine
 */
public class AvisosDao {
    
    public Avisos salvar(Avisos aviso) {
        EntityManager manager;
        manager = ConexaoSingleton.getInstanceSysTM();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        aviso = manager.merge(aviso);
        tx.commit();
        return aviso;
    }
    
    public Avisousuario salvar(Avisousuario avisoUsuario) {
        EntityManager manager;
        manager = ConexaoSingleton.getInstanceSysTM();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        avisoUsuario = manager.merge(avisoUsuario);
        tx.commit();
        return avisoUsuario;
    }
    
}
