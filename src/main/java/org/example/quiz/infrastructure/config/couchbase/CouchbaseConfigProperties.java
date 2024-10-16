package org.example.quiz.infrastructure.config.couchbase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "couchbase.properties")
public class CouchbaseConfigProperties {

    private String cacheableCollection;

    private String connectionString;

    private String username;

    private String password;

    private String bucketName;

    private String scopeName;
}
