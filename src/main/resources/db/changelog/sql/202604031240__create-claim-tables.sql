CREATE TABLE claim
(
    id           UUID      PRIMARY KEY,
    claim_number UUID      NOT NULL,
    user_id      UUID      NOT NULL,
    description  TEXT,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP,

    CONSTRAINT fk_claim_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX claim_user_id_idx      ON claim (user_id);
CREATE INDEX claim_claim_number_idx ON claim (claim_number);

-- ============================================================

CREATE TABLE claim_file
(
    id           UUID        PRIMARY KEY,
    claim_id     UUID        NOT NULL,
    type         VARCHAR(20) NOT NULL,
    content_type VARCHAR(255),
    status       VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP   NOT NULL,
    uploaded_at  TIMESTAMP,
    updated_at   TIMESTAMP,

    CONSTRAINT fk_claim_file_claim_id FOREIGN KEY (claim_id) REFERENCES claim (id) ON DELETE CASCADE
);

CREATE INDEX claim_file_claim_id_idx ON claim_file (claim_id);
