create table friendship (
    first_user_id uuid not null,
    second_user_id uuid not null,
    is_accepted_by_first_user boolean not null,
    is_accepted_by_second_user boolean not null,
    primary key (first_user_id, second_user_id)
);

create table post (
    id uuid not null,
    body varchar(280) not null,
    date_published timestamp not null,
    user_id uuid,
    primary key (id)
);

create table users (
    id uuid not null,
    city varchar(128) not null,
    date_of_birth date not null,
    email varchar(128) not null,
    first_name varchar(128) not null,
    instagram varchar(128),
    interests varchar(250),
    last_name varchar(128) not null,
    password varchar(32) not null,
    sex varchar(255) not null,
    website varchar(128),
    primary key (id)
);

alter table if exists users
    add constraint email_unique
        unique (email);

alter table if exists friendship
    add constraint friendship_first_user_fk
        foreign key (first_user_id) references users;

alter table if exists friendship
    add constraint friendship_second_user_fk
        foreign key (second_user_id) references users;

alter table if exists post
    add constraint post_user_fk
        foreign key (user_id) references users;