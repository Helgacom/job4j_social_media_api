create table messages (
    id bigserial primary key,
    text varchar,
    created timestamp,
    boolean accept,
    user_from_id bigint references users(id),
    user_to_id bigint references users(id)
);