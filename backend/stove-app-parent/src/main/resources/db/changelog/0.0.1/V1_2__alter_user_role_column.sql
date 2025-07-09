--liquibase formatted sql

--changeset your_name:003-alter-user-role-to-varchar
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'users' AND column_name = 'user_role';
--comment: Changing user_role column type from ENUM to VARCHAR

ALTER TABLE users ALTER COLUMN user_role TYPE VARCHAR(255) USING user_role::text;

--rollback ALTER TABLE users ALTER COLUMN user_role TYPE user_role USING user_role::user_role;