create table if not exists author_entity
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists category_entity
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table  if not exists delivery_entity
(
    id     bigint auto_increment
        primary key,
    city   varchar(255) null,
    flat   varchar(255) null,
    house  varchar(255) null,
    region varchar(255) null,
    street varchar(255) null,
    tel    varchar(255) null,
    zip    varchar(255) null
);

create table  if not exists publisher_entity
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table  if not exists book_entity
(
    id           bigint auto_increment
        primary key,
    image        longtext     null,
    name         varchar(255) null,
    price        double       not null,
    publish_year int          null,
    publisher_id bigint       null,
    constraint FKbxob2ku1fl0q5sxp5n1h1dswn
        foreign key (publisher_id) references publisher_entity (id)
);

create table  if not exists book_entity_authors
(
    book_entity_id bigint not null,
    authors_id     bigint not null,
    constraint FKlbiokaxometja0e50hd4m3tib
        foreign key (book_entity_id) references book_entity (id),
    constraint FKle8rxdux9o2denf8dghuojgcc
        foreign key (authors_id) references author_entity (id)
);

create table  if not exists book_entity_categories
(
    book_entity_id bigint not null,
    categories_id  bigint not null,
    constraint FKp8w1n9k4tnsj7r8eysu7aappq
        foreign key (book_entity_id) references book_entity (id),
    constraint FKr2n71nfs1rcx5bmf48jiai6qo
        foreign key (categories_id) references category_entity (id)
);

create table  if not exists book_in_bucket_entity
(
    id         bigint auto_increment
        primary key,
    book_count int    null,
    book_id    bigint null,
    constraint FKgtmn1ky98mqdt97a3n7naqflv
        foreign key (book_id) references book_entity (id)
);

create table  if not exists user_entity
(
    id                   bigint auto_increment
        primary key,
    email                varchar(255)                                       null,
    firebase_identity_id varchar(255)                                       null,
    role                 enum ('ROLE_ADMIN', 'ROLE_ANONYMOUS', 'ROLE_USER') null,
    constraint UK_8421cd5c5xopiylsccblb0r93
        unique (firebase_identity_id)
);

create table  if not exists bucket_entity
(
    id             bigint auto_increment
        primary key,
    state          enum ('IN_PROCESSING', 'OPEN', 'RESOLVED', 'SUBMITTED') null,
    delivery_id    bigint                                                  null,
    user_entity_id bigint                                                  null,
    constraint UK_trqjpxk37c3urkaie88g2ouo6
        unique (delivery_id),
    constraint FK7vg23m3bv75qratxbu0ph4aur
        foreign key (delivery_id) references delivery_entity (id),
    constraint FK8nirtgegwhh9gousyudngqn5x
        foreign key (user_entity_id) references user_entity (id)
);

create table  if not exists bucket_entity_books
(
    bucket_entity_id bigint not null,
    books_id         bigint not null,
    constraint FKnq51q1jlcqxihox9m63k3gxa8
        foreign key (books_id) references book_in_bucket_entity (id),
    constraint FKsskwlwfslijkcaa4grkl1d7o5
        foreign key (bucket_entity_id) references bucket_entity (id)
);

create table  if not exists user_entity_buckets
(
    user_entity_id bigint not null,
    buckets_id     bigint not null,
    constraint UK_pegnr4ocy9r0042p9aywbe4y1
        unique (buckets_id),
    constraint FKqxhr5lgq7t78dhtihi1qsm7mm
        foreign key (user_entity_id) references user_entity (id),
    constraint FKt1nv3wdg0kpvfu1qx6ycqo3vk
        foreign key (buckets_id) references bucket_entity (id)
);

