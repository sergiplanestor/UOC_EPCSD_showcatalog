package edu.uoc.epcsd.showcatalog.models.db.valueobj;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateTime {
    private String date;
    private String time;
}
