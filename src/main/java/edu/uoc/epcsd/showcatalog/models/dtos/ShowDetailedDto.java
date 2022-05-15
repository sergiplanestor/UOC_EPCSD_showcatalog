package edu.uoc.epcsd.showcatalog.models.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetailedDto {
    private ShowOverviewDto showOverview;
    private List<CategoryDto> categories;
    private List<PerformanceDto> performances;
}
