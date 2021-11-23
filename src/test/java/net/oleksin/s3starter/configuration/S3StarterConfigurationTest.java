package net.oleksin.s3starter.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = S3StarterConfiguration.class)
class S3StarterConfigurationTest {
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}