CREATE TABLE categories
(
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

CREATE TABLE products (
    id          bigserial primary key,
    title       varchar(255),
    price       numeric (8, 2),
    category_id     bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

CREATE TABLE orders (
    id          bigserial primary key,
    username    varchar(255),
    total_price numeric (8, 2) not null,
    address     varchar(255),
    phone       varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE order_items (
    id                  bigserial primary key,
    product_id          bigint not null references products (id),
    order_id            bigint not null references orders (id),
    quantity            int not null,
    price_per_product   numeric (8, 2) not null,
    price               numeric (8,2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO categories (title) VALUES
('Еда'),
('Канцтовары');

INSERT INTO products (title, price, category_id) VALUES
('Хлеб', 46.99, 1),
('Лаваш', 55.99, 1),
('Штрудель', 42.90, 1),
('Баранки', 69.30, 1),
('Молоко', 120.00, 1),
('Сливки', 199.00, 1),
('Йогурт', 132.00, 1),
('Творог', 111.99, 1),
('Сырок глазированный', 56.99, 1),
('Кефир', 89.00, 1),
('Ряженка', 63.99, 1),
('Айран', 46.99, 1),
('Масло сливочное', 178.99, 1),
('Маргарин', 94.99, 1),
('Сметана', 78.99, 1),
('Яйцо куриное 10шт', 109.99, 1),
('Сахар', 253.00, 1),
('Мука', 119.80, 1),
('Сыр', 529.99, 1),
('Ручка', 89.90, 2),
('Ластик', 63.50, 2),
('Корректор', 234.00, 2),
('Цветные карандаши 12шт.', 429.00, 2),
('Карандаш', 23.00, 2),
('Пенал', 359.99, 2),
('Клей-карандаш', 59.99, 2),
('Маркеры', 219.00, 2),
('Циркуль', 349.00, 2),
('Ножницы', 399.00, 2),
('Дырокол', 169.99, 2),
('Точилка', 59.99, 2),
('Скрепки', 39.99, 2),
('Нож канцелярский', 24.99, 2),
('Краски акварельные', 249.00, 2),
('Тетрадь 24л. клетка', 26.90, 2),
('Тетрадь 24л. линия', 26.90, 2),
('Линейка 15см', 124.90, 2);