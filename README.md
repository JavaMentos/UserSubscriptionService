**UserSubscriptionService** — это приложение для управления подписками пользователей. Оно демонстрирует работу с подписками,
используя PostgreSQL в Docker-контейнере для хранения данных и Liquibase для миграции базы данных.

## Настройка базы данных
- **URL**: `jdbc:postgresql://localhost:5432/subscription-db`
- **Пользователь**: `postgres`
- **Пароль**: `postgres`

## Доступ к документации API
- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Порт приложения
- Приложение работает на порту **8080**.

## Коллекция для Postman
- В проекте предоставлена коллекция для импорта в Postman: `UserSubscriptionService.postman_collection.json`.
- Она содержит примеры запросов, что упрощает тестирование и взаимодействие с API.

## Запуск
- Для запуска можно использовать docker-compose.yml, который находится в корне проекта