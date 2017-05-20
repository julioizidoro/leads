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

//    public List<Contato> list(int idContato) throws SQLException {
//        Connection conn = ConexaoSingleton.getInstanceLead();
//        PreparedStatement stmt = conn.prepareStatement("select c from Contato c where c.idcontato>" + idContato);
//        ResultSet rs = stmt.executeQuery();
//        List<Contato> lista = new ArrayList<Contato>();
//        while (rs.next()) {
//            Contato c = new Contato();
//            c.setIdcontato(rs.getInt("idcontato"));
//            c.setEmail(rs.getString("email"));
//            c.setMensagem(rs.getString("mensagem"));
//            c.setNome(rs.getString("nome"));
//            c.setTelefone(rs.getString("telefone"));
//            c.setUnidade(rs.getInt("unidade"));
//            lista.add(c);
//        }
//        stmt.close();
//        if (lista.size()>0){
//            return lista;
//        }
//        return null;
//    }   
    public List<Leads> list(int idContato) throws SQLException {
        EntityManager manager = ConexaoSingleton.getInstanceLead();
        Query q = manager.createQuery("select c from Leads c where c.id>" + idContato + " and c.unidade=2");
        if (q.getResultList().size() > 0) {
            return q.getResultList();
        }
        return null;
    }

}
