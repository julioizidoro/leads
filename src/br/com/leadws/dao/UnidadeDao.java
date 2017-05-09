/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.dao;

import br.com.leadws.Singleton.ConexaoSingleton;
import br.com.leadws.model.Unidadenegocio;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jizid
 */
public class UnidadeDao {
    
    public int getUsuarioResponsavel(int idUnidade){
        EntityManager manager = ConexaoSingleton.getInstanceSysTM();
        Query q = manager.createQuery("select u from Unidadenegocio u where u.idunidadeNegocio=" + idUnidade);
        if (q.getResultList().size()>0){
            Unidadenegocio unidade = (Unidadenegocio) q.getResultList().get(0);
            return unidade.getResponsavelcrm();
        }
        return 0;
    }
    
}