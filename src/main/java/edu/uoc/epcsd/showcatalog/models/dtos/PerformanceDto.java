package edu.uoc.epcsd.showcatalog.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

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
    @Nullable
    private String identifier;

    public PerformanceDto(Date date, Date time, String streamingUrl, Integer remainingSeats) {
        this.date = date;
        this.time = time;
        this.streamingUrl = streamingUrl;
        this.remainingSeats = remainingSeats;
    }
}
