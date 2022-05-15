package edu.uoc.epcsd.showcatalog.services.impl;

import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.db.entities.Category;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.services.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.any;
import static edu.uoc.epcsd.showcatalog.utils.ValidatorUtils.isNullOrBlank;

@Log4j2(topic = "CategoryService")
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) throws InvalidParamError, NotUniqueValueError {
        if (isInvalid(category)) {
            throw new InvalidParamError("Category(name) and/or Category(description)", "Not null or blank");
        } else if (any(getAll(), c -> c.getName().equals(category.getName()))) {
            throw new NotUniqueValueError();
        } else {
            return categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public void deleteById(Long id) throws InvalidParamError {
        if (id == null) {
            throw new InvalidParamError("id", "Not null");
        } else {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new InvalidParamError("id", "Not found"));
            categoryRepository.delete(category);
        }
    }

    private boolean isInvalid(Category category) {
        return category == null || isNullOrBlank(category.getName(), category.getDescription());
    }
}
