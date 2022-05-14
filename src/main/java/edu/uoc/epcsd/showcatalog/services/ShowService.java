package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.models.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;
import edu.uoc.epcsd.showcatalog.utils.ValueValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Log4j2(topic = "ShowService")
@Component
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> getAll() {
        return showRepository.findAll();
    }

    public List<Show> findByName(boolean isCaseSensitive, boolean allowContains, String name) {
        if (!ValueValidator.nonNullOrBlank(name)) {
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

            return ListUtils.filter(getAll(), predicate);
        }
    }

    public List<Show> findByCategory(String name) {
        if (!ValueValidator.nonNullOrBlank(name)) {
            return new ArrayList<>();
        } else {
            return ListUtils.filter(getAll(), s -> ListUtils.any(s.getCategories(), c -> c.getName().equalsIgnoreCase(name)));
        }
    }

    public Show save(Show show) throws InvalidParamError {
        if (show == null || isAnyParamInvalid(show)) {
            throw new InvalidParamError(
                    true,
                    show == null ? "Object itself" : invalidParams(show).toString()
            );
        } else {
            return showRepository.save(show);
        }
    }

    public void delete(Long id) throws InvalidParamError {
        if (id == null) {
            throw new InvalidParamError(true, "id", "Not null");
        } else {
            Show show = showRepository.findById(id)
                    .orElseThrow(() -> new InvalidParamError(true, "id", "Not found"));
            showRepository.delete(show);
        }
    }

    private boolean isAnyParamInvalid(@NotNull Show show) {
        return !ValueValidator.nonNullOrBlank(show.getName(), show.getDescription(), show.getImage(),
                show.getDurationUnit(), show.getOnSaleDate()) ||
                !ValueValidator.nonNullOrNegative(show.getPrice(), show.getCapacity(), show.getStatus()) ||
                !ValueValidator.nonNullOrNegativeZeroInclusive(show.getDuration()) ||
                show.getCategories().isEmpty();
    }

    private List<String> invalidParams(@NotNull Show show) {
        List<String> invalid = new ArrayList<>();
        if (!ValueValidator.nonNullOrBlank(show.getName())) {
            invalid.add("name");
        }
        if (!ValueValidator.nonNullOrBlank(show.getDescription())) {
            invalid.add("description");
        }
        if (!ValueValidator.nonNullOrBlank(show.getImage())) {
            invalid.add("image");
        }
        if (!ValueValidator.nonNullOrBlank(show.getDurationUnit())) {
            invalid.add("duration value's unit");
        }
        if (!ValueValidator.nonNullOrBlank(show.getOnSaleDate())) {
            invalid.add("sale date");
        }
        if (!ValueValidator.nonNullOrNegative(show.getPrice())) {
            invalid.add("price");
        }
        if (!ValueValidator.nonNullOrNegative(show.getCapacity())) {
            invalid.add("capacity");
        }
        if (!ValueValidator.nonNullOrNegative(show.getStatus())) {
            invalid.add("status");
        }
        if (!ValueValidator.nonNullOrNegativeZeroInclusive(show.getDuration())) {
            invalid.add("duration value");
        }
        if (show.getCategories().isEmpty()) {
            invalid.add("No category");
        }
        return invalid;
    }
}
