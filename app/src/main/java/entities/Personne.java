
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private String habite;

    private String lieuDeNaissance;

    private Date dateDeNaissance;

    private Date dateDEnregistrement;

    private String motDePasse;

    private byte[] photo;

    private List<Personne> personneList;

    private List<Personne> personneList1;

    private List<Personne> personneList2;

    private List<Personne> personneList3;

    private List<Invitation> invitationList;

    private List<Evenement> evenementList;

    private List<EvenementInvitation> evenementInvitationList;

    private List<EvenementInvitation> evenementInvitationList1;

    public Personne() {
    }

    public Personne(Integer id) {
        this.id = id;
    }

    public Personne(Integer id, String nom, String prenom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHabite() {
        return habite;
    }

    public void setHabite(String habite) {
        this.habite = habite;
    }

    public String getLieuDeNaissance() {
        return lieuDeNaissance;
    }

    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Date getDateDEnregistrement() {
        return dateDEnregistrement;
    }

    public void setDateDEnregistrement(Date dateDEnregistrement) {
        this.dateDEnregistrement = dateDEnregistrement;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    public List<Personne> getPersonneList() {
        return personneList;
    }

    public void setPersonneList(List<Personne> personneList) {
        this.personneList = personneList;
    }


    public List<Personne> getPersonneList1() {
        return personneList1;
    }

    public void setPersonneList1(List<Personne> personneList1) {
        this.personneList1 = personneList1;
    }

    public List<Personne> getPersonneList2() {
        return personneList2;
    }

    public void setPersonneList2(List<Personne> personneList2) {
        this.personneList2 = personneList2;
    }


    public List<Personne> getPersonneList3() {
        return personneList3;
    }

    public void setPersonneList3(List<Personne> personneList3) {
        this.personneList3 = personneList3;
    }


    public List<Invitation> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<Invitation> invitationList) {
        this.invitationList = invitationList;
    }


    public List<Evenement> getEvenementList() {
        return evenementList;
    }

    public void setEvenementList(List<Evenement> evenementList) {
        this.evenementList = evenementList;
    }


    public List<EvenementInvitation> getEvenementInvitationList() {
        return evenementInvitationList;
    }

    public void setEvenementInvitationList(List<EvenementInvitation> evenementInvitationList) {
        this.evenementInvitationList = evenementInvitationList;
    }


    public List<EvenementInvitation> getEvenementInvitationList1() {
        return evenementInvitationList1;
    }

    public void setEvenementInvitationList1(List<EvenementInvitation> evenementInvitationList1) {
        this.evenementInvitationList1 = evenementInvitationList1;
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
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Personne[ id=" + id + " ]";
    }

}
