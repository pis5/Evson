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

public class Amis implements Serializable {

    private static final long serialVersionUID = 1L;

    protected AmisPK amisPK;

    private Date date;

    private Personne personne1;

    private Personne personne2;

    public Amis() {
    }

    public Amis(AmisPK amisPK) {
        this.amisPK = amisPK;
    }

    public Amis(int personne, int ami) {
        this.amisPK = new AmisPK(personne, ami);
    }

    public AmisPK getAmisPK() {
        return amisPK;
    }

    public void setAmisPK(AmisPK amisPK) {
        this.amisPK = amisPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Personne getPersonne1() {
        return personne1;
    }

    public void setPersonne1(Personne personne1) {
        this.personne1 = personne1;
    }

    public Personne getPersonne2() {
        return personne2;
    }

    public void setPersonne2(Personne personne2) {
        this.personne2 = personne2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amisPK != null ? amisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amis)) {
            return false;
        }
        Amis other = (Amis) object;
        if ((this.amisPK == null && other.amisPK != null) || (this.amisPK != null && !this.amisPK.equals(other.amisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Amis[ amisPK=" + amisPK + " ]";
    }

}
