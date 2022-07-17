CREATE TABLE products (
    id bigserial PRIMARY KEY,
    title varchar(255),
    price int
);

INSERT INTO products (title, price) VALUES
    ('Подставка под сис. блок Buro BU-CS3AL светло-серый', 1405),
    ('Подставка для клавиатуры Buro KB002W светло-серый подстольная, универсальная', 1290),
    ('Подставка для клавиатуры Buro KB002B черный подстольная, универсальная', 1290),
    ('Замок врезной для тумбы 138/22 хром', 150),
    ('Замок висячий Apecs PD-01-32-Blister', 85),
    ('Заглушка для проводки D60, черная 101995', 50),
    ('Заглушка для проводки D60, бежевая 101997', 50),
    ('Коврик напольный д/паркета, ламината 126020RR D-60 см, круглый/прозрачный поликарбонат', 2615),
    ('Подставка для ног Fellowes FS-48121 Standard черный', 2050),
    ('Подставка для ног "Refresh" FS-80660 вентилируемая (2 позиции), черный', 2480),
    ('Подставка под сист.блок Buro BU-CS2AB черный', 970),
    ('Подставка для ног Fellowes Microban с антибакт. покрытием, пластик черный, FS-8035001', 3610),
    ('Подставка для ног WILLIE (F6018) черный 450*330*85', 2220),
    ('Подставка под сист.блок Buro BU-CS3BL черный', 1405),
    ('Подставка для ног MORGAN (F6032) черный 434*330*105', 1725),
    ('Заглушка для проводки D60, коричневая 101996', 50),
    ('Заглушка для проводки D60, белая 101994', 50),
    ('Заглушка для проводки D60, серая 110547', 50),
    ('Подставка под монитор Fellowes FS-80201 черный/серый', 2590),
    ('Опора брифинга Талант 444, d -110*70', 1700);

CREATE TABLE roles (
    id bigserial PRIMARY KEY,
    name varchar(50) not null
);

INSERT INTO roles (id, name) VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN'),
    (3, 'ADD_PRODUCT'),
    (4, 'EDIT_PRODUCT'),
    (5, 'DROP_PRODUCT'),
    (6, 'VIEW_CART');

CREATE TABLE users (
    id bigserial PRIMARY KEY,
    username varchar(30) not null unique,
    password varchar(100) not null,
    email varchar(100) unique
);

INSERT INTO users (id, username, password, email) VALUES
    (1, 'admin', '$2a$12$N98yw2Zl4FaiyjsfIFKRVurvc1r.2XxbhEm4V8v3/PN0xqLbq5z4W', 'admin@test.com'), -- admin/admin
    (2, 'user', '$2a$12$Ig.DRE5DEEVKMHqVL2ouwuQIUIFtiYaQsyp.R/pPgosMv5rR/QRUi', 'user@test.com'); -- user/user

CREATE TABLE users_to_roles (
    user_id bigint not null,
    role_id bigint not null,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users_to_roles (user_id, role_id) VALUES
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 1),
    (2, 6);
