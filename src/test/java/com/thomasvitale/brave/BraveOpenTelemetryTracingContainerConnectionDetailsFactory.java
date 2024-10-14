package com.thomasvitale.brave;

import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinConnectionDetails;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;
import org.testcontainers.containers.Container;

public class BraveOpenTelemetryTracingContainerConnectionDetailsFactory extends ContainerConnectionDetailsFactory<Container<?>, ZipkinConnectionDetails> {

    BraveOpenTelemetryTracingContainerConnectionDetailsFactory() {
        super("otel/opentelemetry-collector-contrib",
                "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration");
    }

    @Override
    protected ZipkinConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<Container<?>> source) {
        return new BraveOpenTelemetryTracingContainerConnectionDetails(source);
    }

    private static final class BraveOpenTelemetryTracingContainerConnectionDetails extends ContainerConnectionDetails<Container<?>> implements ZipkinConnectionDetails {

        private BraveOpenTelemetryTracingContainerConnectionDetails(ContainerConnectionSource<Container<?>> source) {
            super(source);
        }

        @Override
        public String getSpanEndpoint() {
            return "http://%s:%d/v1/traces".formatted(getContainer().getHost(), getContainer().getMappedPort(4318));
        }

    }

}
