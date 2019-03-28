package guuog.nioserver.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class WriteLogger implements SingleLogger {
    private volatile static Logger writeLogger;

    private WriteLogger() {

    }

    public static Logger getLogger() {
        if (writeLogger == null) {
            synchronized (WriteLogger.class) {
                if (writeLogger == null) {
                    writeLogger = Logger.getLogger("WriteLogger");
                    Handler output = new ConsoleHandler();
                    output.setFormatter(new LoggerFormatter());
                    writeLogger.addHandler(output);
                }
            }
        }

        return writeLogger;
    }
}
