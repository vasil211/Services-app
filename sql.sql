drop database if exists services;
create database services;
use services;


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(20) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    phone VARCHAR(13) NOT NULL,
    last_online DATETIME default now(),
    modified DATETIME default now(),
    created DATETIME default now()
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    info VARCHAR(250) NOT NULL,
    deleted boolean default false,
    deleted_reason varchar(250),
    moderated boolean default false,
    created DATETIME default now(),
    modified DATETIME default now(),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_provider_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    message VARCHAR(250) NOT NULL,
    sent DATETIME NOT NULL DEFAULT NOW(),
    CONSTRAINT FOREIGN KEY (service_provider_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (post_id)
        REFERENCES posts (id)
);

CREATE TABLE ratings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_provider_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    rating FLOAT NOT NULL,
    comment VARCHAR(250),
	deleted boolean default false,
    deleted_reasonx varchar(250),
    moderated boolean default false,
    created DATETIME NOT NULL default now(),
    modified DATETIME  default now(),
    CONSTRAINT FOREIGN KEY (service_provider_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (post_id)
        REFERENCES posts (id)
);

create table appointments(
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_provider_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    address varchar(200) not null,
    state varchar(20) not null,
    decline_comment varchar(250),
	created DATETIME NOT NULL default now(),
    modified DATETIME  default now(),
    CONSTRAINT FOREIGN KEY (service_provider_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT FOREIGN KEY (post_id)
        REFERENCES posts (id)
); 

insert into users(role, user_name, password, email, first_name, last_name, phone,created)
values("ADMIN", "admin", "4d1ace77931dbb1208607eb3ab594086", "admin@abv.bg", "AdminFirstName", "AdminLastName", "+359876547826", now()),
("MODERATOR", "moderator", "4d1ace77931dbb1208607eb3ab594086", "moderator@abv.bg", "ModeratorFirstName", "ModeratorLastName", "+359876547826", now()),
("SERVICE_PROVIDER", "serviceProvider", "4d1ace77931dbb1208607eb3ab594086", "provider@abv.bg", "ProviderFirstName", "ProviderLastName", "+359876547826", now()),
("USER", "admin", "4d1ace77931dbb1208607eb3ab594086", "user@abv.bg", "UserFirstName", "UserLastName", "+359876547826", now());

insert into categories(name)
values("Plumbing"),
("Electrical"),
("Construction");

insert into posts(user_id, category_id, name, info, created)
values(3, 1, "General Plumber", "Hi! im Ivan Ivanov and im a great blumber.", now()),
(3, 2, "Electrician", "Hi! im Ivan Ivanov and im a great electrition. I can help with your electrical problems", now());

insert into messages(sender_id, service_provider_id, user_id, post_id, message, sent) 
values( 4, 3, 4, 2, "Hello! i need help", now()),
(3, 3, 4, 2, "Hello! How i can help", now()),
(4, 3, 4, 2, "My toilet is cloged, and i cant fix it", now()),
(3, 3, 4, 2, "yes of course, my rate is 50$ an hour, im free now", now()),
(4, 3, 4, 2, "ok, my adress is ............. im waiting", now());

insert into ratings(service_provider_id, user_id, post_id, rating, comment , created)
values(3, 4, 2, 5.0, "Exelent work! Came rly fast", now());
insert into ratings(service_provider_id, user_id, post_id, rating, comment , created)
values(3, 4, 2, 4.0, "Good", now());


insert into appointments(service_provider_id, user_id, post_id, address, state)
values(3, 4, 2, "studentski grad, blok 4, ap.64", "FINISHED");
