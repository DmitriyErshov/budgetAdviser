CREATE TABLE money_transactions
(
    transaction_id SERIAL PRIMARY KEY,
    name    VARCHAR(30) NOT NULL,
    createdBy INTEGER,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    group_id INTEGER,
    is_Income BOOLEAN,
    sum DECIMAL,
    CONSTRAINT user_created FOREIGN KEY (createdBy) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT group_created FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE CASCADE
);
