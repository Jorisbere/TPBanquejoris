/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycustomer.tpbanquejoris.config;

import com.mycustomer.tpbanquejoris.entity.CompteBancaire;
import com.mycustomer.tpbanquejoris.service.GestionnaireCompte;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
@ApplicationScoped
public class Init {

    private static final Logger LOG = Logger.getLogger(Init.class.getName());

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
        LOG.info("Vérification des comptes au démarrage...");
        if (gestionnaireCompte.compterComptes() == 0) {
            LOG.info("Table des comptes vide. Création des  comptes par défaut.");
            creerComptesParDefaut();
        } else {
            LOG.info("Des comptes existent déja. Aucune création.");
        }
    }

    @Transactional
    protected void creerComptesParDefaut() {

        gestionnaireCompte.creerCompte(new CompteBancaire("John Lennon", 15000));
        gestionnaireCompte.creerCompte(new CompteBancaire("Paul McCartney", 95000));
        gestionnaireCompte.creerCompte(new CompteBancaire("Ringo Starr", 2000));
        gestionnaireCompte.creerCompte(new CompteBancaire("Georges Harrison", 10000));

        LOG.info("4 comptes créés avec succès.");
    }

}
