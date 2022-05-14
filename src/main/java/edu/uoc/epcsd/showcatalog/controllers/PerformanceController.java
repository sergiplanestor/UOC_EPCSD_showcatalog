package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.controllers.base.BaseController;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.dtos.CategoryDto;
import edu.uoc.epcsd.showcatalog.models.dtos.PerformanceDto;
import edu.uoc.epcsd.showcatalog.models.entities.Category;
import edu.uoc.epcsd.showcatalog.models.entities.Performance;
import edu.uoc.epcsd.showcatalog.models.mappers.CategoryMapper;
import edu.uoc.epcsd.showcatalog.models.mappers.PerformanceMapper;
import edu.uoc.epcsd.showcatalog.services.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2(topic = "PerformanceMapper")
@RestController
@RequestMapping("/performance")
public class PerformanceController extends BaseController {

    @Autowired
    private PerformanceService performanceService;

    @PostMapping("/")
    @ResponseBody
    @Operation(
            method = "createPerformance",
            summary = "Add new performance.",
            description = "Create new performance.",
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
    public ResponseEntity<PerformanceDto> createPerformance(@RequestParam String showName,
                                                            @RequestBody PerformanceDto performance) throws Exception {
        logApiRequest("createCategory");
        try {
            Performance entity = PerformanceMapper.mapToEntity(performance);
            Performance created = performanceService.save(showName, entity);
            PerformanceDto dto = PerformanceMapper.mapToDto(created);
            return ResponseEntity.ok(dto);
        } catch (InvalidParamError | NotUniqueValueError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @DeleteMapping("/{showId}/{performanceId}")
    @ResponseBody
    public ResponseEntity<Void> removePerformance(@PathVariable(name = "categoryId") Long id) throws Exception {
        throw new RuntimeException();
    }
}
