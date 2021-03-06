package sh.spinlock.higgins.agent;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.spinlock.higgins.agent.connection.HostConnection;
import sh.spinlock.higgins.agent.file.RootDirectory;

import java.io.IOException;

public class HigginsAgent {
    private static final Logger LOG = LogManager.getLogger();

    @Getter
    private static HigginsAgent instance;

    @Getter
    @Setter
    private HostConnection connection;

    @Getter
    private RootDirectory rootDirectory;

    public void start() {
        rootDirectory = new RootDirectory("higgins-agent/");

        try {
            rootDirectory.initialize();
        } catch (IOException e) {
            LOG.error("Could not initialize root directory.", e);
            return;
        }

        try {
            connection.connect();
        } catch (IOException e) {
            LOG.error("Could not connect to host.", e);
        }
    }

    public static void initializeInstance(HigginsAgent agent) {
        instance = agent;
    }
}
