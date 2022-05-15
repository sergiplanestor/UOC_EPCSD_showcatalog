package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.db.valueobj.DateTime;
import edu.uoc.epcsd.showcatalog.models.db.valueobj.Performance;
import edu.uoc.epcsd.showcatalog.models.dtos.PerformanceDto;

import java.util.List;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.map;

public class PerformanceMapper {

    private PerformanceMapper() {
    }

    public static List<Performance> mapToEntity(List<PerformanceDto> dtos) {
        return map(dtos, PerformanceMapper::mapToEntity);
    }

    public static Performance mapToEntity(PerformanceDto dto) {
        return new Performance(
                new DateTime(DateMapper.mapOnlyDate(dto.getDate()), DateMapper.mapOnlyTime(dto.getTime())),
                dto.getStreamingUrl(),
                dto.getRemainingSeats()
        );
    }

    public static List<PerformanceDto> mapToDto(List<Performance> entities) {
        return map(entities, PerformanceMapper::mapToDto);
    }

    public static PerformanceDto mapToDto(Performance entity) {
        return new PerformanceDto(
                DateMapper.mapOnlyDate(entity.getDateTime().getDate()),
                DateMapper.mapOnlyTime(entity.getDateTime().getTime()),
                entity.getStreamingUrl(),
                entity.getRemainingSeats(),
                entity.getId()
        );
    }
}
