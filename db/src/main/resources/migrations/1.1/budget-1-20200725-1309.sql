CREATE TABLE global_roles
(
    global_role_id integer PRIMARY KEY ,
    name CHARACTER VARYING (50)
);

ALTER TABLE users
ADD global_role_id integer REFERENCES global_roles(global_role_id) NOT NULL ;

DROP TABLE users_groups;
DROP TABLE user_roles;
DROP TABLE users_roles_in_groups;
DROP TABLE roles;

ALTER TABLE roles_group
RENAME TO roles;

CREATE TABLE category
(
    category_id integer PRIMARY KEY ,
    name CHARACTER VARYING (70)
);

ALTER TABLE money_transactions
ADD Amount double precision,
ADD category_id integer REFERENCES category (category_id);

CREATE TABLE group_members
(
    group_id integer REFERENCES groups(group_id) PRIMARY KEY,
    user_id integer REFERENCES users(user_id),
    role_id integer REFERENCES roles (role_id)
)


