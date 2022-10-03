package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "learn_set")
public class LearnSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Name cant be blank")
    @Size(message = "Name must be at least of length 1 and max of length 64")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @ManyToOne(targetEntity = Language.class, optional = false)
    @JoinColumn(name = "language1", nullable = false)
    private Language language1;

    @ManyToOne(targetEntity = Language.class, optional = false)
    @JoinColumn(name = "language2", nullable = false)
    private Language language2;

    @Column(name = "creation_date")
    private Date creationDate = new Date();

    @Column(name = "last_edited")
    private Date lastEdited = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage1() {
        return language1;
    }

    public void setLanguage1(Language language1) {
        this.language1 = language1;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage2(Language language2) {
        this.language2 = language2;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited() {
        this.lastEdited = new Date();
    }
}
