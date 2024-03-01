package com.ruoyi.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MinioConfig {
    @Value("${minio.config.url}")
    private String url;
    @Value("${minio.config.bucket}")
    private String bucket;
}
