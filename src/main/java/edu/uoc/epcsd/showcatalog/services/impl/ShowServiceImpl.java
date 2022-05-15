package edu.uoc.epcsd.showcatalog.services.impl;

import edu.uoc.epcsd.showcatalog.errors.exceptions.IdentifierNotFoundError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import edu.uoc.epcsd.showcatalog.models.db.valueobj.Performance;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import edu.uoc.epcsd.showcatalog.services.ShowService;
import edu.uoc.epcsd.showcatalog.utils.ValidatorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.*;
import static edu.uoc.epcsd.showcatalog.utils.ValidatorUtils.*;

@Log4j2(topic = "ShowService")
@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Override
    public List<Show> getAll() {
        return showRepository.findAll();
    }

    @Override
    public Show getDetails(Long id) throws IdentifierNotFoundError {
        return showRepository.findById(id)
                .orElseThrow(() -> new IdentifierNotFoundError("show(id)", "Must exists"));
    }

    @Override
    public List<Performance> getShowPerformances(Long id) throws IdentifierNotFoundError {
        return getDetails(id).getPerformances();
    }

    @Override
    public List<Show> findByName(boolean isCaseSensitive, boolean allowContains, String name) {
        if (isNullOrBlank(name)) {
            return new ArrayList<>();
        } else {
            Predicate<Show> predicate;
            if (isCaseSensitive && allowContains) {
                predicate = s -> s.getName().contains(name);
            } else if (!isCaseSensitive && allowContains) {
                predicate = s -> s.getName().toLowerCase().contains(name.toLowerCase());
            } else if (isCaseSensitive) {
                predicate = s -> s.getName().equals(name);
            } else {
                predicate = s -> s.getName().equalsIgnoreCase(name);
            }

            return filter(getAll(), predicate);
        }
    }

    @Override
    public List<Show> findByCategory(String name) {
        if (isNullOrBlank(name)) {
            return new ArrayList<>();
        } else {
            return filter(getAll(), s -> any(s.getCategories(), c -> c.getName().equalsIgnoreCase(name)));
        }
    }

    @Override
    public Show saveShow(Show show) throws InvalidParamError {
        ValidatorUtils.Result validationResult = validateShowParams(show);
        if (validationResult.isFailed()) {
            throw new InvalidParamError(validationResult.failedParams(), validationResult.reasons());
        } else {
            return showRepository.saveAndFlush(show);
        }
    }

    @Override
    public Performance savePerformance(Long showId, Performance performance) throws IdentifierNotFoundError, InvalidParamError {
        Show show = getDetails(showId);
        performance.setShow(show);
        show.addPerformance(performance);
        Show updated = saveShow(show);
        return find(updated.getPerformances(), p -> p.getId().equals(performance.getId()));
    }

    @Override
    public void deleteShow(Long id) throws InvalidParamError {
        if (id == null) {
            throw new InvalidParamError("id", "Not null");
        } else {
            Show show = showRepository.findById(id)
                    .orElseThrow(() -> new InvalidParamError("id", "Not found"));
            showRepository.delete(show);
        }
    }

    @Override
    public void deletePerformance(Long showId, String performanceId) throws InvalidParamError, IdentifierNotFoundError {
        if (showId == null) {
            throw new InvalidParamError("showId", "Not null");
        } else if (isNullOrBlank(performanceId)) {
            throw new InvalidParamError("performanceId", "Not null");
        } else {
            Show show = getDetails(showId);
            List<Performance> performances = show.getPerformances();
            Performance performance = find(performances, p -> p.getId().equals(performanceId));
            performances.remove(performance);
            show.setPerformances(performances);
            showRepository.saveAndFlush(show);
        }
    }

    private ValidatorUtils.Result validateShowParams(Show show) {
        return ValidatorUtils.Result.join(
                isNull(i -> "Object itself", show),
                isNullOrBlank(nullOrBlankParameterProvider(),
                        show.getName(), show.getDescription(), show.getImage(), show.getDurationUnit(), show.getOnSaleDate()),
                isNullOrNegative(nullOrNegParameterProvider(),
                        show.getPrice(), show.getCapacity(), show.getStatus()),
                isNullOrNegativeZeroInclusive(nullOrNegZeroInclParameterProvider(),
                        show.getDuration())
        );
    }

    private ValidatorUtils.ParamNameProvider nullOrBlankParameterProvider() {
        return i -> {
            switch (i) {
                case 0:
                    return "show(name)";
                case 1:
                    return "show(description)";
                case 2:
                    return "show(image)";
                case 3:
                    return "show(duration value's unit)";
                case 4:
                    return "show(sale date)";
                default:
                    return "";
            }
        };
    }

    private ValidatorUtils.ParamNameProvider nullOrNegParameterProvider() {
        return i -> {
            switch (i) {
                case 0:
                    return "show(price)";
                case 1:
                    return "show(capacity)";
                case 2:
                    return "show(status)";
                default:
                    return "";
            }
        };
    }

    private ValidatorUtils.ParamNameProvider nullOrNegZeroInclParameterProvider() {
        return i -> "show(duration)";
    }

    private boolean isInvalid(@NotNull Show show) {
        return isNull(show) ||
                isNullOrBlank(show.getName(), show.getDescription(), show.getImage(), show.getDurationUnit(), show.getOnSaleDate()) ||
                isNullOrNegative(show.getPrice(), show.getCapacity(), show.getStatus()) ||
                isNullOrNegativeZeroInclusive(show.getDuration()) ||
                show.getCategories().isEmpty();
    }

    private List<String> invalidParams(@NotNull Show show) {
        List<String> invalid = new ArrayList<>();
        if (isNullOrBlank(show.getName())) {
            invalid.add("name");
        }
        if (isNullOrBlank(show.getDescription())) {
            invalid.add("description");
        }
        if (isNullOrBlank(show.getImage())) {
            invalid.add("image");
        }
        if (isNullOrBlank(show.getDurationUnit())) {
            invalid.add("duration value's unit");
        }
        if (isNullOrBlank(show.getOnSaleDate())) {
            invalid.add("sale date");
        }
        if (isNullOrNegative(show.getPrice())) {
            invalid.add("price");
        }
        if (isNullOrNegative(show.getCapacity())) {
            invalid.add("capacity");
        }
        if (isNullOrNegative(show.getStatus())) {
            invalid.add("status");
        }
        if (isNullOrNegativeZeroInclusive(show.getDuration())) {
            invalid.add("duration value");
        }
        if (show.getCategories().isEmpty()) {
            invalid.add("No category");
        }
        return invalid;
    }
}
