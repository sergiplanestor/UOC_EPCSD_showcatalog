package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.entities.Category;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2(topic = "CategoryService")
@Component
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) throws InvalidParamError, NotUniqueValueError {
        if (isAnyParamInvalid(category)) {
            throw new InvalidParamError(
                    true,
                    "Category(name) and/or Category(description)",
                    "Not null or blank"
            );
        } else if (ListUtils.any(getAll(), c -> c.getName().equals(category.getName()))) {
            throw new NotUniqueValueError();
        } else {
            return categoryRepository.saveAndFlush(category);
        }
    }

    public void deleteById(Long id) throws InvalidParamError {
        if (id == null) {
            throw new InvalidParamError(true, "id", "Not null");
        } else {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new InvalidParamError(true, "id", "Not found"));
            categoryRepository.delete(category);
        }
    }

    private boolean isAnyParamInvalid(Category category) {
        return category == null || category.getName() == null || category.getName().isBlank() ||
                category.getDescription() == null || category.getDescription().isBlank();
    }
}
