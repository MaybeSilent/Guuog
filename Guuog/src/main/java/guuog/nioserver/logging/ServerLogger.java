package guuog.nioserver.logging;

import guuog.nioserver.connector.Server;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ServerLogger implements SingleLogger {
    private final static Logger serverLogger = Logger.getLogger(Server.class.getName());

    static {
        Handler output = new ConsoleHandler();
        output.setFormatter(new LoggerFormatter());
        serverLogger.addHandler(output);
        serverLogger.getParent().removeHandler(serverLogger.getParent().getHandlers()[0]);
    }

    private ServerLogger() {

    }

    public static Logger getLogger() {
        return serverLogger;
    }
}
