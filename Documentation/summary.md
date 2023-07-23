## Отчёт по итогам автоматизации

Проведено автоматизированное тестирование веб-сервиса покупки туров для обоих вариантов покупки:
* Оплата по дебетовой карте
* Оплата по кредитной карте

#### Было запланировано:
- реализовать набор UI-тестов (как позитивных, так и негативных) по предоставленным данным (валидная и невалидная карты);
- проверить поддержку заявленных СУБД (MySQL, PostgreSQL);
- использовать перечень инструментов.

#### Было сделано:
- реализован набор UI-тестов (62 тест-кейса);
- подтверждена заявленная поддержка СУБД (MySQL, PostgreSQL);
- в процессе работы использовались все запланированные инструменты кроме Appveyor.

#### Сработавшие риски:
- трудности с поиском необходимых CSS-селекторов для UI тестирования;
- трудности с подключением баз данных и SUT к Docker;
- наличие багов в тестируемом приложении;
- сложности, связанные с необходимостью работы сразу с двумя БД - MySQL и PostgreSQL;
- Возникли проблемы с генерацией отчёта с помощью AllureReport.

#### Общий итог по времени:
- Планирование - запланировано 10 часов, фактически - 10;
- Автоматизация - запланировано 120 часов, фактически - 100;
- Отчётные документы по итогам тестирования - запланировано 5 часов, фактически - 5;
- Отчётные документы по итогам автоматизации - запланировано 5 часов, фактически - 5;
- Запланированное время на риски - 5 часов.

Итого запланировано: С учётом возможных рисков и сложностей - **145 часов**.

Итого затрачено: **125 часов**.

Работа была проведена в заявленные сроки, и без превышения заявленных в [плане](https://github.com/AlenaBobrov/QA_Auto_Diploma/blob/main/Documentation/Plan.md) временных затрат в 120 часов.

Ключевая задача - автоматизировать сценарии (как позитивные, так и негативные) покупки тура - выполнена.