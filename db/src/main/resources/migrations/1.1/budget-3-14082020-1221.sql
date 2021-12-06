ALTER TABLE groups
    DROP join_code,
    ADD join_code CHAR(64) NOT NULL;
