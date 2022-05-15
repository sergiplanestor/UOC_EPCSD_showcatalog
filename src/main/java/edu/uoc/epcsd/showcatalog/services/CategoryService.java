package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.db.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category save(Category category) throws InvalidParamError, NotUniqueValueError;
    void deleteById(Long id) throws InvalidParamError;
}
