ALTER TABLE groups
    DROP balance,
    ADD balance double precision DEFAULT 0;
