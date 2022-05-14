package edu.uoc.epcsd.showcatalog.models.dtos;


import edu.uoc.epcsd.showcatalog.models.entities.Performance;
import edu.uoc.epcsd.showcatalog.models.entities.Show;
import edu.uoc.epcsd.showcatalog.models.values.Duration;
import edu.uoc.epcsd.showcatalog.models.values.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/***
 *  Data transfer object for the entity {@link Show}.
 **/
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDto {
    private String name;
    private String description;
    private String image;
    private Float price;
    private Duration duration;
    private Integer capacity;
    private Date onSaleDate;
    private Status status;
    private List<CategoryDto> categories;
    private List<PerformanceDto> performances;
}
