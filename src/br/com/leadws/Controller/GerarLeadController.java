/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.Controller;

import br.com.leadws.facade.AvisosFacade;
import br.com.leadws.facade.ClienteFacade;
import br.com.leadws.facade.ContatoFacade;
import br.com.leadws.facade.HistoricoFacade;
import br.com.leadws.facade.LeadControleFacade;
import br.com.leadws.facade.LeadFacade;
import br.com.leadws.facade.LeadResponsavelFacade;
import br.com.leadws.facade.UnidadeFacade;
import br.com.leadws.facade.UsuarioFacade;
import br.com.leadws.model.Avisos;
import br.com.leadws.model.Avisousuario;
import br.com.leadws.model.Cliente;
import br.com.leadws.model.Leads;
import br.com.leadws.model.Lead;
import br.com.leadws.model.Leadcontrole;
import br.com.leadws.model.Leadhistorico;
import br.com.leadws.model.Leadresponsavel;
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
    private int numeroLead;
    private String emailAnterior;
    List<Leadresponsavel> listaLeadResponsavel;
    
    public GerarLeadController(Parametroslead parametrosLead) {
        this.parametrosLead = parametrosLead;
        
    }
    
    public void gerarListaUnidade(){
        emailAnterior = "";
        numeroLead = 0;
        UnidadeFacade unidadeFacade = new UnidadeFacade();
//        Unidadenegocio unidade = unidadeFacade.getUsuarioResponsavel(1);//Floripa
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(2);//Curitiba
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(6);//Matriz
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(10);//Chapeco
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(23);//Porto Alegre
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(5);//Moema
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(3);//Balneario
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(54);//Juveve
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(9);//Maringa
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(58);//Mogi Guaçu
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(60);//Alphaville
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(4);//Belo Horizonte
//        gerarListaLead(unidade);
//        unidade = unidadeFacade.getUsuarioResponsavel(11);//Belo Horizonte
//        gerarListaLead(unidade);
        List<Unidadenegocio> listaUnidade = unidadeFacade.getUnidaded();
        if (listaUnidade!=null){
            for (int i=0;i<listaUnidade.size();i++){
                int idUnidade = listaUnidade.get(i).getIdunidadeNegocio();
                if (idUnidade==6){
                    idUnidade = 0;
                }
                System.out.println(idUnidade);
                gerarListaLead(listaUnidade.get(i));
            }
        }
        Leadcontrole leadControle = new Leadcontrole();
        leadControle.setData(new Date());
        leadControle.setHora(formatarHoraString());
        leadControle.setNumeroleads(numeroLead);
        LeadControleFacade leadControleFacade = new LeadControleFacade();
        leadControleFacade.salvar(leadControle);
    }
    
    public void gerarListaLead(Unidadenegocio unidade) {
        ContatoFacade contatoFacade = new ContatoFacade();
        int idunidade =0;
        if (unidade.getIdunidadeNegocio()!=6){
            idunidade = unidade.getIdunidadeNegocio();
        }
        List<Leads> lista = contatoFacade.list(parametrosLead.getIdcontato(), idunidade);
        listaLeadResponsavel = null;
        if (lista != null) {
            if (unidade.isLeadautomatica()) {
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                List<Usuario> listaUsuairo = usuarioFacade.consultar(unidade.getIdunidadeNegocio());
                if (listaUsuairo != null) {
                    int contador = 0;
                    int idUsuario = unidade.getUsuarioleadautomatica();
                    for (int i = 0; i < listaUsuairo.size(); i++) {
                        if (listaUsuairo.get(i).getIdusuario()==idUsuario) {
                            contador = i;                            
                            i = 10000;
                        }
                    }
                    boolean salvouLead = false;
                    for (int i = 0; i < lista.size(); i++) {
                        Lead lead = salvarLeads(lista.get(i), listaUsuairo.get(contador), unidade, true);
                        if (lead !=null){
                            listaResponsavelUnidade(unidade.getIdunidadeNegocio(), listaUsuairo.get(contador).getIdusuario());
                            contador++;
                        }
                        if (contador >= (listaUsuairo.size()-1)) {
                            contador = 0;
                        }
                        numeroLead = numeroLead + lista.size();
                    }
                    if (contador >= (listaUsuairo.size()-1)) {
                            contador = 0;
                    }else {
                        contador++;
                    }
                    unidade.setUsuarioleadautomatica(listaUsuairo.get(contador).getIdusuario());
                    UnidadeFacade unidadeFacade = new  UnidadeFacade();
                    unidadeFacade.salvar(unidade);
                  
                }
            } else {
                boolean salvou= false;
                if (listaLeadResponsavel==null){
                     LeadResponsavelFacade leadResponsavelFacade = new LeadResponsavelFacade();
                     if (idunidade==0){
                         idunidade=6;
                     }
                    listaLeadResponsavel = leadResponsavelFacade.list(idunidade);
                }
                for (int i = 0; i < lista.size(); i++) {
                   Lead lead = salvarLeads(lista.get(i), null, unidade, false);
                   if (!salvou){
                       if (lead!=null){
                           salvou = true;
                       }
                   }
                }
                if (salvou){
                    listaResponsavelUnidade(unidade.getIdunidadeNegocio(), 0);
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
    
    public Lead salvarLeads(Leads contato, Usuario usuario, Unidadenegocio unidade, boolean dataEnvio){
        jaecliente = true;
        Cliente cliente = salvarCliente(contato);
        Lead lead = new Lead();
        LeadFacade leadFacede = new LeadFacade();
        boolean lancarHistorico = false;
        if (emailAnterior.equalsIgnoreCase(contato.getEmail())){
            jaecliente = false;
            lead = new Lead();
            lancarHistorico = false;
        }
        if (jaecliente){
            lead = leadFacede.getLead(cliente.getIdcliente());
            lancarHistorico = true;
        }else{
            lead = null;
        }
        if (lead == null) {
            lead = new Lead();
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
            lead.setUrlclient(contato.getUrlclient());
            if (usuario != null) {
                lead.setUsuario(usuario.getIdusuario());
            } else {
                if (listaLeadResponsavel!=null){
                    lead.setUsuario(listaLeadResponsavel.get(0).getUsuario());
                }
            }
            lead.setIdcontrole(contato.getId());
            if (dataEnvio) {
                lead.setDataenvio(new Date());
            }
            lead = leadFacede.salvar(lead);
            ContatoFacade contatoFacade = new ContatoFacade();
            contatoFacade.salvar(contato);
            emailAnterior = contato.getEmail();
            return lead;
        }else {
            if (lancarHistorico){
                lancarHistoricoLead(lead, contato);
                return null;
            }else return null;
        }
    }
    
    public void lancarHistoricoLead(Lead lead, Leads contato){
        Leadhistorico historico = new Leadhistorico();
        historico.setCliente(lead.getCliente());
        historico.setDatahistorico(new Date());
        historico.setHistorico("Nova solictação do cliente via fale coosco para unidade " + contato.getUnidade_desc());
        historico.setTipocontato(1);
        HistoricoFacade historicoFacade = new HistoricoFacade();
        historicoFacade.salvar(historico);
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
    
    public void listaResponsavelUnidade(int unidade, int usuario){
        if (usuario==0){
            if (listaLeadResponsavel!=null){
                Avisos aviso = criarAviso(unidade);
                for(int i=0;i<listaLeadResponsavel.size();i++){
                    criarAvisoUsuario(aviso, listaLeadResponsavel.get(i).getUsuario());
                }
            }
        }else {
            Avisos aviso = criarAviso(unidade);
            criarAvisoUsuario(aviso, usuario);
        }
    }
    
    public Avisos criarAviso(int unidade){
        Avisos aviso  = new Avisos();
        aviso.setData(new Date());
        aviso.setIdunidade(unidade);
        aviso.setImagem("lead");
        aviso.setLiberar(false);
        aviso.setTexto("Novo Lead recebido do Fale Conosco cliente");
        aviso.setUsuario(1);
        aviso.setLiberar(true);
        AvisosFacade avisosFacade = new AvisosFacade();
        aviso = avisosFacade.salvar(aviso);
        return aviso;
    }
    
    public void criarAvisoUsuario(Avisos aviso, int idusuario){
        
        Avisousuario avisoUsuario = new Avisousuario();
        avisoUsuario.setAvisos(aviso.getIdavisos());
        avisoUsuario.setUsuario(idusuario);
        avisoUsuario.setVisto(false);
        AvisosFacade avisosFacade = new AvisosFacade();
        avisosFacade.salvar(avisoUsuario);
    }
    
    
    
}
