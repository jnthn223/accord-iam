CREATE TABLE oauth_clients
(
    id            UUID PRIMARY KEY,
    project_id    UUID         NOT NULL,
    client_id     VARCHAR(255) NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT fk_oauth_clients_project
        FOREIGN KEY (project_id)
            REFERENCES projects (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_oauth_clients_project_client
        UNIQUE (project_id, client_id)
);

CREATE INDEX idx_oauth_clients_project_id
    ON oauth_clients (project_id);