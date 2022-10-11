package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * This class is the learnWord entity class.
 * It is used to store the learnWord information in the database.
 * It has the following fields:
 * - id: the id of the learnWord
 * - translation: the first word
 * - word: the second word
 * - learnSetId: the corresponding learnSet where the word is being used
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */
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
}
