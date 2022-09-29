package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "learn_word")
public class LearnWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Translation cant be blank")
    @Size(message = "Translation must be at least of length 1 and max of length 64")
    @Column(name = "translation", nullable = false, length = 64)
    private String translation;

    @NotBlank(message = "Word cant be blank")
    @Size(message = "Word must be at least of length 1 and max of length 64")
    @Column(name = "word", nullable = false, length = 64)
    private String word;

    @JoinColumn(name = "learn_set_id")
    private Integer learnSetId;

    @Column(columnDefinition = "BOOLEAN default false")
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
