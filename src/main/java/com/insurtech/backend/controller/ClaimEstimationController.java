package com.insurtech.backend.controller;

import com.insurtech.backend.constants.ApiConstants;
import com.insurtech.backend.dto.api.response.ClaimResponse;
import com.insurtech.backend.service.ClaimEstimationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "ClaimEstimations",
    description = "Manage insurance claims estimation submitted by authenticated users.")
@RestController
@RequestMapping(ClaimEstimationController.URL)
@RequiredArgsConstructor
public class ClaimEstimationController {

  public static final String URL = ApiConstants.BASE_URL + "/estimations";

  private final ClaimEstimationService claimEstimationService;

  @Operation(
      summary = "Get claim estimation by claim number",
      description =
          "Retrieves the full details of a single claim estimation"
              + " number. Returns 404 if no claim estimation with the given number exists.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Claim estimation found — returns claim details",
        content = @Content(schema = @Schema(implementation = ClaimResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "claimNumber query parameter is missing",
        content = @Content(schema = @Schema())),
    @ApiResponse(
        responseCode = "401",
        description = "Missing or invalid JWT access token",
        content = @Content(schema = @Schema())),
    @ApiResponse(
        responseCode = "404",
        description = "Claim estimation not found",
        content = @Content(schema = @Schema())),
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected server error",
        content = @Content(schema = @Schema()))
  })
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/{claimNumber}/estimation")
  public ResponseEntity<Object> getByClaimNumber(
      @PathVariable
          @Parameter(
              description = "Unique claim number",
              example = "CLM-2024-000123",
              required = true)
          String claimNumber) {
    return ResponseEntity.ok(claimEstimationService.getByClaimNumber(claimNumber));
  }
}
