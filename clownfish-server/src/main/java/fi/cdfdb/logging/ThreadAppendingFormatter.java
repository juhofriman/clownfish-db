package fi.cdfdb.logging;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * This is just a stupid way to get thread name to logs...
 */
public class ThreadAppendingFormatter extends SimpleFormatter {

    @Override
    public String format(LogRecord record) {
        String baseMessage = super.format(record).stripTrailing();
        return baseMessage + " {" + Thread.currentThread().getName() + "}\n";
    }
}
