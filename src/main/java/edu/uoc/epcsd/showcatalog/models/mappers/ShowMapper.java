package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.dtos.ShowDto;
import edu.uoc.epcsd.showcatalog.models.entities.Show;
import edu.uoc.epcsd.showcatalog.models.values.Duration;
import edu.uoc.epcsd.showcatalog.models.values.Status;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;

import java.util.List;

public class ShowMapper {

    private ShowMapper() {
    }

    public static List<Show> mapToEntity(List<ShowDto> dtos) {
        return ListUtils.map(dtos, ShowMapper::mapToEntity);
    }

    public static Show mapToEntity(ShowDto dto) {
        return new Show(
                dto.getName(),
                dto.getDescription(),
                dto.getImage(),
                dto.getPrice(),
                dto.getDuration().millis(),
                dto.getDuration().getUnit().name(),
                dto.getCapacity(),
                DateMapper.mapDateTime(dto.getOnSaleDate()),
                dto.getStatus().getValue(),
                ListUtils.map(dto.getCategories(), CategoryMapper::mapToEntity),
                ListUtils.map(dto.getPerformances(), PerformanceMapper::mapToEntity)
        );
    }

    public static List<ShowDto> mapToDto(List<Show> entities) {
        return ListUtils.map(entities, ShowMapper::mapToDto);
    }

    public static ShowDto mapToDto(Show entity) {
        return new ShowDto(
                entity.getName(),
                entity.getDescription(),
                entity.getImage(),
                entity.getPrice(),
                new Duration(entity.getDuration(), entity.getDurationUnit()),
                entity.getCapacity(),
                DateMapper.mapDateTime(entity.getOnSaleDate()),
                Status.from(entity.getStatus()),
                ListUtils.map(entity.getCategories(), CategoryMapper::mapToDto),
                ListUtils.map(entity.getPerformances(), PerformanceMapper::mapToDto)
        );
    }
}
