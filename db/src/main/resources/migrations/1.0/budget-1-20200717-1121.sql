CREATE TABLE roles
(
    role_id INTEGER PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);

CREATE TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(15)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   CHAR(144)    NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by INTEGER,
    is_active  BOOLEAN      NOT NULL DEFAULT FALSE,
    is_locked  BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT users_created_by_fk FOREIGN KEY (created_by) REFERENCES users (user_id) ON DELETE SET NULL
);

CREATE TABLE user_roles
(
    user_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT user_roles_role_id_fk FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
);
