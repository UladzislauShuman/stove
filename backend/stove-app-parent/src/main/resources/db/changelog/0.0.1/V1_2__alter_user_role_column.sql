--liquibase formatted sql

--changeset your_name:003-alter-enum-columns-to-varchar
--comment: Changing ENUM columns (user_role, status) to VARCHAR for JPA compatibility

-- Изменение user_role в таблице users
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'users' AND column_name = 'user_role';
ALTER TABLE users ALTER COLUMN user_role TYPE VARCHAR(255) USING user_role::text;

-- Изменение status в таблице orders
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'orders' AND column_name = 'status';
ALTER TABLE orders ALTER COLUMN status TYPE VARCHAR(255) USING status::text;


--rollback ALTER TABLE users ALTER COLUMN user_role TYPE user_role USING user_role::user_role;
--rollback ALTER TABLE orders ALTER COLUMN status TYPE order_status USING status::order_status;