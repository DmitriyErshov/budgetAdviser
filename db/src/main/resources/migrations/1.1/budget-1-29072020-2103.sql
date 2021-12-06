CREATE TABLE month_expenditure
(
    created_at      TIMESTAMP  NOT NULL,
    group_id        INTEGER,
    category_id     INTEGER,
    recommended_sum double precision DEFAULT 0,
    spent_sum       double precision DEFAULT 0,
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE SET NULL,
    CONSTRAINT group_fk FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE SET NULL
);

ALTER TABLE money_transactions
    DROP sum;
