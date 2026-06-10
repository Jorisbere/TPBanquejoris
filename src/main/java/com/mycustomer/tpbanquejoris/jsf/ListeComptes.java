/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycustomer.tpbanquejoris.jsf;

import com.mycustomer.tpbanquejoris.entity.CompteBancaire;
import com.mycustomer.tpbanquejoris.service.GestionnaireCompte;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 *Backing bean pour la page JSF customerList
 * @author HP
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {
    
    
    @Inject
    private GestionnaireCompte gestionnaireCompte;
    private List<CompteBancaire> listCompteBancaires;

    /**
     * Creates a new instance of CustomerBean
     */
    public ListeComptes() {
    }
    
    /**
     * Retourne la liste des clients pour affichage dans une DataTable.
     */
     public List<CompteBancaire> getListeCpte() {
        if (listCompteBancaires == null) {
            listCompteBancaires = gestionnaireCompte.getAllComptes();
        }
        return listCompteBancaires;
    }
}
