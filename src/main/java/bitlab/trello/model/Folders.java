package bitlab.trello.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "folders")
@Getter
@Setter
public class Folders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany
    private List<TaskCategories> categories;

    @PreRemove
    private void preRemove() {
        if (categories != null) {
            categories.clear();
        }
    }
}
