package ch.project.quizme.databases;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Name cant be blank")
    @Size(min=1, max = 64, message = "Name must be at least of length 1 and max of length 64")
    @Column(name = "Name", nullable = false, length = 64)
    private String name;

    @NotBlank(message = "Flag cant be blank")
    @Size(min = 2, max = 2, message = "Flag must be of size 2")
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
