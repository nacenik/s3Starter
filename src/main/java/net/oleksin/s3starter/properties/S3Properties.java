package net.oleksin.s3starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("s3")
public class S3Properties {
    String bucketName;
    String region;
}