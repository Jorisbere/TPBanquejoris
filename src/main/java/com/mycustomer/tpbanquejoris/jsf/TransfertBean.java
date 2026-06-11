/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycustomer.tpbanquejoris.jsf;

import com.mycustomer.tpbanquejoris.service.GestionnaireCompte;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

/**
 *
 * @author HP
 */
@Named(value = "transfertBean")
@ViewScoped
public class TransfertBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    private Long sourceId;
    private Long destinationId;
    private double montant;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String transferer() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            gestionnaireCompte.tranferer(sourceId, destinationId, montant);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Transfert réussi", String.format("%.2f FCFA transférés du compte %d vers le compte %d", montant, sourceId, destinationId)));

            return "transfert?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de transfert", e.getMessage()));
            return null;
        }
    }

    /**
     * Creates a new instance of TransfertBean
     */
    public TransfertBean() {
    }

}
