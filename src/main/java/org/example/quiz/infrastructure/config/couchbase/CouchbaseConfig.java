package org.example.quiz.infrastructure.config.couchbase;

import com.couchbase.client.java.codec.JsonTranscoder;
import com.couchbase.client.java.codec.Transcoder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.quiz.enums.Caches;
import org.example.quiz.serializer.ResponseEntityDeserializer;
import org.springframework.boot.autoconfigure.cache.CouchbaseCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;


@Configuration
@EnableCouchbaseAuditing
@EnableTransactionManagement
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    private final CouchbaseConfigProperties couchbaseConfigProperties;

    public CouchbaseConfig(CouchbaseConfigProperties couchbaseConfigProperties) {
        this.couchbaseConfigProperties = couchbaseConfigProperties;
    }

    @Override
    public String getConnectionString() {
        return couchbaseConfigProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseConfigProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseConfigProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseConfigProperties.getBucketName();
    }

    @Override
    public String getScopeName() {
        return couchbaseConfigProperties.getScopeName();
    }

    @Bean
    public CouchbaseCacheManagerBuilderCustomizer couchbaseCacheManagerBuilderCustomizer(ObjectMapper objectMapper) {
        var couchbaseObjectMapper = couchbaseObjectMapper(objectMapper);
        var objectMapperSerializer = new CouchbaseJsonSerializer(couchbaseObjectMapper);
        var transcoder = JsonTranscoder.create(objectMapperSerializer);

        return builder -> builder
                .withCacheConfiguration(Caches.ETERNAL,
                        getCacheConfiguration(Duration.ofSeconds(Caches.ETERNAL_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.ONE_MINUTE,
                        getCacheConfiguration(Duration.ofSeconds(Caches.ONE_MINUTE_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.FIVE_MINUTES,
                        getCacheConfiguration(Duration.ofSeconds(Caches.FIVE_MINUTES_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.FIFTEEN_MINUTES,
                        getCacheConfiguration(Duration.ofSeconds(Caches.FIFTEEN_MINUTES_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.HALF_HOUR,
                        getCacheConfiguration(Duration.ofSeconds(Caches.HALF_HOUR_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.ONE_HOUR,
                        getCacheConfiguration(Duration.ofSeconds(Caches.ONE_HOUR_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder))
                .withCacheConfiguration(Caches.ONE_DAY,
                        getCacheConfiguration(Duration.ofSeconds(Caches.ONE_DAY_TTL),
                                couchbaseConfigProperties.getCacheableCollection(),
                                transcoder)).build();
    }

    private ObjectMapper couchbaseObjectMapper(ObjectMapper objectMapper) {
        var couchbaseObjectMapper = objectMapper.copy();

        var deserializerModule = new SimpleModule();
        deserializerModule.addDeserializer(ResponseEntity.class, new ResponseEntityDeserializer());
        couchbaseObjectMapper.registerModule(deserializerModule);

        couchbaseObjectMapper.activateDefaultTyping(
                couchbaseObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);

        return couchbaseObjectMapper;
    }

    private CouchbaseCacheConfiguration getCacheConfiguration(Duration ttl, String collection,
                                                              Transcoder transcoder) {

        return CouchbaseCacheConfiguration.defaultCacheConfig()
                .valueTranscoder(transcoder)
                .collection(collection)
                .entryExpiry(ttl);
    }
}