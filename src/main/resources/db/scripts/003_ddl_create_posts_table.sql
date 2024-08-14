create table posts (
    id bigserial primary key,
    title varchar not null,
    text varchar,
    created timestamp,
    user_id bigint references users(id)
);