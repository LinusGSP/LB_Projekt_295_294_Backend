package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "Name may not be empty")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @NotEmpty(message = "Flag may not be empty")
    @Column(name = "flag", nullable = false, length = 2)
    private String flag;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
