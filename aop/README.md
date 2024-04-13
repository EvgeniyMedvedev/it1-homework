## Cистемы учета времени выполнения методов

Описание
====
Система учета времени выполнения методов в приложении с использованием Spring AOP. 
Система должна быть способна асинхронно логировать и анализировать данные о времени выполнения методов.

Spring AOP + Spring Data + timescaledb + Docker

API /api/time
====

* /avg - получить среднее значение исполнения
* /all - получить все методы
* /min - получить минимальное значение исполнения
* /max - получить максимальное значение исполнения
* /newMethod POST - добавить метод в бд с рандомным временем исполнения