/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;


/**
 *
 * @author mkass
 */

public class EvenementInvitationPK implements Serializable {


    private int evenement;

    private int emetteur;

    private int recepteur;

    public EvenementInvitationPK() {
    }

    public EvenementInvitationPK(int evenement, int emetteur, int recepteur) {
        this.evenement = evenement;
        this.emetteur = emetteur;
        this.recepteur = recepteur;
    }

    public int getEvenement() {
        return evenement;
    }

    public void setEvenement(int evenement) {
        this.evenement = evenement;
    }

    public int getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(int emetteur) {
        this.emetteur = emetteur;
    }

    public int getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(int recepteur) {
        this.recepteur = recepteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) evenement;
        hash += (int) emetteur;
        hash += (int) recepteur;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvenementInvitationPK)) {
            return false;
        }
        EvenementInvitationPK other = (EvenementInvitationPK) object;
        if (this.evenement != other.evenement) {
            return false;
        }
        if (this.emetteur != other.emetteur) {
            return false;
        }
        if (this.recepteur != other.recepteur) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EvenementInvitationPK[ evenement=" + evenement + ", emetteur=" + emetteur + ", recepteur=" + recepteur + " ]";
    }
    
}
