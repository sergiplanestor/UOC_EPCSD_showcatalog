package edu.uoc.epcsd.showcatalog.errors.exceptions;


public class InvalidParamError extends BaseError {

    public InvalidParamError(String param, String reason) {
        super(param, reason);
    }

    @Override
    protected String getMessageTemplate() {
        return "Param provided [%s] is invalid. Param constraints=[%s].";
    }
}
