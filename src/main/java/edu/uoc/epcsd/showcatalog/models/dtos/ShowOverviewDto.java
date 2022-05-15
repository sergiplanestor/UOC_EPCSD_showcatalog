package edu.uoc.epcsd.showcatalog.models.dtos;


import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import edu.uoc.epcsd.showcatalog.models.values.Duration;
import edu.uoc.epcsd.showcatalog.models.values.Status;
import lombok.*;

import java.util.Date;

/***
 *  Data transfer object for the entity {@link Show}.
 **/
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowOverviewDto {
    private String name;
    private String description;
    private String image;
    private Float price;
    private Duration duration;
    private Integer capacity;
    private Date onSaleDate;
    private Status status;
}
