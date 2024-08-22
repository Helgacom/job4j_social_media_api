create table subscribes (
    id bigserial primary key,
    user_subscriber_id bigint references users(id),
    user_to_id bigint references users(id)
);