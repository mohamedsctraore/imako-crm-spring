package ci.imako.imakocrmtest.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rendezvous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-")
    @Column(nullable = false)
    private Date dateRendezVous;

    @Lob
    @Column(nullable = false)
    private String noteRendezVous;

    @ManyToOne
    private Contact contact;

    public RendezVous() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRendezVous() {
        return dateRendezVous;
    }

    public void setDateRendezVous(Date dateRendezVous) {
        this.dateRendezVous = dateRendezVous;
    }

    public String getNoteRendezVous() {
        return noteRendezVous;
    }

    public void setNoteRendezVous(String noteRendezVous) {
        this.noteRendezVous = noteRendezVous;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", dateRendezVous=" + dateRendezVous +
                ", noteRendezVous='" + noteRendezVous + '\'' +
                ", contact=" + contact +
                '}';
    }
}
