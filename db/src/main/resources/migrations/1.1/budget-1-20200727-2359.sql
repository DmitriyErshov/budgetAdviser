ALTER TABLE group_members
    DROP group_id,
    ADD group_id integer REFERENCES groups(group_id)  NOT NULL;
