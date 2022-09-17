package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "learn_set")
public class LearnSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "learn_set name can't be empty")
    @Size(min = 1, max = 50, message = "learn_set name must be between 1 and 50 characters")
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @NotNull(message = "first language can't be empty")
    @ManyToOne(optional = false)
    @JoinColumn(name = "first_language_id", nullable = false)
    private Language firstLanguage;

    @NotNull(message = "second language can't be empty")
    @ManyToOne(optional = false)
    @JoinColumn(name = "second_language_id", nullable = false)
    private Language secondLanguage;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_edited")
    private Date lastEdited;

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

    public Language getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(Language firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public Language getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(Language secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        this.creationDate = new Date();;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited() {
        this.lastEdited = new Date();;
    }
}
