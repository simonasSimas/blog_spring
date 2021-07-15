DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;

CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(10) NOT NULL,
    post_id BIGINT
);

CREATE TABLE "post"
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_edited TIMESTAMP
);

CREATE TABLE role
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE user_role
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT,
    role_id BIGINT
);