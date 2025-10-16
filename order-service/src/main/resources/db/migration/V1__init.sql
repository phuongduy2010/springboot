create extension if not exists "uuid-ossp";

create table if not exists orders(
     id uuid primary key,
     customer_id uuid not null,
     status varchar(40) not null,
     created_at timestamptz not null,
     version bigint not null default 0
);

create table if not exists order_items(
    id bigserial primary key,
    order_id uuid not null references orders(id) on delete cascade,
    product_id text not null,
    quantity int not null,
    unit_price numeric(18,2) not null default 0
);

create table if not exists outbox(
    id bigserial primary key,
    aggregate_type text not null,
    aggregate_id uuid,
    event_type text not null,
    payload jsonb not null,
    occurred_at timestamptz not null,
    processed boolean not null default false,
    dedup_key text not null,
    version bigint not null default 0
);
create unique index if not exists uq_outbox_dedup on outbox(dedup_key);
create index if not exists ix_outbox_processed_time on outbox(processed, occurred_at);