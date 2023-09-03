CREATE TABLE app_user_auth (
     id bigserial PRIMARY KEY,
     username VARCHAR(50) NOT NULL,
     hashed_password VARCHAR(128) NOT NULL
);

