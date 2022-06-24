create table if not exists temp_user
(
    github_id   BIGINT not null
        primary key,
    profile_uri VARCHAR(255) not null
);

create table if not exists user
(
    user_id     BIGINT       auto_increment
        PRIMARY KEY,
    github_login_id VARCHAR(50), # alter table user add column github_login_id varchar(50);
    email       VARCHAR(50)  not null,
    github_id   BIGINT       not null,
    name        VARCHAR(255) not null,
    profile_uri VARCHAR(255) not null,
    role        VARCHAR(50)  not null
);

create table if not exists company
(
    company_id           BIGINT auto_increment
        PRIMARY KEY,
    company_image_uri    TEXT, /** image uri는 매우 길어질  수 있다. **/
    company_homepage_uri VARCHAR(255)   not null,
    company_location     VARCHAR(65)    not null,
    company_name         VARCHAR(65)    not null
);

create table if not exists worker
(
    worker_id      BIGINT auto_increment
        PRIMARY KEY,
    dev_year       INTEGER,
    give_link      VARCHAR(255),
    introduction   VARCHAR(30),
    position       VARCHAR(15),
    company_id     BIGINT not null,
    user_id        BIGINT not null,

    constraint dev_year_rang
        CHECK ( dev_year BETWEEN 0 AND 50 ),
    constraint fk_company
        FOREIGN KEY (company_id) references company (company_id),
    constraint fk_user
        FOREIGN KEY (user_id) references user (user_id)
            ON DELETE CASCADE
);