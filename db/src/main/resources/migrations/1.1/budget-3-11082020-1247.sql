create table users_activation_codes
(
    user_id INTEGER,
    activation_code  CHAR(8) NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE SET NULL
);
