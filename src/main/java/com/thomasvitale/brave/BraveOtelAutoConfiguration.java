package com.thomasvitale.brave;

import zipkin2.reporter.Encoding;
import zipkin2.reporter.otel.brave.OtlpProtoV1Encoder;

import org.springframework.boot.actuate.autoconfigure.opentelemetry.OpenTelemetryProperties;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(ZipkinAutoConfiguration.class)
@EnableConfigurationProperties(OpenTelemetryProperties.class)
public class BraveOtelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OtlpProtoV1Encoder otlpProtoV1Encoder(OpenTelemetryProperties properties) {
        return OtlpProtoV1Encoder.newBuilder().resourceAttributes(properties.getResourceAttributes()).build();
    }

    @Bean
    public Encoding otlpEncoding() {
        return Encoding.PROTO3;
    }

}
