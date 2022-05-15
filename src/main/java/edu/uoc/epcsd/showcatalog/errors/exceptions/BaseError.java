package edu.uoc.epcsd.showcatalog.errors.exceptions;

import lombok.Getter;

@Getter
public abstract class BaseError extends Exception {

    protected String param;
    protected String reason;

    public BaseError(String param, String reason) {
        super();
        this.param = param;
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return String.format(getMessageTemplate(), param, reason);
    }

    protected abstract String getMessageTemplate();
}
