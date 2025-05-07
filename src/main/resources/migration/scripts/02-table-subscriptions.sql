--liquibase formatted sql

-- changeset VasiliySachkov:subscriptions-table
CREATE TABLE IF NOT EXISTS subscriptions
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    service_name VARCHAR(255),
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (user_id, service_name)
);

COMMENT ON TABLE subscriptions IS 'Таблица подписок';
COMMENT ON COLUMN subscriptions.id IS 'Первичный ключ';
COMMENT ON COLUMN subscriptions.service_name IS 'Название сервиса';
COMMENT ON COLUMN subscriptions.user_id IS 'Уникальное имя пользователя';
