# Автотесты Swag Labs

UI-автотесты учебного сайта [Sauce Demo](https://www.saucedemo.com/) на Java, Selenium WebDriver, TestNG и Maven.

## Требования

- JDK 17 или новее
- Maven 3.9 или новее
- Google Chrome

Selenium Manager автоматически подбирает драйвер Chrome при первом запуске.

## Запуск

```bash
mvn clean test
```

По умолчанию тесты запускаются в headless-режиме. Для запуска с открытым браузером:

```bash
mvn clean test -Dheadless=false
```

Результаты Maven Surefire находятся в `target/surefire-reports`, скриншоты неуспешных тестов - в `target/screenshots`.

## Реализованные сценарии

1. Успешная авторизация пользователя `standard_user`.
2. Ошибка авторизации пользователя `locked_out_user`.
3. Добавление Sauce Labs Backpack в корзину.
4. Завершение оформления заказа.
5. Сортировка товаров по возрастанию цены.

Учётные данные используются только от учебного сайта Sauce Demo и хранятся в конфигурации тестов.