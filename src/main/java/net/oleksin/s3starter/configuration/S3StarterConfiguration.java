package net.oleksin.s3starter.configuration;

import net.oleksin.s3starter.client.S3ClientWorker;
import net.oleksin.s3starter.properties.S3Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3StarterConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "s3", value = {"bucketName", "region"})
    public S3ClientWorker s3ClientWorker(S3Properties s3Properties) {
        return new S3ClientWorker(s3Properties);
    }
}
