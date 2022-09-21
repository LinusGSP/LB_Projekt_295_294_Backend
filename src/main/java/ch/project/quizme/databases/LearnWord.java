package ch.project.quizme.databases;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "learn_word")
public class LearnWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "translation", nullable = false, length = 64)
    private String translation;

    @Column(name = "word", nullable = false, length = 64)
    private String word;

    @JsonIgnore
    @ManyToOne(targetEntity = LearnSet.class)
    @JoinColumn(name = "learnSet_id")
    private LearnSet learnSet;

    @Column(name = "learnSet_id", insertable = false, updatable = false, nullable = false)
    private Integer learnSetId;

    @Column(name = "marked")
    private Boolean marked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public LearnSet getLearnSet() {
        return learnSet;
    }

    public void setLearnSet(LearnSet learnSet) {
        this.learnSet = learnSet;
    }

    public Integer getLearnSetId() {
        return learnSetId;
    }

    public void setLearnSetId(Integer learnSetId) {
        this.learnSetId = learnSetId;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }
}
