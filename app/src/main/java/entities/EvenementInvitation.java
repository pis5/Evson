
package entities;

import java.io.Serializable;

/**
 *
 * @author mkass
 */
public class EvenementInvitation implements Serializable {

    private static final long serialVersionUID = 1L;

    protected EvenementInvitationPK evenementInvitationPK;

    private Evenement evenement1;

    private Personne personne;

    private Personne personne1;

    public EvenementInvitation() {
    }

    public EvenementInvitation(EvenementInvitationPK evenementInvitationPK) {
        this.evenementInvitationPK = evenementInvitationPK;
    }

    public EvenementInvitation(int evenement, int emetteur, int recepteur) {
        this.evenementInvitationPK = new EvenementInvitationPK(evenement, emetteur, recepteur);
    }

    public EvenementInvitationPK getEvenementInvitationPK() {
        return evenementInvitationPK;
    }

    public void setEvenementInvitationPK(EvenementInvitationPK evenementInvitationPK) {
        this.evenementInvitationPK = evenementInvitationPK;
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

    public Personne getPersonne1() {
        return personne1;
    }

    public void setPersonne1(Personne personne1) {
        this.personne1 = personne1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evenementInvitationPK != null ? evenementInvitationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvenementInvitation)) {
            return false;
        }
        EvenementInvitation other = (EvenementInvitation) object;
        if ((this.evenementInvitationPK == null && other.evenementInvitationPK != null) || (this.evenementInvitationPK != null && !this.evenementInvitationPK.equals(other.evenementInvitationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EvenementInvitation[ evenementInvitationPK=" + evenementInvitationPK + " ]";
    }
    
}
