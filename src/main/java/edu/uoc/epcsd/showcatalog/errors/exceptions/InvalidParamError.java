package edu.uoc.epcsd.showcatalog.errors.exceptions;


public class InvalidParamError extends Exception {

    private String param;
    private String reason;
    private boolean isHandledError;
    private static final String TEMPLATE = "Param provided [%s] is invalid. Param constraints=[%s].";
    private static final String DEFAULT = "Has been provided one or more invalid params. Please check the params constraints.";

    public InvalidParamError(boolean isHandled) {
        this(isHandled, DEFAULT);
    }

    public InvalidParamError(boolean isHandled, String param) {
        this(isHandled, param, null);
    }

    public InvalidParamError(boolean isHandled, String param, String reason) {
        this(isHandled, param, reason, String.format(TEMPLATE, param, reason));
    }

    private InvalidParamError(boolean isHandled, String param, String reason, String message) {
        super(message);
        this.isHandledError = isHandled;
        this.param = param;
        this.reason = reason;
    }

    public String param() {
        return param;
    }

    public String reason() {
        return reason;
    }

    public boolean isHandled() {
        return isHandledError;
    }
}
