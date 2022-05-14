package edu.uoc.epcsd.showcatalog.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "performance")
@ToString(exclude = "show")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "streaming_url")
    private String streamingUrl;
    @Column(name = "remaining_seats")
    private Integer remainingSeats;
    @Column(name = "status")
    private Integer status;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "show_id", nullable = false)
    private Show show;

    public Performance(String date, String time, String streamingUrl, Integer remainingSeats, Integer status) {
        this.date = date;
        this.time = time;
        this.streamingUrl = streamingUrl;
        this.remainingSeats = remainingSeats;
        this.status = status;
    }
}
