# Проект "job4j_todo"

### 1. Данное приложение разработано для хранения списка задач. 
###    Приложение оболадает несколькими удобными функциями:

##### * Отображается текущий статус задачи - "Выполнено/Не выполнено".

<img src="data/Список задач.png"/>

##### * Реализован удобный отбор задач по статусу "Все/Выполнено/Не выполнено".

<img src="data/Не выполненные.png"/>

##### * Форма создания задачи.

<img src="data/Создание задачи.png"/>

##### * Есть отдельная форма для внесения изменеий в созданную задачу. 
#####   Доступные команды - отредактировать, выполнить, удалить. Также есть возможнсть вернутся к списку без изменеий в задаче.
<img src="data/Редактирование задачи.png"/>

### 2. Стек технологий: 
+ Java 17
+ Spring (Spring MVC, Spring boot)
+ Hibernate 5
+ Thymeleaf 3 
+ Bootstrap 5.1 (CSS 3, HTML 5).

### 3. Требования к окружению:
+ Java 17
+ Maven 3.8.7
+ PostgresSql 14

### 4. Чтобы запустить приложение необходимо:
+ Создать базу данных ```create database todo;```
+ Открыть консоль в операционной системе ```cmd.exe```
+ Перейти в директорию с проектом 
+ Выполнить команду mvn exec:java -Dexec.mainclass="ru.job4j.Main"
+ Перейти на стартовую страницу http://localhost:8080/index

### 5. Контакты:
- **email**    avetis.mkhitaryants@gmail.com
- **телеграм** @avetis_m
