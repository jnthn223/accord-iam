CREATE TABLE oauth_client_scopes
(
    client_id UUID NOT NULL,
    scope_id  UUID NOT NULL,

    CONSTRAINT pk_oauth_client_scopes
        PRIMARY KEY (client_id, scope_id),

    CONSTRAINT fk_client_scopes_client
        FOREIGN KEY (client_id)
            REFERENCES oauth_clients (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_client_scopes_scope
        FOREIGN KEY (scope_id)
            REFERENCES oauth_scopes (id)
            ON DELETE CASCADE
);