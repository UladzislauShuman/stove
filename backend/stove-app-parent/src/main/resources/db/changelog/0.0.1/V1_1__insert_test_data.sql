-- liquibase formatted sql

-- changeset your_name:4
-- Пароль 'password' для всех
INSERT INTO users (id, full_name, email, phone_number, password_hash, user_role, created_at, updated_at) VALUES
(1, 'Vlad Shumpanov', 'sumanvlad36@gmail.com', '+375290000000', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'CRAFTSMAN', NOW(), NOW()),
(2, 'Kostia Ivanov', 'lol228@gmail.com', '+375290000001', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'CUSTOMER', NOW(), NOW()),
(3, 'Test Customer', 'customer@example.com', '+375290000002', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'CUSTOMER', NOW(), NOW());

INSERT INTO stove_types (id, name, description, base_price, image_url) VALUES
(1, 'Помпейская печь', 'Классическая дровяная печь для пиццы и выпечки.', 1001.00, 'https://pushkapech.ru/wp-content/uploads/2024/11/255c97cb-c291-4876-a921-0aa51ce24a24.jpg'),
(2, 'Барбекю из кирпича', 'Комплекс для приготовления еды на открытом воздухе.', 1002.00, 'https://rubeleco.by/wp-content/uploads/2023/10/0812ww-11.webp');

INSERT INTO components (id, stove_type_id, name, description) VALUES
(11, 1, 'Основание', 'На чем будет стоять ваша печь.'),
(12, 1, 'Купол', 'Материал основной камеры горения.'),
(21, 2, 'Основание', 'Фундамент и нижняя часть барбекю.'),
(22, 2, 'Топочная зона', 'Где происходит горение дров/углей.');

INSERT INTO component_options (id, component_id, name, price_modifier, is_default) VALUES
(101, 11, 'На кирпичном постаменте', 2500.00, true),
(102, 11, 'На металлическом каркасе', 3500.00, false),
(103, 12, 'Купол из шамотного кирпича', 1800.00, true);

INSERT INTO addons (id, name, description, price) VALUES
(1, 'Решетка-гриль', 'Включает стоимость решетки и работ.', 150.00),
(2, 'Доставка материалов', 'Доставка всех материалов на участок.', 250.00);

-- Обновляем сиквенсы, чтобы новые записи не конфликтовали с заданными вручную ID
-- Это ВАЖНО для PostgreSQL
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('stove_types_id_seq', (SELECT MAX(id) FROM stove_types));
SELECT setval('components_id_seq', (SELECT MAX(id) FROM components));
SELECT setval('component_options_id_seq', (SELECT MAX(id) FROM component_options));
SELECT setval('addons_id_seq', (SELECT MAX(id) FROM addons));