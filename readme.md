# Учебный проект по Spring

## Хранилище данных

### Выбор SQLite в качестве СУБД

В качестве хранилища данных используется _[SQLite](https://www.sqlite.org/index.html)_.

Большое преимущество SQLite перед другими СУБД — возможность включить файл
с базой данных в сам проект. Для сравнения, при использовании H2 при вроде 
бы нескольких вариантах выбора разворачивания БД, пользователь всё-таки
ограничен в своих возможностях:

* ```jdbc:h2:~/test``` — _встроенная_ (embedded) БД, которая будет храниться 
в _домашней_ директории пользователя,
* ```jdbc:h2:tcp://localhost/~/test``` — удалённая БД, требующая запущенного
сервера на текущем компьютере,
* ```jdbc:h2:mem:test``` — _хранящаяся в памяти_ БД, которая не позволяет
сохранить данные между запусками (т.е. не персистентна).

Между тем, в настоящее время диалект SQLite не поддерживается Hibernate
(в проекте используется версия 6.1), но есть поддержка пользовательских диалектов,
поэтому среди зависимостей есть такая:

```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-community-dialects</artifactId>
    <version>6.1.0.Final</version>
</dependency>
```

— она позволяет определить диалект для работы с SQLite:

```shell
org.hibernate.community.dialect.SQLiteDialect
```

### Разворачивание учебной базы данных

При помощи консольного приложения была создана база данных _products.db_:

```shell
.open products.db
```

Далее подключился к этой БД через [DataGrip](https://www.jetbrains.com/datagrip/) 
(кросс-платформенную IDE для СУБД и SQL), создал таблицы продуктов, пользователей 
и заказов:

```roomsql
CREATE TABLE IF NOT EXISTS products (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    price DOUBLE NOT NULL
);


CREATE TABLE IF NOT EXISTS users (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL DEFAULT 0,
    product_id INTEGER NOT NULL DEFAULT 0,
    date TEXT NOT NULL, -- в SQLite нет типа "дата"
    num INTEGER NOT NULL DEFAULT 0,
    price DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
```

Первичный ключ (_id_) здесь не как BIGINT (что было бы логично, так как на уровне 
Java мы оперируем лонгами). Это обусловлено спецификой СУБД:

```shell
[SQLITE_ERROR] SQL error or missing database (AUTOINCREMENT is only allowed on an INTEGER PRIMARY KEY)
```

После того как таблицы были созданы, заполнил их данными:

```roomsql
INSERT INTO products (id, title, price) VALUES
  (1, 'Rapid', 1602000.00),
  (2, 'Octavia', 2613000.00),
  (3, 'Kodiaq', 3364000.00),
  (4, 'Superb', 3386000.00),
  (5, 'Karoq', 2716000.00);
  
INSERT INTO users (id, name) VALUES
  (1, 'Рольф'),
  (2, 'Вагнер'),
  (3, 'АЦ Авто'),
  (4, 'Сигма'),
  (5, 'Сигма'),
  (6, 'Автополе'),
  (7, 'Максимум Авто');

INSERT INTO orders (user_id, product_id, date, num, price) VALUES
  (1, 1, '2021-11-15 12:40:13', 40, 1302000.00),
  (1, 2, '2021-11-15 12:40:13', 10, 2613000.00),
  (1, 3, '2021-11-15 12:40:13', 20, 3200000.00),
  (2, 1, '2021-11-18 16:34:08', 10, 1352000.00),
  (2, 2, '2021-11-18 16:34:08', 4, 2613000.00),
  (2, 3, '2021-11-18 16:34:08', 5, 3200000.00),
  (3, 1, '2021-11-21 15:23:45', 20, 1368000.00),
  (3, 2, '2021-11-21 15:23:45', 7, 2613000.00),
  (3, 3, '2021-11-21 15:23:45', 30, 3200000.00),
  (4, 1, '2021-12-02 10:13:04', 15, 1502000.00),
  (4, 2, '2021-12-02 10:13:04', 15, 2613000.00),
  (4, 3, '2021-12-02 10:13:04', 15, 3200000.00),
  (5, 1, '2021-12-10 10:07:02', 10, 1502000.00),
  (5, 2, '2021-12-10 10:07:02', 2, 2613000.00),
  (5, 3, '2021-12-10 10:07:02', 8, 3200000.00),
  (6, 1, '2022-01-10 13:47:00', 15, 1502000.00),
  (6, 2, '2022-01-10 13:47:00', 10, 2613000.00),
  (6, 5, '2022-01-10 13:47:00', 5, 2716000.00),
  (7, 1, '2022-02-05 17:00:01', 40, 1502000.00),
  (7, 2, '2022-02-05 17:00:01', 10, 2613000.00),
  (7, 3, '2022-02-05 17:00:01', 20, 3200000.00);
```

В роли магазина выступает завод, в роли покупателей — автосалоны. Заказы, число
машин, цена — вымышленные, возможные совпадения — случайны. 

Возможно (этот момент нужно уточнить), при работе с БД через Hibernate автоинкрементные ключи
и не нужны. Если это так, то я изменю структуру таблицы (уберу автоинкрементное поле и задам
первичному ключу тип BIGINT).