package guuog.nioserver.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ReadLogger implements SingleLogger {

    private volatile static Logger ReadLogger ;

    private ReadLogger() {

    }

    public static Logger getLogger() {
        if (ReadLogger == null) {
            synchronized (ReadLogger.class) {
                if (ReadLogger == null) {
                    ReadLogger = Logger.getLogger("ReadLogger");
                    Handler output = new ConsoleHandler();
                    output.setFormatter(new LoggerFormatter());
                    ReadLogger.addHandler(output);
                }
            }
        }
        return ReadLogger;
    }
}
