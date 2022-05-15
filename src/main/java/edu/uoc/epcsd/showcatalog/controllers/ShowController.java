package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.controllers.base.BaseController;
import edu.uoc.epcsd.showcatalog.errors.exceptions.IdentifierNotFoundError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.models.db.valueobj.Performance;
import edu.uoc.epcsd.showcatalog.models.dtos.PerformanceDto;
import edu.uoc.epcsd.showcatalog.models.dtos.ShowDetailedDto;
import edu.uoc.epcsd.showcatalog.models.dtos.ShowOverviewDto;
import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import edu.uoc.epcsd.showcatalog.models.mappers.PerformanceMapper;
import edu.uoc.epcsd.showcatalog.models.mappers.ShowMapper;
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
    public ResponseEntity<List<ShowOverviewDto>> getAllShows() {
        logApiRequest("getAllShows");
        try {
            List<Show> entities = showService.getAll();
            return ResponseEntity.ok(ShowMapper.mapToOverviewDto(entities));
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @GetMapping("/{showId}")
    @Operation(
            method = "getShowDetails",
            summary = "Fetch show details.",
            description = "Fetch show details"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShowDetailedDto> getShowDetails(@PathVariable(name = "showId") Long id) throws Exception {
        logApiRequest("getShowDetails");
        try {
            Show entity = showService.getDetails(id);
            return ResponseEntity.ok(ShowMapper.mapToDetailedDto(entity));
        } catch (IdentifierNotFoundError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @GetMapping("/{showId}/performances")
    @Operation(
            method = "getShowPerformances",
            summary = "Get all show's performances.",
            description = "Fetch performances from given show."
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PerformanceDto>> getShowPerformances(@PathVariable(name = "showId") Long id) throws Exception {
        logApiRequest("getShowPerformances");
        try {
            List<Performance> entities = showService.getShowPerformances(id);
            return ResponseEntity.ok(PerformanceMapper.mapToDto(entities));
        } catch (IdentifierNotFoundError error) {
            logHandledError(error);
            throw error;
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
    public ResponseEntity<List<ShowOverviewDto>> findShowsByName(@RequestParam(defaultValue = "false") boolean isCaseSensitive,
                                                                 @RequestParam(defaultValue = "false") boolean allowContains,
                                                                 @RequestParam(name = "value") String name) {
        logApiRequest("findShowsByName");
        try {
            List<Show> entities = showService.findByName(isCaseSensitive, allowContains, name);
            return ResponseEntity.ok(ShowMapper.mapToOverviewDto(entities));
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
    public ResponseEntity<List<ShowOverviewDto>> findShowsByCategory(@RequestParam(name = "value") String categoryName) {
        logApiRequest("findShowsByCategory");
        try {
            List<Show> entities = showService.findByCategory(categoryName);
            return ResponseEntity.ok(ShowMapper.mapToOverviewDto(entities));
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
                    @ApiResponse(responseCode = "201", description = "Created: Show provided has been successfully created."),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowOverviewDto> createShow(@RequestBody ShowOverviewDto showOverviewDto) throws Exception {
        logApiRequest("createShow");
        try {
            Show show = ShowMapper.mapOverviewToEntity(showOverviewDto);
            Show created = showService.saveShow(show);
            ShowOverviewDto dto = ShowMapper.mapToOverviewDto(created);
            return ResponseEntity.ok(dto);
        } catch (InvalidParamError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @PostMapping("/{showId}/performance")
    @ResponseBody
    @Operation(
            method = "createPerformance",
            summary = "Create new performance.",
            description = "Create new performance.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance object to be created.",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "400", description = "Bad Request: Performance params provided are invalid."),
                    @ApiResponse(responseCode = "201", description = "Created: Performance provided has been successfully created."),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PerformanceDto> createPerformance(@PathVariable(name = "showId") Long showId,
                                                            @RequestBody PerformanceDto performance) throws Exception {
        logApiRequest("createPerformance");
        try {
            Performance created = showService.savePerformance(showId, PerformanceMapper.mapToEntity(performance));
            return ResponseEntity.ok(PerformanceMapper.mapToDto(created));
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
            showService.deleteShow(id);
            return ResponseEntity.ok(null);
        } catch (InvalidParamError error) {
            logHandledError(error);
            throw error;
        } catch (Exception error) {
            logUnexpectedError(error);
            throw error;
        }
    }

    @DeleteMapping("/{showId}/performance")
    @Operation(
            method = "removePerformance",
            summary = "Remove an existing performance.",
            description = "Remove an existing performance from the provided show related to showId."
    )
    @ResponseBody
    public ResponseEntity<Void> removePerformance(@PathVariable(name = "showId") Long showId,
                                                  @RequestParam(name = "performanceId") String performanceId) throws Exception {
        logApiRequest("removePerformance");
        try {
            showService.deletePerformance(showId, performanceId);
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
