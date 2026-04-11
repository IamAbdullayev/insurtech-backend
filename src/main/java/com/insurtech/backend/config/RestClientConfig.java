package com.insurtech.backend.config;

import java.net.http.HttpClient;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
  private final AIAnalysisProperties aiAnalysisProperties;

  @Bean("aiRestClient")
  public RestClient aiRestClient() {
    HttpClient httpClient =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(aiAnalysisProperties.connectTimeoutSeconds()))
            .build();

    JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
    factory.setReadTimeout(Duration.ofMinutes(aiAnalysisProperties.readTimeoutMinutes()));

    return RestClient.builder()
        .requestFactory(factory)
        .baseUrl(aiAnalysisProperties.baseUrl())
        .build();
  }
}
