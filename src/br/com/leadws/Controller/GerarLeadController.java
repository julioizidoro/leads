/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.Controller;

import br.com.leadws.facade.ClienteFacade;
import br.com.leadws.facade.ContatoFacade;
import br.com.leadws.facade.LeadFacade;
import br.com.leadws.facade.UnidadeFacade;
import br.com.leadws.model.Cliente;
import br.com.leadws.model.Leads;
import br.com.leadws.model.Lead;
import br.com.leadws.model.Parametroslead;
import java.util.List;

/**
 *
 * @author jizid
 */
public class GerarLeadController {
    
    private Parametroslead parametrosLead;
    private boolean jaecliente;
    private int idContato;

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public GerarLeadController(Parametroslead parametrosLead) {
        this.parametrosLead = parametrosLead;
        
    }
    
    public void gerarListaLead(){
        ContatoFacade contatoFacade = new ContatoFacade();
        List<Leads> lista = contatoFacade.list(parametrosLead.getIdcontato());
        if (lista!=null){
            idContato=0;
            for (int i=0;i<lista.size();i++){
                idContato = lista.get(i).getId();
                salvarLeads(lista.get(i));
            }
        }
    }

    public Parametroslead getParametrosLead() {
        return parametrosLead;
    }

    public void setParametrosLead(Parametroslead parametrosLead) {
        this.parametrosLead = parametrosLead;
    }
    
    public void salvarLeads(Leads contato){
        jaecliente = true;
        Cliente cliente = salvarCliente(contato);
        Lead lead = new Lead();
        lead.setCliente(cliente.getIdcliente());
        lead.setJaecliente(jaecliente);
        lead.setNotas(contato.getMensagem());
        lead.setProdutos(parametrosLead.getProduto());
        lead.setSituacao(1);
        lead.setTipocontato(1);
        lead.setTipoorigem(parametrosLead.getTipoorigem());
        lead.setUnidadenegocio(contato.getUnidade());
        lead.setMotivocancelamento1(1);
        UnidadeFacade unidadeFacade = new UnidadeFacade();
        int responsavel = responsavel = unidadeFacade.getUsuarioResponsavel(lead.getUnidadenegocio());
        if (contato.getUnidade()==0){
            responsavel =6;
        }
        lead.setUsuario(responsavel);
        LeadFacade leadFacede = new LeadFacade();
        leadFacede.salvar(lead);
    }
    
    public Cliente salvarCliente(Leads contato){
        ClienteFacade clienteFacade = new ClienteFacade();
        Cliente cliente = clienteFacade.consultarEmail(contato.getEmail());
        if (cliente==null){
            jaecliente=false;
            cliente = new Cliente();
            cliente.setNome(contato.getNome());
            cliente.setEmail(contato.getEmail());
            cliente.setFoneCelular(contato.getTelefone());
            cliente.setTipoCliente("FollowUp");
            cliente.setPublicidade(parametrosLead.getPublicidade());
            if (contato.getUnidade()==0){
                cliente.setUnidadenegocio(6);
            }else cliente.setUnidadenegocio(contato.getUnidade());
            
            cliente = clienteFacade.salvar(cliente);
        }
        return cliente;
    } 
    
}
