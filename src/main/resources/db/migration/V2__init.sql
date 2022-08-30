
-- Диалект PostgreSQL еще не родной для меня, поэтому ниже нет очевидных ограничений на данные. Некоторые решения
-- неоптимальны именно из-за незнания нюансов использования именно этой СУБД. Литература закуплена, работаю над этим.
--
-- Основные сущности (в первом приближении) расписаны ниже. Как отдельные сущности в проекте я их пока не разворачивал.
--
-- Категории товаров. Предполагается, что возможна древовидная структура категорий (одна категория может быть
-- родительской для нескольких других, те, в свою очередь, родительскими для других таблиц.
--
-- Здесь и далее вводятся технические поля created_at и updated_at в которых хранятся временные метки создания
-- и изменения записи (соответственно). Временные метки везде храню как int: потолок здесь 2147483647, что соответствует
-- Tue Jan 19 2038 03:14:07 GMT+0000 (т.е. 15 лет запаса).

create table if not exists categories
(
	id bigserial constraint categories_pk primary key,
	parent_category_id bigint null default null,
	title varchar(255) not null,
	created_at int,
	updated_at int
);

comment on table categories is 'Категории товаров';

create unique index categories_title_uindex on categories (title);

-- Производители товаров. Базовый набор полей: название компании, описание + ссылка на файл с логотипом

create table if not exists manufacturers
(
    id bigserial constraint manufacturers_id primary key,
    title varchar(255) not null,
    description text,
    logo_path varchar(100) null default null,
	created_at int,
	updated_at int
);

comment on table manufacturers is 'Производители товаров';

create unique index manufacturers_title_uindex on manufacturers (title);

-- Товары. Здесь только название (title) и описание (description) товаров, а также ссылку на категорию.
-- Все три поля обязательны к заполнению. Цены, ссылки на фотографии вынес в отдельные таблицы (см. ниже) - цены зависят
-- от даты (можно создать поле с текущей ценой здесь и отдельной рутиной актуализировать это поле раз в день, скажем),
-- в текущем виде максимально гибко можно управлять ценой.
--
-- Изображения вынесены в отдельную таблицу, так как между товаром и изображениями связь один-ко-многим.
--
-- В поле score хранится средняя оценка товара пользователями. Рассчитывается серверной рутиной
-- раз в день на основе отзывов пользователей (см. ниже).

create table if not exists goods
(
	id bigserial constraint categories_pk primary key,
	category_id bigint not null,
	manufacturer_id bigint not null,
	title varchar(255) not null,
	description text not null,
	score decimal(3,2) null default null,
	created_at int,
	updated_at int
);

comment on table goods is 'Товары';

create unique index goods_title_uindex on goods (title);

-- Цены на товары. Так как цена на товары меняется и при этом важно хранить историю изменения цены (на случай
-- условных налоговых проверок), цены вынесены в отдельную таблицу. Здесь есть два поля, в которых фиксируются временные
-- метки начала и завершения действия цен. Если верхняя граница не задана, считаем, что цена доступна сейчас.
--
-- Сами цены хранятся как инты (чтобы избежать проблем при умножении/делении чисел с плавающей точкой). Трюк здесь такой:
-- умножаем для хранения цену на 100, а при отображении, делим на 100.

create table if not exists prices
(
	id bigserial constraint categories_pk primary key,
    good_id bigint not null,
	start_tstmp int not null,
	finish_tstmp int,
	price_multiplied_by_100 int not null,
	created_at int,
	updated_at int
);

comment on table prices is 'Цены на товары';

-- Параметры товаров в категориях. Все товары, относящиеся к одной категории, имеют одинаковые параметры (причем
-- часть параметров могут вынесены в родительскую категорию). Здесь хранятся названия параметров (свойств) товаров
-- (габариты, цвет, тип экрана) и очередность выдачи параметров на странице товара.

create table if not exists category_params
(
    id bigserial constraint category_params_pk primary key,
    category_id bigint not null,
    rank int not null,
    param_name varchar,
	created_at int,
	updated_at int
);

comment on table category_params is 'Параметры таваров в категории';

-- Изображение товаров. Сами картинки хранятся на сервере, здесь указаны пути к изображениям трех разных размерах.

create table if not exists goods_images
(
    id bigserial constraint goods_images_pk primary key,
    good_id bigint not null,
    small_img_path varchar not null,
    mid_img_path varchar not null,
    big_img_path varchar not null,
	created_at int,
	updated_at int
);

comment on table goods_images is 'Изображения товаров';

-- Роли пользователей. Нет смысла хранить пользователей и сотрудников магазина отдельно, доступ к тем или иным возможностям
-- магазина разруливается ролями. Здесь пока что только заготовка таблицы, возможно, имеет смысл здесь создать матрицу
-- допуска (набор полей

create table if not exists roles
(
    id bigserial constraint roles_pk primary key,
    parent_role_id bigint null default null,
    title varchar not null,
	created_at int,
	updated_at int
);

comment on table roles is 'Роли пользователей';

insert into roles (id, parent_role_id, title, created_at, updated_at) values
  (1, null, 'Сотрудник магазина', 1661100168, 1661100168),
  (2, null, 'Покупатель', 1661100168, 1661100168),
  (3, 1, 'Менеджер', 1661100168, 1661100168),
  (4, 1, 'Линейный сотрудник', 1661100168, 1661100168),
  (5, 3, 'Супервайзер', 1661100168, 1661100168),
  (6, 3, 'Маркетолог', 1661100168, 1661100168),
  (7, 4, 'Кладовщик', 1661100168, 1661100168),
  (8, 4, 'Менеджер по закупкам', 1661100168, 1661100168),
  (9, 4, 'Сборщик заказов', 1661100168, 1661100168),
  (10, 4, 'Курьер', 1661100168, 1661100168),
  (12, 1, 'Бухгалтер', 1661100168, 1661100168);

-- Пользователи (покупатели и сотрудники магазина). В качестве логина используется электронный адрес (уникальное поле).
-- В поле password хранится хэш (пароли в чистом виде здесь не хранятся).
-- Пользователь может быть активен/неактивен (например, заблокирован).

create table if not exists users
(
	id bigserial constraint users_pk primary key,
	role_id bigint not null,
	email varchar not null,
	password varchar not null,
	firstname varchar not null,
	lastname varchar not null,
	active boolean default false,
	comment text null default null,
	created_at int,
	updated_at int
);

comment on table users is 'Пользователи';

create unique index users_email_uindex on users (email);

-- Отзывы пользователей на товары. Один пользователь может оставить только один отзыв на конкретный товар
-- и поставить ему оценку.

create table if not exists goods_reviews
(
    id bigserial constraint goods_reviews_pk primary key,
    good_id bigint not null,
    user_id bigint not null,
    score smallint not null, -- от 1 до 5
    review text,
	created_at int,
	updated_at int
);

comment on table goods_reviews is 'Оценки товаров пользователями';

create unique index goods_reviews_good_id_user_id_uindex on goods_reviews (good_id, user_id);

