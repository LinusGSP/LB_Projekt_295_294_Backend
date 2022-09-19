package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "language name can't be empty")
    @Size(min = 1, max = 50, message = "language name must be between 1 and 50 characters")
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Size(min = 2, max = 2, message = "language flag must be of size 2")
    @NotEmpty(message = "language flag can't be empty")
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
