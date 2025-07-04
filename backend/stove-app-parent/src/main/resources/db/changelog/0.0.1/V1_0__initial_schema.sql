-- liquibase formatted sql

-- changeset your_name:1
-- preConditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_type WHERE typname = 'user_role';
CREATE TYPE user_role AS ENUM ('CUSTOMER', 'CRAFTSMAN');

-- changeset your_name:2
-- preConditions onFail:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_type WHERE typname = 'order_status';
CREATE TYPE order_status AS ENUM ('DRAFT', 'PLACED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');

-- changeset your_name:3
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_role user_role NOT NULL DEFAULT 'CUSTOMER',
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- changeset your_name:20250704-4 runOnChange:false
-- Создаем все остальные таблицы
CREATE TABLE stove_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    base_price DECIMAL(10, 2) NOT NULL DEFAULT 0,
    image_url VARCHAR(255)
);

CREATE TABLE components (
    id BIGSERIAL PRIMARY KEY,
    stove_type_id BIGINT NOT NULL REFERENCES stove_types(id),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_required BOOLEAN NOT NULL DEFAULT true,
    allow_multiple_choices BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE component_options (
    id BIGSERIAL PRIMARY KEY,
    component_id BIGINT NOT NULL REFERENCES components(id),
    name VARCHAR(255) NOT NULL,
    price_modifier DECIMAL(10, 2) NOT NULL DEFAULT 0,
    image_url VARCHAR(255),
    is_default BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE configurations (
    id BIGSERIAL PRIMARY KEY,
    stove_type_id BIGINT NOT NULL REFERENCES stove_types(id),
    author_id BIGINT REFERENCES users(id),
    name VARCHAR(255),
    is_template BOOLEAN NOT NULL DEFAULT false,
    is_locked BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE configuration_choices (
    configuration_id BIGINT NOT NULL REFERENCES configurations(id) ON DELETE CASCADE,
    option_id BIGINT NOT NULL REFERENCES component_options(id),
    quantity BIGINT,
    PRIMARY KEY (configuration_id, option_id)
);

CREATE TABLE addons (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE configuration_addons (
    configuration_id BIGINT NOT NULL REFERENCES configurations(id) ON DELETE CASCADE,
    addon_id BIGINT NOT NULL REFERENCES addons(id),
    PRIMARY KEY (configuration_id, addon_id)
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    configuration_id BIGINT NOT NULL REFERENCES configurations(id) UNIQUE,
    status order_status NOT NULL DEFAULT 'PLACED',
    customer_name VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(50) NOT NULL,
    object_address TEXT,
    customer_comment TEXT,
    final_price DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE favorites (
    user_id BIGINT NOT NULL REFERENCES users(id),
    configuration_id BIGINT NOT NULL REFERENCES configurations(id) ON DELETE CASCADE,
    added_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, configuration_id)
);

CREATE TABLE portfolio_items (
    id BIGSERIAL PRIMARY KEY,
    configuration_id BIGINT NOT NULL REFERENCES configurations(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    main_image_url VARCHAR(255),
    completion_date DATE
);