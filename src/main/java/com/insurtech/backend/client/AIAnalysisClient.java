package com.insurtech.backend.client;

import com.insurtech.backend.dto.ai.request.AIAnalysisRequest;
import com.insurtech.backend.dto.ai.response.AIAnalysisResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class AIAnalysisClient {
  private final RestClient restClient;

  @Value("${ai.analyze-uri}")
  private String analyzeUri;

  public AIAnalysisClient(@Qualifier("aiRestClient") RestClient restClient) {
    this.restClient = restClient;
  }

  public AIAnalysisResponse analyze(AIAnalysisRequest request) {
    log.info("Sending analysis request to AI service. imageCount: {}", request.links().size());

    AIAnalysisResponse response =
        restClient
            .post()
            .uri(analyzeUri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request.links())
            .retrieve()
            .body(AIAnalysisResponse.class);

    if (Objects.isNull(response)) {
      log.info("AI service responded successfully. But response is null");
      return null;
    }

    log.info(
        "AI service responded successfully. damagedPartsCount: {}", response.damagedParts().size());
    return response;
  }
}
