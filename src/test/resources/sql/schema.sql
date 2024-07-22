DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS banks CASCADE;

CREATE TABLE users
(
    id        bigserial PRIMARY KEY,
    name      varchar(128) NOT NULL,
    last_name varchar(128) NOT NULL
);


CREATE TABLE banks
(
    id          bigserial PRIMARY KEY,
    title        varchar(128)  NOT NULL UNIQUE
);

CREATE TABLE accounts
(
    id       bigserial PRIMARY KEY,
    number     varchar(15) NOT NULL,
    cash   decimal(7,2) NOT NULL DEFAULT 0.00,
    user_id  bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    bank_id bigint NOT NULL REFERENCES banks(id) ON DELETE CASCADE
);