package ch.project.quizme.databases;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "Word1 cant be empty")
    @Size(min = 1, max = 256, message = "Word1 must be between 1 and 255 characters long")
    @Column(name = "word1", nullable = false, length = 256)
    private String word1;

    @NotEmpty(message = "Word2 cant be empty")
    @Size(min = 1, max = 256, message = "Word2 must be between 1 and 255 characters long")
    @Column(name = "word2", nullable = false, length = 256)
    private String word2;

    @NotNull(message = "learnSet cant be null")
    @ManyToOne(targetEntity = LearnSet.class)
    @JoinColumn(name = "learn_set_id", nullable = false)
    private Integer learnSetId;

    @Column(name = "marked", nullable = true)
    private Boolean marked = false;

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public Integer getLearnSetId() {
        return learnSetId;
    }

    public void setLearnSetId(Integer learnSetId) {
        this.learnSetId = learnSetId;
    }
}
