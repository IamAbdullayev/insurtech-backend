package com.insurtech.backend.client;

import com.insurtech.backend.dto.ai.request.AIAnalysisRequest;
import com.insurtech.backend.dto.ai.response.AIAnalysisResponse;
import com.insurtech.backend.exception.AIServiceException;
import com.insurtech.backend.exception.handler.ErrorCode;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Component
public class AIAnalysisClient {
  private final RestClient restClient;

  public AIAnalysisClient(@Qualifier("aiRestClient") RestClient restClient) {
    this.restClient = restClient;
  }

  public AIAnalysisResponse analyze(AIAnalysisRequest request) {
    log.info("Sending analysis request to AI service. imageCount: {}", request.links().size());

    try {
      AIAnalysisResponse response =
          restClient
              .post()
              .uri("/analyze")
              .contentType(MediaType.APPLICATION_JSON)
              .body(request.links())
              .retrieve()
              .body(AIAnalysisResponse.class);

      if (Objects.isNull(response)) {
        log.info("AI service responded successfully. But response is null");
        return null;
      }

      log.info(
          "AI service responded successfully. damagedPartsCount: {}",
          response.damagedParts().size());
      return response;

    } catch (RestClientResponseException ex) {
      throw new AIServiceException(
          ErrorCode.AI_SERVICE_ERROR,
          "AI service returned error. status: "
              + ex.getStatusCode()
              + " | body: "
              + ex.getResponseBodyAsString(),
          ex);
    } catch (ResourceAccessException ex) {
      throw new AIServiceException(
          ErrorCode.AI_SERVICE_ERROR, "AI service is unreachable or timed out", ex);
    }
  }
}
