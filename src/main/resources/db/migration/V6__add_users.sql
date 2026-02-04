create table users
(
    id         uuid primary key,
    project_id uuid         not null references projects (id),
    email      varchar(255) not null,
    password   varchar(255) not null,
    enabled    boolean      not null default true,
    created_at timestamptz  not null
);

create unique index ux_users_project_email
    on users (project_id, email);