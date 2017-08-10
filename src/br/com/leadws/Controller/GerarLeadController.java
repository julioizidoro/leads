/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.Controller;

import br.com.leadws.facade.ClienteFacade;
import br.com.leadws.facade.ContatoFacade;
import br.com.leadws.facade.LeadControleFacade;
import br.com.leadws.facade.LeadFacade;
import br.com.leadws.facade.UnidadeFacade;
import br.com.leadws.facade.UsuarioFacade;
import br.com.leadws.model.Cliente;
import br.com.leadws.model.Leads;
import br.com.leadws.model.Lead;
import br.com.leadws.model.Leadcontrole;
import br.com.leadws.model.Parametroslead;
import br.com.leadws.model.Unidadenegocio;
import br.com.leadws.model.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jizid
 */
public class GerarLeadController {
    
    private Parametroslead parametrosLead;
    private boolean jaecliente;
    private int idContato;
    int numeroLead;

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public GerarLeadController(Parametroslead parametrosLead) {
        this.parametrosLead = parametrosLead;
        
    }
    
    public void gerarListaUnidade(){
        idContato = 0;
        numeroLead = 0;
        UnidadeFacade unidadeFacade = new UnidadeFacade();
        Unidadenegocio unidade = unidadeFacade.getUsuarioResponsavel(1);
        gerarListaLead(unidade);
        unidade = unidadeFacade.getUsuarioResponsavel(1);
        gerarListaLead(unidade);
        Leadcontrole leadControle = new Leadcontrole();
        leadControle.setData(new Date());
        leadControle.setHora(formatarHoraString());
        leadControle.setNumeroleads(numeroLead);
        LeadControleFacade leadControleFacade = new LeadControleFacade();
        leadControleFacade.salvar(leadControle);
    }
    
    public void gerarListaLead(Unidadenegocio unidade) {
        ContatoFacade contatoFacade = new ContatoFacade();
        List<Leads> lista = contatoFacade.list(parametrosLead.getIdcontato(), unidade.getIdunidadeNegocio());
        if (lista != null) {
            if (unidade.isLeadautomatica()) {
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                List<Usuario> listaUsuairo = usuarioFacade.consultar(unidade.getIdunidadeNegocio());
                if (listaUsuairo != null) {
                    int contador = 0;
                    for (int i = 0; i < listaUsuairo.size(); i++) {
                        if (listaUsuairo.get(i).isRecebeleadautomatica()) {
                            contador = i;
                            listaUsuairo.get(i).setRecebeleadautomatica(false);
                            usuarioFacade.salvar(listaUsuairo.get(i));
                            i = 10000;
                        }
                    }
                    for (int i = 0; i < lista.size(); i++) {
                        salvarLeads(lista.get(i), listaUsuairo.get(contador), unidade);
                        contador++;
                        if (contador >= listaUsuairo.size()) {
                            contador = 0;
                        }
                    }
                    listaUsuairo.get(contador).setRecebeleadautomatica(true);
                    usuarioFacade.salvar(listaUsuairo.get(contador));
                }
            } else {
                for (int i = 0; i < lista.size(); i++) {
                    idContato = lista.get(i).getId();
                    salvarLeads(lista.get(i), null, unidade);
                }
                numeroLead = numeroLead + lista.size();
            }
        }
    }

    public Parametroslead getParametrosLead() {
        return parametrosLead;
    }

    public void setParametrosLead(Parametroslead parametrosLead) {
        this.parametrosLead = parametrosLead;
    }
    
    public void salvarLeads(Leads contato, Usuario usuario, Unidadenegocio unidade){
        jaecliente = true;
        Cliente cliente = salvarCliente(contato);
        Lead lead = new Lead();
        lead.setCliente(cliente.getIdcliente());
        lead.setJaecliente(jaecliente);
        lead.setNotas(contato.getMensagem());
        lead.setProdutos(parametrosLead.getProduto());
        lead.setSituacao(1);
        lead.setTipocontato(1);
        lead.setPais(5);
        lead.setPublicidade(parametrosLead.getPublicidade());
        lead.setUnidadenegocio(unidade.getIdunidadeNegocio());
        lead.setMotivocancelamento1(1);
        lead.setDatarecebimento(new Date());
        lead.setHorarecebimento(formatarHoraString());
        if (usuario!=null){
            lead.setUsuario(usuario.getIdusuario());
        }else lead.setUsuario(unidade.getResponsavelcrm());
        lead.setIdcontrole(contato.getId());
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
            cliente.setDataCadastro(new Date());
            cliente.setFoneCelular(formatTelefone(contato.getTelefone()));
            cliente.setTipoCliente("FollowUp");
            cliente.setPublicidade(parametrosLead.getPublicidade());
            if (contato.getUnidade()==0){
                cliente.setUnidadenegocio(6);
            }else cliente.setUnidadenegocio(contato.getUnidade());
            
            cliente = clienteFacade.salvar(cliente);
        }
        return cliente;
    } 
    
    public String formatTelefone(String fone){
        String novoFone ="";
        int tamanho = fone.length();
        for(int i=0;i<tamanho;i++){
            if (fone.charAt(i)!=' '){
                if (fone.charAt(i)=='-'){
                    i++;
                    novoFone = novoFone + fone.charAt(i) + "-";
                }else {
                    novoFone = novoFone + fone.charAt(i);
                }
            }
        }
        return novoFone;
    }
    
    public String formatarHoraString() {
	DateFormat formato = new SimpleDateFormat("HH:mm:ss");
	String formattedDate = formato.format(new Date());
	return formattedDate;
    }
    
}
