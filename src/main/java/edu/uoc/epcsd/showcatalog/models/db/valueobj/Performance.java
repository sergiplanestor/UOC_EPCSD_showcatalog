package edu.uoc.epcsd.showcatalog.models.db.valueobj;


import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import lombok.*;
import org.hibernate.annotations.Parent;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {

    @Parent
    private Show show;

    @AttributeOverrides({
            @AttributeOverride(name = "date", column = @Column(name = "date")),
            @AttributeOverride(name = "time", column = @Column(name = "time"))})
    @Embedded
    private DateTime dateTime;

    @Column(name = "streaming_url")
    private String streamingUrl;

    @Column(name = "remaining_seats")
    private Integer remainingSeats;

    public Performance(DateTime dateTime, String streamingUrl, Integer remainingSeats) {
        this.dateTime = dateTime;
        this.streamingUrl = streamingUrl;
        this.remainingSeats = remainingSeats;
    }

    public String getId() {
        if (show == null || show.getId() == null) {
            return null;
        } else {
            return show.getId().toString() + "-" + dateTime.getDate() + "-" + dateTime.getTime();
        }
    }
}
