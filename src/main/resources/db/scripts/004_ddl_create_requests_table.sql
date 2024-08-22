create table requests (
    id bigserial primary key,
    created timestamp,
    accept boolean default false,
    user_from_id bigint references users(id),
    user_to_id bigint references users(id)
);