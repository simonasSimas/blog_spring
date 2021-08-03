DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS comment CASCADE;

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL
);

CREATE TABLE post
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    title        VARCHAR(255)          NOT NULL,
    content      TEXT                  NOT NULL,
    date_created timestamp NOT NULL DEFAULT now(),
    date_edited  TIMESTAMP NOT NULL DEFAULT now(),
    users_id     BIGINT REFERENCES users (id)
);

CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE   NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGINT REFERENCES users (id),
    role_id BIGINT REFERENCES role (id)
);

CREATE TABLE comment
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    content VARCHAR(255)          NOT NULL,
    blog_id BIGINT REFERENCES post (id),
    user_id BIGINT REFERENCES users (id)
);

INSERT INTO users (email, password, first_name, last_name)
VALUES ('ad', '{bcrypt}$2a$10$xCdNkyDrOP21RbfZkYI7sOIvYSkD543Bwljfv3lyyr50X02q5UIau', 'Bob', 'Admin'),
       ('simp', '{bcrypt}$2a$10$xCdNkyDrOP21RbfZkYI7sOIvYSkD543Bwljfv3lyyr50X02q5UIau', 'Michael', 'Pleb');

INSERT INTO post (title, content, users_id, date_created, date_edited)
VALUES ('a', 'Hai', 1, now(), now()),
       ('b', 'Hai1', 2, now(), now()),
       ('c', 'Hai2', 1, now(), now()),
       ('d', 'Hai3', 2, now(), now()),
       ('g', 'Hai4', 1, now(), now());

INSERT INTO role (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);

INSERT INTO comment (content, blog_id, user_id)
VALUES ('sucks', 2, 2),
       ('also sucks', 3, 2);