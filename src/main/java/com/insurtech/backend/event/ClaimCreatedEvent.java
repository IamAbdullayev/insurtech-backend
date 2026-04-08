package com.insurtech.backend.event;

import java.util.UUID;

public record ClaimCreatedEvent(UUID claimId) {}
