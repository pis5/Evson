/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mkass
 */
public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String titre;

    private String text;

    private Date dateDeCreation;

    private Date dateEvenement;

    private Date heure;

    private Integer nombreInvitesMax;

    private Personne organisateur;

    private GenreDEvenement genre;

    private Lieu lieu;

    private List<EvenementInvitation> evenementInvitationList;

    public Evenement() {
    }

    public Evenement(Integer id) {
        this.id = id;
    }

    public Evenement(Integer id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public Integer getNombreInvitesMax() {
        return nombreInvitesMax;
    }

    public void setNombreInvitesMax(Integer nombreInvitesMax) {
        this.nombreInvitesMax = nombreInvitesMax;
    }

    public Personne getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Personne organisateur) {
        this.organisateur = organisateur;
    }

    public GenreDEvenement getGenre() {
        return genre;
    }

    public void setGenre(GenreDEvenement genre) {
        this.genre = genre;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public List<EvenementInvitation> getEvenementInvitationList() {
        return evenementInvitationList;
    }

    public void setEvenementInvitationList(List<EvenementInvitation> evenementInvitationList) {
        this.evenementInvitationList = evenementInvitationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evenement)) {
            return false;
        }
        Evenement other = (Evenement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Evenement[ id=" + id + " ]";
    }

}
