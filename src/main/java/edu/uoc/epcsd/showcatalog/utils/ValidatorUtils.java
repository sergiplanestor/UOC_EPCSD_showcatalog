package edu.uoc.epcsd.showcatalog.utils;

import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.*;

import static edu.uoc.epcsd.showcatalog.utils.CollectionUtils.*;

public class ValidatorUtils {

    private static final String NOT_NULLABLE_ALLOWED = "No nullable values allowed";
    private static final String NOT_NULLABLE_OR_BLANK_ALLOWED = "No nullable or blank values allowed";
    private static final String NOT_NEGATIVE_ALLOWED = "No negative values allowed";
    private static final String NOT_NEGATIVE_OR_ZERO_ALLOWED = "No negative values or zero allowed";

    public static ValidatorUtils.Result isNull(ParamNameProvider provider, Object... objects) {
        ValidatorUtils.Result result = new ValidatorUtils.Result();
        List<Object> params = listOf(objects);
        Map<Integer, Boolean> map = associate(params, params::indexOf, Objects::isNull);
        filter(listOf(map.entrySet()), Map.Entry::getValue).forEach(
                e -> result.addFailed(provider.getParamNameAt(e.getKey()), NOT_NULLABLE_ALLOWED)
        );
        return result;
    }

    public static boolean isNull(Object... objects) {
        return CollectionUtils.any(Objects::isNull, objects);
    }

    public static ValidatorUtils.Result isNullOrBlank(ParamNameProvider provider, String... strings) {
        ValidatorUtils.Result result = new ValidatorUtils.Result();
        List<String> params = listOf(strings);
        Map<Integer, Boolean> map = associate(params, params::indexOf, s -> s == null || s.isBlank());
        filter(listOf(map.entrySet()), Map.Entry::getValue).forEach(
                e -> result.addFailed(provider.getParamNameAt(e.getKey()), NOT_NULLABLE_OR_BLANK_ALLOWED)
        );
        return result;
    }

    public static boolean isNullOrBlank(String... strings) {
        return CollectionUtils.any(s -> s == null || s.isBlank(), strings);
    }

    @SafeVarargs
    public static <N extends Number> ValidatorUtils.Result isNullOrNegative(ParamNameProvider provider,
                                                                            N... numbers) {
        return isNullOrNegative(provider, true, numbers);
    }

    @SafeVarargs
    public static <N extends Number> boolean isNullOrNegative(N... numbers) {
        return isNullOrNegative(true, numbers);
    }

    @SafeVarargs
    public static <N extends Number> ValidatorUtils.Result isNullOrNegativeZeroInclusive(ParamNameProvider provider,
                                                                                         N... numbers) {
        return isNullOrNegative(provider, false, numbers);
    }

    @SafeVarargs
    public static <N extends Number> boolean isNullOrNegativeZeroInclusive(N... numbers) {
        return isNullOrNegative(false, numbers);
    }

    @SafeVarargs
    private static <N extends Number> ValidatorUtils.Result isNullOrNegative(ParamNameProvider provider,
                                                                             boolean isZeroInclusive,
                                                                             N... numbers) {
        ValidatorUtils.Result result = new ValidatorUtils.Result();
        List<N> params = listOf(numbers);
        Map<Integer, Boolean> map = associate(
                params,
                params::indexOf,
                n -> n.doubleValue() < 0 || (!isZeroInclusive && n.doubleValue() == 0)
        );

        filter(listOf(map.entrySet()), Map.Entry::getValue).forEach(
                e -> result.addFailed(provider.getParamNameAt(e.getKey()), isZeroInclusive ? NOT_NEGATIVE_OR_ZERO_ALLOWED : NOT_NEGATIVE_ALLOWED)
        );
        return result;
    }

    @SafeVarargs
    private static <N extends Number> boolean isNullOrNegative(boolean isZeroInclusive, N... numbers) {
        return CollectionUtils.any(n -> n.doubleValue() < 0 || (!isZeroInclusive && n.doubleValue() == 0), numbers);
    }

    private ValidatorUtils() {
    }

    @NoArgsConstructor
    public static class Result {
        private final Map<String, String> mapFailedParamToReason = new HashMap<>();

        public List<String> failedParamList() {
            return listOf(mapFailedParamToReason.keySet());
        }

        public String failedParams() {
            return failedParamList().toString();
        }

        public String reasons() {
            return listOf(mapFailedParamToReason.values()).toString();
        }

        public void addFailed(String param, String reason) {
            addFailed(param, reason, null);
        }

        public void addFailed(String param, String reason, @Nullable String value) {
            mapFailedParamToReason.put(param, reason + " and current value is `" + (value == null ? "null" : value) + "`");
        }

        private void removeFailed(String param) {
            this.mapFailedParamToReason.remove(param);
        }

        public boolean isSuccess() {
            return mapFailedParamToReason.isEmpty();
        }

        public boolean isFailed() {
            return !isSuccess();
        }

        public static Result join(Result... results) {
            Result resultJoined = new Result();
            listOf(results).forEach(result -> {
                List<String> failed = listOf(result.failedParams());
                List<String> failedNonRepeated = filter(failed, param -> result.failedParamList().contains(param));
                failedNonRepeated.forEach(result::removeFailed);
                resultJoined.mapFailedParamToReason.putAll(result.mapFailedParamToReason);
            });
            return resultJoined;
        }
    }

    public interface ParamNameProvider {
        String getParamNameAt(int index);
    }
}
