package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.controllers.base.BaseController;
import edu.uoc.epcsd.showcatalog.models.dtos.CategoryDto;
import edu.uoc.epcsd.showcatalog.models.mappers.CategoryMapper;
import edu.uoc.epcsd.showcatalog.models.entities.Category;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2(topic = "CategoryController")
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    @Operation(
            method = "getAllCategories",
            summary = "Fetch every existing category",
            description = "Find every category existing."
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryDto>> getAllCategories() throws Exception {
        logApiRequest("getAllCategories");
        try {
            List<Category> entities = categoryService.getAll();
            return ResponseEntity.ok(CategoryMapper.mapToDto(entities));
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @PostMapping("/")
    @ResponseBody
    @Operation(
            method = "createCategory",
            summary = "Add new category.",
            description = "Create new category.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category object to be created.",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "400", description = "Bad Request: Category provided cannot be null."),
                    @ApiResponse(responseCode = "400", description = "Bad Request: Category provided cannot exist."),
                    @ApiResponse(responseCode = "201", description = "Created: Category provided has been successfully created."),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) throws Exception {
        logApiRequest("createCategory");
        try {
            Category category = CategoryMapper.mapToEntity(categoryDto);
            Category created = categoryService.save(category);
            CategoryDto dto = CategoryMapper.mapToDto(created);
            return ResponseEntity.ok(dto);
        } catch (InvalidParamError | NotUniqueValueError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @DeleteMapping("/{categoryId}")
    @Operation(
            method = "removeCategory",
            summary = "Remove existing category by id.",
            description = "Delete the category related to the provided id."
    )
    @ResponseBody
    public ResponseEntity<Void> removeCategory(@PathVariable(name = "categoryId") Long id) throws Exception {
        logApiRequest("removeCategory");
        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok(null);
        } catch (InvalidParamError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }
}
