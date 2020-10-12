create table "Customer" (
    id integer not null primary key,
    name varchar(255) not null,
    email_address varchar(255) not null
);

create table "Order" (
    id integer not null primary key,
    name varchar(255) not null,
    price integer not null,
    customer_id integer not null references "Customer"(id)
);
