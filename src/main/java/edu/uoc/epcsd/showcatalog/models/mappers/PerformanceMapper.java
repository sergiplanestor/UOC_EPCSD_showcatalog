package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.dtos.PerformanceDto;
import edu.uoc.epcsd.showcatalog.models.entities.Performance;
import edu.uoc.epcsd.showcatalog.models.values.Status;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;

import java.util.List;

public class PerformanceMapper {

    private PerformanceMapper() {
    }

    public static List<Performance> mapToEntity(List<PerformanceDto> dtos) {
        return ListUtils.map(dtos, PerformanceMapper::mapToEntity);
    }

    public static Performance mapToEntity(PerformanceDto dto) {
        return new Performance(
                DateMapper.mapOnlyDate(dto.getDate()),
                DateMapper.mapOnlyTime(dto.getTime()),
                dto.getStreamingUrl(),
                dto.getRemainingSeats(),
                dto.getStatus().getValue()
        );
    }

    public static List<PerformanceDto> mapToDto(List<Performance> entities) {
        return ListUtils.map(entities, PerformanceMapper::mapToDto);
    }

    public static PerformanceDto mapToDto(Performance entity) {
        return new PerformanceDto(
                DateMapper.mapOnlyDate(entity.getDate()),
                DateMapper.mapOnlyTime(entity.getTime()),
                entity.getStreamingUrl(),
                entity.getRemainingSeats(),
                Status.from(entity.getStatus())
        );
    }
}
