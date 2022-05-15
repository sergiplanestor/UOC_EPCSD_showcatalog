package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import edu.uoc.epcsd.showcatalog.models.dtos.ShowDetailedDto;
import edu.uoc.epcsd.showcatalog.models.dtos.ShowOverviewDto;
import edu.uoc.epcsd.showcatalog.models.values.Duration;
import edu.uoc.epcsd.showcatalog.models.values.Status;

import java.util.List;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.map;

public class ShowMapper {

    private ShowMapper() {
    }

    public static List<Show> mapOverviewToEntity(List<ShowOverviewDto> dtos) {
        return map(dtos, ShowMapper::mapOverviewToEntity);
    }

    public static Show mapOverviewToEntity(ShowOverviewDto dto) {
        return new Show(
                dto.getName(),
                dto.getDescription(),
                dto.getImage(),
                dto.getPrice(),
                dto.getDuration().millis(),
                dto.getDuration().getUnit().name(),
                dto.getCapacity(),
                DateMapper.mapDateTime(dto.getOnSaleDate()),
                dto.getStatus().getValue()
        );
    }

    public static List<ShowOverviewDto> mapToOverviewDto(List<Show> entities) {
        return map(entities, ShowMapper::mapToOverviewDto);
    }

    public static ShowOverviewDto mapToOverviewDto(Show entity) {
        return new ShowOverviewDto(
                entity.getName(),
                entity.getDescription(),
                entity.getImage(),
                entity.getPrice(),
                new Duration(entity.getDuration(), entity.getDurationUnit()),
                entity.getCapacity(),
                DateMapper.mapDateTime(entity.getOnSaleDate()),
                Status.from(entity.getStatus())
        );
    }

    public static List<Show> mapDetailedToEntity(List<ShowDetailedDto> dtos) {
        return map(dtos, ShowMapper::mapDetailedToEntity);
    }

    public static Show mapDetailedToEntity(ShowDetailedDto dto) {
        return new Show(
                dto.getShowOverview().getName(),
                dto.getShowOverview().getDescription(),
                dto.getShowOverview().getImage(),
                dto.getShowOverview().getPrice(),
                dto.getShowOverview().getDuration().millis(),
                dto.getShowOverview().getDuration().getUnit().name(),
                dto.getShowOverview().getCapacity(),
                DateMapper.mapDateTime(dto.getShowOverview().getOnSaleDate()),
                dto.getShowOverview().getStatus().getValue(),
                map(dto.getCategories(), CategoryMapper::mapToEntity),
                map(dto.getPerformances(), PerformanceMapper::mapToEntity)
        );
    }

    public static List<ShowDetailedDto> mapToDetailedDto(List<Show> entities) {
        return map(entities, ShowMapper::mapToDetailedDto);
    }

    public static ShowDetailedDto mapToDetailedDto(Show entity) {
        return new ShowDetailedDto(
                mapToOverviewDto(entity),
                map(entity.getCategories(), CategoryMapper::mapToDto),
                map(entity.getPerformances(), PerformanceMapper::mapToDto)
        );
    }
}
