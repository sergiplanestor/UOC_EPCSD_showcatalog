package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.controllers.base.BaseController;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.dtos.CategoryDto;
import edu.uoc.epcsd.showcatalog.models.dtos.ShowDto;
import edu.uoc.epcsd.showcatalog.models.entities.Category;
import edu.uoc.epcsd.showcatalog.models.mappers.CategoryMapper;
import edu.uoc.epcsd.showcatalog.models.mappers.ShowMapper;
import edu.uoc.epcsd.showcatalog.models.entities.Show;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.services.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2(topic = "ShowController")
@RestController
@RequestMapping("/show")
public class ShowController extends BaseController {

    @Autowired
    private ShowService showService;

    @Autowired
    private KafkaTemplate<String, Show> kafkaTemplate;

    @GetMapping("/")
    @Operation(
            method = "getAllShows",
            summary = "Fetch all shows.",
            description = "Find every show existing."
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShowDto>> getAllShows() {
        logApiRequest("getAllShows");
        try {
            List<Show> entities = showService.getAll();
            return ResponseEntity.ok(ShowMapper.mapToDto(entities));
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @GetMapping("/find/name")
    @Operation(
            method = "findShowsByName",
            summary = "Find shows by name",
            description = "Find every show existing that the name matches exactly with the name provided.",
            parameters = {
                    @Parameter(
                            name = "isCaseSensitive",
                            description = "Flag to indicate if query needs to be case sensitive. By default is set to false."
                    ),
                    @Parameter(
                            name = "allowContains",
                            description = "Flag to indicate if match needs to be exactly or if should include the shows that it's name contains the value provided. By default is set to false."
                    ),
                    @Parameter(name = "value", description = "The name of the show that has to be fetched."),
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShowDto>> findShowsByName(@RequestParam(defaultValue = "false") boolean isCaseSensitive,
                                                         @RequestParam(defaultValue = "false") boolean allowContains,
                                                         @RequestParam(name = "value") String name) {
        logApiRequest("findShowsByName");
        try {
            List<Show> entities = showService.findByName(isCaseSensitive, allowContains, name);
            return ResponseEntity.ok(ShowMapper.mapToDto(entities));
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @GetMapping("/find/category")
    @Operation(
            method = "findShowsByCategory",
            summary = "Find shows by category",
            description = "Find every show existing that belongs to category matched by the name provided.",
            parameters = {
                    @Parameter(name = "value", description = "The name of the category to match with to discriminate shows."),
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShowDto>> findShowsByCategory(@RequestParam(name = "value") String categoryName) {
        logApiRequest("findShowsByCategory");
        try {
            List<Show> entities = showService.findByCategory(categoryName);
            return ResponseEntity.ok(ShowMapper.mapToDto(entities));
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @PostMapping("/")
    @ResponseBody
    @Operation(
            method = "createShow",
            summary = "Create new show.",
            description = "Create new show.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Show object to be created.",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "400", description = "Bad Request: Show params provided are invalid."),
                    @ApiResponse(responseCode = "201", description = "Created: Category provided has been successfully created."),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowDto> createShow(@RequestBody ShowDto showDto) throws Exception {
        logApiRequest("createShow");
        try {
            Show show = ShowMapper.mapToEntity(showDto);
            Show created = showService.save(show);
            ShowDto dto = ShowMapper.mapToDto(created);
            return ResponseEntity.ok(dto);
        } catch (InvalidParamError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @DeleteMapping("/{showId}")
    @Operation(
            method = "removeShow",
            summary = "Remove an existing show.",
            description = "Remove the show related to the provided id."
    )
    @ResponseBody
    public ResponseEntity<Void> removeShow(@PathVariable(name = "showId") Long id) throws Exception {
        logApiRequest("removeShow");
        try {
            showService.delete(id);
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
