package edu.uoc.epcsd.showcatalog.models.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "category")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@ToString(exclude = "shows")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Show> shows;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
