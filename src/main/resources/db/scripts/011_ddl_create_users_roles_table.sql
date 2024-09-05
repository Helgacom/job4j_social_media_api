CREATE TABLE users_roles
(
    role_id INT REFERENCES roles(id),
    user_id BIGINT REFERENCES users(id)
);