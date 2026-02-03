ALTER TABLE oauth_clients
    ADD COLUMN metadata jsonb NOT NULL DEFAULT '{}';