CREATE TABLE roles_group
(
    role_id INTEGER PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);

CREATE TABLE groups
(
    group_id    SERIAL PRIMARY KEY,
    group_name  VARCHAR(50),
    description varchar(150),
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by  INTEGER,
    balance     DECIMAL  NOT NULL DEFAULT 0,
    CONSTRAINT groups_created_by_fk FOREIGN KEY (created_by) REFERENCES users (user_id) ON DELETE SET NULL
);

CREATE TABLE users_groups
(
    user_id INTEGER,
    group_id INTEGER,
    PRIMARY KEY (user_id, group_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT group_id_fk FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE CASCADE
);
INSERT INTO roles_group(role_id, name)
VALUES (3, 'GROUP_ADMIN'),
       (4, 'GROUP_USER');

CREATE TABLE users_roles_in_groups
(
    user_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT user_roles_role_id_fk FOREIGN KEY (role_id) REFERENCES roles_group (role_id) ON DELETE CASCADE
);
