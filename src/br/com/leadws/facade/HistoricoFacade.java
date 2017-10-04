/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.HistoricoDao;
import br.com.leadws.model.Leadhistorico;

/**
 *
 * @author wolverine
 */
public class HistoricoFacade {
    
    public Leadhistorico salvar(Leadhistorico historico) {
        HistoricoDao historicoDao = new HistoricoDao();
        return historicoDao.salvar(historico);
    }
    
}
