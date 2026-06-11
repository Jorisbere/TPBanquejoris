/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycustomer.tpbanquejoris.service;

import com.mycustomer.tpbanquejoris.entity.CompteBancaire;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author HP
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "bere", // nom et
        password = "root", // mot de passe que vous avez donnés lors de la création de la base de données
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true",
            "driverClass=com.mysql.cj.jdbc.Driver"
        }
)
@ApplicationScoped
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    @Transactional
    public void creerCompte(CompteBancaire compteBancaire) {
        em.persist(compteBancaire);
    }

    public List<CompteBancaire> getAllComptes() {
        Query query = em.createQuery("SELECT c FROM CompteBancaire c");
        return query.getResultList();
    }

    public CompteBancaire findCompteBancaireById(Long id) {
        return em.find(CompteBancaire.class, id);
    }

    @Transactional
    public void tranferer(Long sourceId, Long destinationId, double montant)
            throws IllegalArgumentException {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        CompteBancaire source = findCompteBancaireById(sourceId);
        CompteBancaire destination = findCompteBancaireById(destinationId);

        if (source == null) {
            throw new IllegalArgumentException("Compte source introuvable(ID: " + sourceId + ")");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Compte destination introuvable(ID: " + destinationId + ")");
        }
        if (source.getSolde() < montant) {
            throw new IllegalArgumentException("Solde insuffisant sur le compte source");
        }

        source.setSolde((int) (source.getSolde() - montant));
        destination.setSolde((int) (destination.getSolde() + montant));

        em.merge(source);
        em.merge(destination);
    }

    public long compterComptes() {
        return em.createQuery("SELECT COUNT(c) From CompteBancaire c", Long.class)
                .getSingleResult();
    }

}
