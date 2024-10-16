package org.example.quiz.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSourceConfiguration {

    @Bean
    public static MessageSource messageSource() {

        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages_tr");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    public static String getMessage(String message, String... dynamicValues) {
        return messageSource().getMessage(message, dynamicValues, Locale.getDefault());
    }
}
