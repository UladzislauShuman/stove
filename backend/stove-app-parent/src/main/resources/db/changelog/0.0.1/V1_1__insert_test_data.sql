-- liquibase formatted sql

-- changeset your_name:data-insertion-1
-- preconditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM users;

-- 1. Пользователи
-- Пароль для всех 'password'
INSERT INTO users (id, full_name, email, phone_number, password_hash, user_role, created_at, updated_at) VALUES
(1, 'Vlad Shumpanov', 'sumanvlad36@gmail.com', '+375290000000', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'STOVEMAKER', NOW(), NOW()),
(2, 'Kostia Ivanov', 'lol228@gmail.com', '+375290000001', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'CUSTOMER', NOW(), NOW()),
(3, 'Test Customer', 'customer@example.com', '+375290000002', '$2a$10$ne3O9gJb5B/5L9.IIc20eOKAYiYqR6xNq1f5gE/h9aQRiG0jMh.4q', 'CUSTOMER', NOW(), NOW());

-- 2. Типы строений
INSERT INTO stove_types (id, name, description, base_price, image_url) VALUES
(1, 'Помпейская печь', 'Классическая дровяная печь для пиццы и выпечки.', 1001.00, 'https://pushkapech.ru/wp-content/uploads/2024/11/255c97cb-c291-4876-a921-0aa51ce24a24.jpg'),
(2, 'Барбекю из кирпича', 'Комплекс для приготовления еды на открытом воздухе.', 1002.00, 'https://rubeleco.by/wp-content/uploads/2023/10/0812ww-11.webp'),
(3, 'Камин', 'Облицованный кирпичом камин для уюта и тепла.', 1003.00, 'https://www.mastertile.by/wp-content/uploads/2021/01/izobrazhenie_viber_2020-12-03_15-18-39.jpg'),
(4, 'Кострище', 'Обустроенное место для разведения огня на участке.', 500.00, 'https://i.pinimg.com/736x/d5/10/98/d510983ad24ef3fb0ba9b79c301f45a9.jpg');

-- 3. Компоненты
INSERT INTO components (id, stove_type_id, name, description, is_required, allow_multiple_choices) VALUES
-- Помпейская печь (id=1)
(11, 1, 'Основание', 'На чем будет стоять ваша печь.', true, false),
(12, 1, 'Купол', 'Материал основной камеры горения.', true, false),
(13, 1, 'Утепление', 'Как долго печь будет держать тепло.', true, false),
(14, 1, 'Дымоход', 'Труба для отвода дыма.', true, false),
(15, 1, 'Внешняя отделка', 'Внешний вид вашей печи.', false, false),
-- Барбекю (id=2)
(21, 2, 'Основание', 'Фундамент и нижняя часть барбекю.', true, false),
(22, 2, 'Топочная зона', 'Где происходит горение дров/углей.', true, false),
(23, 2, 'Дымосборник', 'Конструкция над топкой для сбора дыма.', true, false),
(24, 2, 'Столешница', 'Рабочая поверхность.', false, false),
-- Камин (id=3)
(31, 3, 'Топка камина', 'Готовая чугунная или стальная вставка.', true, false),
(32, 3, 'Облицовочный кирпич', 'Основной материал для внешнего вида.', true, false),
(33, 3, 'Каминная полка', 'Декоративный элемент над топкой.', false, false),
-- Кострище (id=4)
(41, 4, 'Основание и чаша', 'Где будет гореть огонь.', true, false),
(42, 4, 'Внешняя облицовка', 'Чем будет обложен очаг.', true, false);

-- 4. Варианты компонентов
INSERT INTO component_options (id, component_id, name, price_modifier, image_url, is_default) VALUES
(101, 11, 'На кирпичном постаменте', 2500.00, '.../base_brick.jpg', true),
(102, 11, 'На металлическом каркасе', 3500.00, '.../base_metal.jpg', false),
(103, 12, 'Купол из шамотного кирпича', 1800.00, '.../dome_yellow.jpg', true),
(104, 12, 'Купол из красного огнеупора', 1500.00, '.../dome_red.jpg', false),
(105, 13, 'Утеплитель - керамоволокно', 800.00, '.../ins_ceramic.jpg', true),
(106, 13, 'Утеплитель - вермикулит', 600.00, '.../ins_vermiculite.jpg', false),
(107, 14, 'Дымоход кирпичный', 1200.00, '.../pipe_brick.jpg', true),
(108, 14, 'Дымоход "Сэндвич" (сталь)', 1500.00, '.../pipe_sandwich.jpg', false),
(109, 15, 'Штукатурка под покраску', 500.00, '.../face_plaster.jpg', true),
(110, 15, 'Отделка мозаикой', 2500.00, '.../face_mosaic.jpg', false),
(201, 22, 'Топка из шамотного кирпича', 900.00, '.../bbq_firebox.jpg', true),
(202, 23, 'Кирпичный дымосборник', 1300.00, '.../bbq_chimney.jpg', true),
(203, 24, 'Столешница из бетона', 1000.00, '.../bbq_concrete.jpg', true),
(204, 24, 'Столешница из гранита', 3000.00, '.../bbq_granite.jpg', false),
(301, 31, 'Чугунная топка "Везувий"', 4000.00, '.../fp_vezuviy.jpg', true),
(302, 31, 'Чугунная топка "Invicta"', 6000.00, '.../fp_invicta.jpg', false),
(303, 32, 'Кирпич облицовочный "Lode"', 2000.00, '.../fp_lode.jpg', true),
(304, 33, 'Деревянная каминная полка', 700.00, '.../fp_shelf_wood.jpg', true),
(401, 41, 'Бетонная чаша', 400.00, '.../pit_concrete.jpg', true),
(402, 42, 'Облицовка диким камнем', 800.00, '.../pit_stone.jpg', true);

-- 5. Дополнительные услуги
INSERT INTO addons (id, name, description, price) VALUES
(1, 'Установка решетки-гриль', 'Включает стоимость решетки и работ.', 150.00),
(2, 'Доставка материалов', 'Доставка всех материалов на участок.', 250.00),
(3, 'Выезд на замер', 'Консультация и замер на участке.', 50.00);

-- 6. Конфигурации
INSERT INTO configurations (id, stove_type_id, author_id, is_template, is_locked, name, created_at, updated_at) VALUES
(1, 1, 1, true, false, 'Классическая помпейская печь в беседке', NOW(), NOW()),
(2, 2, 2, false, false, 'Мое барбекю на дачу', NOW(), NOW()),
(3, 3, 3, false, true, 'Камин в гостиную', NOW(), NOW());

-- 7. Выборы для конфигураций
INSERT INTO configuration_choices (configuration_id, option_id) VALUES
-- Для Конфигурации 1 (Шаблон помпейской печи)
(1, 101), (1, 103), (1, 105), (1, 108),
-- Для Конфигурации 2 (Черновик барбекю)
(2, 201), (2, 202), (2, 204),
-- Для Конфигурации 3 (Камин для заказа)
(3, 302), (3, 303), (3, 304);

-- 8. Доп. услуги для конфигураций
INSERT INTO configuration_addons (configuration_id, addon_id) VALUES
(2, 1), -- К барбекю добавили решетку
(3, 2); -- К камину заказали доставку

-- 9. Заказы
INSERT INTO orders (id, user_id, configuration_id, status, customer_name, customer_phone, final_price, created_at, updated_at) VALUES
(1, 3, 3, 'COMPLETED', 'Тестовый Покупатель', '+375290000002', 9753.00, NOW(), NOW());

-- 10. Портфолио
INSERT INTO portfolio_items (id, configuration_id, title, description, main_image_url, completion_date) VALUES
(1, 1, 'Помпейская печь в итальянском стиле', 'Построена в августе 2024 года...', '.../portfolio_1.jpg', '2024-08-15');

-- 11. Избранное
INSERT INTO favorites (user_id, configuration_id, added_at) VALUES
(2, 1, NOW());

-- Обновляем сиквенсы, чтобы новые записи не конфликтовали с заданными вручную ID
-- Это ВАЖНО для PostgreSQL
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users), true);
SELECT setval('stove_types_id_seq', (SELECT MAX(id) FROM stove_types), true);
SELECT setval('components_id_seq', (SELECT MAX(id) FROM components), true);
SELECT setval('component_options_id_seq', (SELECT MAX(id) FROM component_options), true);
SELECT setval('addons_id_seq', (SELECT MAX(id) FROM addons), true);
SELECT setval('configurations_id_seq', (SELECT MAX(id) FROM configurations), true);
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders), true);
SELECT setval('portfolio_items_id_seq', (SELECT MAX(id) FROM portfolio_items), true);