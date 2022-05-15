package edu.uoc.epcsd.showcatalog.models.values;

import lombok.Getter;

import java.util.Arrays;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.find;

@Getter
public enum Status {
    OPEN(1),
    CLOSED(0);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public static Status from(int statusValue) {
        return from(statusValue, Status.CLOSED);
    }

    public static Status from(int statusValue, Status fallback) {
        return find(Arrays.asList(values()), fallback, s -> s.getValue() == statusValue);
    }
}
