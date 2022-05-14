package edu.uoc.epcsd.showcatalog.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "show")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "start_sale_date"})})
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image;

    @Column(name = "price")
    private Float price;

    @Column(name = "duration_value")
    private Long duration;

    @Column(name = "duration_unit")
    private String durationUnit;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "start_sale_date")
    private String onSaleDate;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "show_categories",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"show_id", "category_id"})}
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "show")
    private List<Performance> performances;

    public Show(String name, String description, String image, Float price, Long duration, String durationUnit,
                Integer capacity, String onSaleDate, Integer status, List<Category> categories,
                List<Performance> performances) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.capacity = capacity;
        this.onSaleDate = onSaleDate;
        this.status = status;
        this.categories = categories;
        this.performances = performances;
    }
}
