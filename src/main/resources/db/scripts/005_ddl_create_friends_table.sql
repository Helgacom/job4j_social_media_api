create table friends (
    id bigserial primary key,
    status boolean,
    user_offer_id bigint references users(id),
    user_accept_id bigint references users(id)
);