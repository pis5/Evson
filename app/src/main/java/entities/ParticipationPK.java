/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;


/**
 *
 * @author ilias
 */

public class ParticipationPK implements Serializable {


    private int evenement;

    private int participant;

    public ParticipationPK() {
    }

    public ParticipationPK(int evenement, int participant) {
        this.evenement = evenement;
        this.participant = participant;
    }

    public int getEvenement() {
        return evenement;
    }

    public void setEvenement(int evenement) {
        this.evenement = evenement;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) evenement;
        hash += (int) participant;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipationPK)) {
            return false;
        }
        ParticipationPK other = (ParticipationPK) object;
        if (this.evenement != other.evenement) {
            return false;
        }
        if (this.participant != other.participant) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ParticipationPK[ evenement=" + evenement + ", participant=" + participant + " ]";
    }
    
}
