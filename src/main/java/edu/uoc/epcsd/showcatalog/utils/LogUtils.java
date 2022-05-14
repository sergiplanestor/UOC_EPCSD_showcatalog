package edu.uoc.epcsd.showcatalog.utils;

import org.apache.logging.log4j.Logger;

public class LogUtils {

    private static String header(boolean isStart, String header) {
        return "\n" + (isStart ? "<<< " : ">>> ") + header + "\n";
    }

    public static void i(Logger logger, String header, Object message) {
        logger.info(header(true, header));
        logger.info(message);
        logger.info(header(false, header));
    }

    public static void w(Logger logger, String header, Object message) {
        logger.info(header(true, header));
        logger.info(message);
        logger.info(header(false, header));
    }

    public static void e(Logger logger, String header, Object message) {
        logger.error(header(true, header));
        logger.error(message);
        logger.error(header(false, header));
    }

    public static void e(Logger logger, boolean isExpected, Object message) {
        String header = (isExpected ? "HANDLED" : "UNEXPECTED") + " ERROR";
        if (isExpected) {
            w(logger, header, message);
        } else {
            e(logger, header, message);
        }
    }
}
