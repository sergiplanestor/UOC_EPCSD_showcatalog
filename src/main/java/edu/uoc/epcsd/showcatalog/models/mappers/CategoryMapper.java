package edu.uoc.epcsd.showcatalog.models.mappers;

import edu.uoc.epcsd.showcatalog.models.dtos.CategoryDto;
import edu.uoc.epcsd.showcatalog.models.entities.Category;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;

import java.util.List;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static List<Category> mapToEntity(List<CategoryDto> dtos) {
        return ListUtils.map(dtos, CategoryMapper::mapToEntity);
    }

    public static Category mapToEntity(CategoryDto dto) {
        return new Category(dto.getName(), dto.getDescription());
    }

    public static List<CategoryDto> mapToDto(List<Category> entities) {
        return ListUtils.map(entities, CategoryMapper::mapToDto);
    }

    public static CategoryDto mapToDto(Category entity) {
        return new CategoryDto(entity.getName(), entity.getDescription());
    }
}
