package com.epam.esm.config.localization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class LanguageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    private static final String ENGLISH = "en";
    private static final String UZBEK = "uz";
    private static final String RUSSIAN = "ru";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String MESSAGES_BASE_NAME = "messages";
    private static final String DEFAULT_ENCODING = "UTF-8";

    List<Locale> LOCALES = Arrays.asList(new Locale(ENGLISH),
                                         new Locale(UZBEK),
                                         new Locale(RUSSIAN));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader(ACCEPT_LANGUAGE);
        return (headerLang == null || headerLang.isEmpty())
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasename(MESSAGES_BASE_NAME);
        bundleMessageSource.setDefaultEncoding(DEFAULT_ENCODING);
        bundleMessageSource.setDefaultLocale(Locale.ENGLISH);
        bundleMessageSource.setUseCodeAsDefaultMessage(true);
        return bundleMessageSource;
    }
}
