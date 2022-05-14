package edu.uoc.epcsd.showcatalog.models.values;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Duration {
    private Long value;
    private Duration.Unit unit;

    public Duration(long value, String unit) {
        this.value = value;
        this.unit = Unit.valueOf(unit);
    }

    @Getter
    public enum Unit {
        MILLIS(1),
        SECONDS(1000),
        MINUTES(SECONDS.factor * 60),
        HOURS(MINUTES.factor * 60),
        DAYS(HOURS.factor * 24);

        private final int factor;

        Unit(int factor) {
            this.factor = factor;
        }

    }
    public Long millis() {
        return value * unit.getFactor();
    }

    public static Duration minutes(long minutes) {
        return new Duration(minutes, Unit.MINUTES);
    }

    public static Duration fromMillis(long millis, Unit unit) {
        return new Duration(millis/unit.getFactor(), unit);
    }
}
