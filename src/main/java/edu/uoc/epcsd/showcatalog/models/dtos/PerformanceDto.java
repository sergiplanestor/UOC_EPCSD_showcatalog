package edu.uoc.epcsd.showcatalog.models.dtos;

import edu.uoc.epcsd.showcatalog.models.values.Status;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDto {
    private Date date;
    private Date time;
    private String streamingUrl;
    private Integer remainingSeats;
    private Status status;
}
