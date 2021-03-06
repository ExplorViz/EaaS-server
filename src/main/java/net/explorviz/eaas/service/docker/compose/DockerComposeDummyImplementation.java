package net.explorviz.eaas.service.docker.compose;

import net.explorviz.eaas.service.process.BackgroundProcess;
import net.explorviz.eaas.service.process.ProcessListener;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;

/**
 * Implements {@link DockerComposeAdapter} with all no-op methods. Can be used for testing purposes or frontend
 * development without a docker daemon present.
 */
public class DockerComposeDummyImplementation implements DockerComposeAdapter {
    @Override
    public void up(@NonNull DockerComposeDefinition service) {
        service.setRunning(true);
        service.setStartedTime(ZonedDateTime.now());
    }

    @Override
    public void down(@NonNull DockerComposeDefinition service) {
        service.setRunning(false);
    }

    @Override
    public String logs(@NonNull DockerComposeDefinition service, @NonNull String... serviceNames) {
        return "";
    }

    @Override
    public BackgroundProcess logsFollow(@NonNull DockerComposeDefinition service, @NonNull String... serviceNames) {
        return new DummyBackgroundProcess();
    }

    private static final class DummyBackgroundProcess extends BackgroundProcess {
        @Override
        public void close() {
        }

        @Override
        public void startListening(@NonNull ProcessListener listener) {
            listener.onDied(0);
        }
    }
}
