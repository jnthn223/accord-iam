CREATE TABLE oauth_scopes
(
    id          UUID PRIMARY KEY,
    project_id  UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(512),
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT fk_oauth_scopes_project
        FOREIGN KEY (project_id)
            REFERENCES projects (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_oauth_scopes_project_name
        UNIQUE (project_id, name)
);

CREATE INDEX idx_oauth_scopes_project_id
    ON oauth_scopes (project_id);