create table events (
    id bigserial primary key,
    user_id bigint references users(id),
    post_id bigint references posts(id),
    created timestamp
);