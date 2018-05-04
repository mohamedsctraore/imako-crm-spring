package ci.imako.imakocrmtest.domain;

import javax.persistence.*;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String noteCommande;

    @ManyToOne
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteCommande() {
        return noteCommande;
    }

    public void setNoteCommande(String noteCommande) {
        this.noteCommande = noteCommande;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", noteCommande='" + noteCommande + '\'' +
                ", contact=" + contact +
                '}';
    }
}
