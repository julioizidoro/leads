/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.leadws.Singleton;

import br.com.leadws.facade.ParametrosLeadFacade;
import br.com.leadws.model.Parametroslead;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kamilla Rodrigues
 */
public class ConexaoSingleton {
    
    
    private static EntityManagerFactory emfSysTM = null;
    private static EntityManager managerSysTM= null;
    private static EntityManagerFactory emfLead = null;
    private static EntityManager managerLead= null;
    private static Connection conexao;
    
    
    public static EntityManager getInstanceSysTM() {
        if (emfSysTM == null) {
            Map mapa = new HashMap();
            mapa.put("hibernate.connection.url", "jdbc:mysql://www.systm.com.br:8081/systm");
            mapa.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            mapa.put("hibernate.connection.password", "simples");
            mapa.put("hibernate.connection.username", "root");
            mapa.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
            mapa.put("hibernate.show_sql", "true");
            mapa.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            emfSysTM = Persistence.createEntityManagerFactory("systmPU", mapa);
            managerSysTM = emfSysTM.createEntityManager();
        }
        if (!managerSysTM.isOpen()) {
            System.out.print("Verifique conexão com banco de dados SysTM");
        }
        return managerSysTM;
    }
    
    public static EntityManager getInstanceLead() {
        managerLead = null;
        ParametrosLeadFacade parametrosLeadFacade = new ParametrosLeadFacade();
        Parametroslead parametrosLead = parametrosLeadFacade.get();
        Map mapa = new HashMap();
        mapa.put("hibernate.connection.url", "jdbc:mysql://" + parametrosLead.getBanco());
        mapa.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        mapa.put("hibernate.connection.password", parametrosLead.getSenha());
        mapa.put("hibernate.connection.username", parametrosLead.getUsuairo());
        mapa.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        mapa.put("hibernate.show_sql", "true");
        mapa.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        emfLead = Persistence.createEntityManagerFactory("leadPU", mapa);
        managerLead = emfLead.createEntityManager();
        if (!managerLead.isOpen()) {
            System.out.print("Verifique conexão com banco de dados LEAD");
        }
        return managerLead;
    }
    
//    public static Connection getInstanceLead() {
//        if (conexao == null) {
//            ParametrosLeadFacade parametrosLeadFacade = new ParametrosLeadFacade();
//            Parametroslead parametrosLead = parametrosLeadFacade.get();
//            try {
//                conexao = DriverManager.getConnection("jdbc:mysql://" + parametrosLead.getBanco() + ", " +  
//                        parametrosLead.getUsuairo()+ ", " + parametrosLead.getSenha());
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return conexao;
//    }

   
}
