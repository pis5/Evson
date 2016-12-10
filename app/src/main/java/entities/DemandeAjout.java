/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author ilias
 */

public class DemandeAjout implements Serializable {

    private static final long serialVersionUID = 1L;
    protected DemandeAjoutPK demandeAjoutPK;
    private Date date;
    private Personne personne;
    private Personne personne1;

    public DemandeAjout() {
    }

    public DemandeAjout(DemandeAjoutPK demandeAjoutPK) {
        this.demandeAjoutPK = demandeAjoutPK;
    }

    public DemandeAjout(int emetteur, int recepteur) {
        this.demandeAjoutPK = new DemandeAjoutPK(emetteur, recepteur);
    }

    public DemandeAjoutPK getDemandeAjoutPK() {
        return demandeAjoutPK;
    }

    public void setDemandeAjoutPK(DemandeAjoutPK demandeAjoutPK) {
        this.demandeAjoutPK = demandeAjoutPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne1() {
        return personne1;
    }

    public void setPersonne1(Personne personne1) {
        this.personne1 = personne1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (demandeAjoutPK != null ? demandeAjoutPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DemandeAjout)) {
            return false;
        }
        DemandeAjout other = (DemandeAjout) object;
        if ((this.demandeAjoutPK == null && other.demandeAjoutPK != null) || (this.demandeAjoutPK != null && !this.demandeAjoutPK.equals(other.demandeAjoutPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DemandeAjout[ demandeAjoutPK=" + demandeAjoutPK + " ]";
    }
    
}
