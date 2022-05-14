package edu.uoc.epcsd.showcatalog.controllers.base;

import edu.uoc.epcsd.showcatalog.utils.LogUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BaseController {

    protected void logApiRequest(String request) {
        String msg = request != null ? request : "";
        LogUtils.i(log, "REQUEST=[" + msg + "]", "");
    }

    protected void logHandledError(Exception cause) {
        if (cause != null) {
            LogUtils.e(log, true, cause.getMessage());
        }
    }

    protected void logUnexpectedError(Exception cause) {
        if (cause != null) {
            LogUtils.e(log, false, cause.getStackTrace());
        }
    }
}
