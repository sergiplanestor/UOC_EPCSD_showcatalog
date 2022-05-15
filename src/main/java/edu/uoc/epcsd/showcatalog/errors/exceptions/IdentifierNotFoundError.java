package edu.uoc.epcsd.showcatalog.errors.exceptions;

public class IdentifierNotFoundError extends BaseError {

    public IdentifierNotFoundError(String param, String reason) {
        super(param, reason);
    }

    @Override
    protected String getMessageTemplate() {
        return "The identifier provided [%s] is invalid. Param constraints=[%s].";
    }
}
