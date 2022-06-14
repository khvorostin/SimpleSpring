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
(кросс-платформенную IDE для СУБД и SQL), создал таблицу продуктов:

```roomsql
CREATE TABLE IF NOT EXISTS products (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    price DOUBLE NOT NULL
);
```

Первичный ключ (_id_) здесь не как BIGINT (что было бы логично, так как на уровне 
Java мы оперируем лонгами). Это обусловлено спецификой СУБД:

```shell
[SQLITE_ERROR] SQL error or missing database (AUTOINCREMENT is only allowed on an INTEGER PRIMARY KEY)
```

После того, как таблица была создана, заполнил её данными:

```roomsql
INSERT INTO products (id, title, price) VALUES
  (1, 'Rapid', 1602000.00),
  (2, 'Octavia', 2613000.00),
  (3, 'Kodiaq', 3364000.00),
  (4, 'Superb', 3386000.00),
  (5, 'Karoq', 2716000.00);
```

Возможно (этот момент нужно уточнить), при работе с БД через Hibernate автоинкрементные ключи
и не нужны. Если это так, то я изменю структуру таблицы (уберу автоинкрементное поле и задам
первичному ключу тип BIGINT).