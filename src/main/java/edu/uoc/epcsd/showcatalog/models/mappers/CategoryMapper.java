package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.db.entities.Category;
import edu.uoc.epcsd.showcatalog.models.dtos.CategoryDto;

import java.util.List;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.map;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static List<Category> mapToEntity(List<CategoryDto> dtos) {
        return map(dtos, CategoryMapper::mapToEntity);
    }

    public static Category mapToEntity(CategoryDto dto) {
        return new Category(dto.getName(), dto.getDescription());
    }

    public static List<CategoryDto> mapToDto(List<Category> entities) {
        return map(entities, CategoryMapper::mapToDto);
    }

    public static CategoryDto mapToDto(Category entity) {
        return new CategoryDto(entity.getName(), entity.getDescription());
    }
}
