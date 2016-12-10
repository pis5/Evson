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

public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ParticipationPK participationPK;

    private Date date;

    private Evenement evenement1;

    private Personne personne;

    public Participation() {
    }

    public Participation(ParticipationPK participationPK) {
        this.participationPK = participationPK;
    }

    public Participation(int evenement, int participant) {
        this.participationPK = new ParticipationPK(evenement, participant);
    }

    public ParticipationPK getParticipationPK() {
        return participationPK;
    }

    public void setParticipationPK(ParticipationPK participationPK) {
        this.participationPK = participationPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Evenement getEvenement1() {
        return evenement1;
    }

    public void setEvenement1(Evenement evenement1) {
        this.evenement1 = evenement1;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participationPK != null ? participationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participation)) {
            return false;
        }
        Participation other = (Participation) object;
        if ((this.participationPK == null && other.participationPK != null) || (this.participationPK != null && !this.participationPK.equals(other.participationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Participation[ participationPK=" + participationPK + " ]";
    }
    
}
