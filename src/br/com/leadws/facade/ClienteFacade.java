/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.facade;

import br.com.leadws.dao.ClienteDao;
import br.com.leadws.model.Cliente;

/**
 *
 * @author jizid
 */
public class ClienteFacade {
    
    public Cliente salvar(Cliente cliente) {
        ClienteDao clienteDao = new ClienteDao();
        return clienteDao.salvar(cliente);
    }
    
    public Cliente consultarEmail(String email) {
        ClienteDao clienteDao = new ClienteDao();
        return clienteDao.consultarEmail(email);
    }
    
}
