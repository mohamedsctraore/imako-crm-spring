package ci.imako.imakocrmtest.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(nullable = false, length = 150, unique = false)
    private String email;

    @Column(nullable = false, length = 25, unique = false)
    private String telephone;

    @Enumerated(value = EnumType.STRING)
    private Categorie categorie;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Commande> commandes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<RendezVous> rendezVousList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void setRendezVousList(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
    }

    public void addRendezVous(RendezVous rendezVous) {
        if (rendezVous != null) {
            this.getRendezVousList().add(rendezVous);
        }
    }

    public void removeRendezVous(RendezVous rendezVous) {
        if (rendezVous != null) {
            this.getRendezVousList().remove(rendezVous);
        }
    }

    public void addCommande(Commande commande) {
        if (commande != null) {
            this.getCommandes().add(commande);
        }
    }

    public void removeCommande(Commande commande) {
        if (commande != null) {
            this.getCommandes().remove(commande);
        }
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", categorie=" + categorie +
                ", commandes=" + commandes +
                ", rendezVousList=" + rendezVousList +
                '}';
    }

    public enum Categorie {
        SUSPECT, PROSPECT, CLIENT, VIP
    }
}

