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

public class InvitationPK implements Serializable {

    private int personne;

    private String email;

    public InvitationPK() {
    }

    public InvitationPK(int personne, String email) {
        this.personne = personne;
        this.email = email;
    }

    public int getPersonne() {
        return personne;
    }

    public void setPersonne(int personne) {
        this.personne = personne;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) personne;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvitationPK)) {
            return false;
        }
        InvitationPK other = (InvitationPK) object;
        if (this.personne != other.personne) {
            return false;
        }
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.InvitationPK[ personne=" + personne + ", email=" + email + " ]";
    }
    
}
