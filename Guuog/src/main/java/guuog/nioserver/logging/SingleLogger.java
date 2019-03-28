package guuog.nioserver.logging;

import java.util.logging.Logger;

public interface SingleLogger {
    static Logger getLogger() {
        return Logger.getLogger("root Logger");
    }
}
