# Версии

## v0.0.2
* добавил endpoint-ы для запроса данных, необходимых для отображения
элементов в конструкторе\
  (Компоненты, Опции компонентов, Дополнения(Addons), Виды строений)
* добавил немного тестовых данных через Java
* чтобы протестировать посмотри тут [тут](#как-протестировать)

## v0.0.1
* добавил аутентификацию и авторизацию
* чтобы протестировать посмотри тут [тут](#как-протестировать) 

## v0.0.0
* добавил все необходимые зависимости в *pom.xml* 
* Настроил *PostgreSQL* и приложение успешно его видит
* так же накинул зависимостей и property-файлов для других функций
  (*Kafka*, *Zookeeper*, *Prometheus*, *Liquibase*)

# Как запустить проект
* для начала запускаем PostgreSQL через Docker из родительской папки проекта\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`docker-compose up -d postgres`
* запускаем приложение через Intellij IDEA

# Как протестировать
* **Аутентификацию и Авторизацию**:
  * вот что-то проверить от [Postman](https://vladlox-7644620.postman.co/workspace/vladlox's-Workspace~b809a4f1-7ddc-4ff0-8e7a-a131f87758aa/collection/44627851-63b6c9af-a13a-4b23-b8b8-742d491c8268?action=share&creator=44627851)
  * иначе, вот что мне написал [Gemini](https://docs.google.com/document/d/1DmYPehbleJgkKnN0wiQ7wsPtck0EBpdMBXq0G3rq26M/edit?usp=sharing)
  * там в папке `test` есть тест какой-то базовый 
* **Endpoint-ы для данных конструктора**
  *  попробуйте [Postman](https://vladlox-7644620.postman.co/workspace/vladlox's-Workspace~b809a4f1-7ddc-4ff0-8e7a-a131f87758aa/request/44627851-c5598a4b-a47d-451b-8091-f30663aac077?action=share&creator=44627851&ctx=documentation)
  * иначе -- вот сами Endpoint-ы (надеюсь по запросу понятно, что тут и зачем),
  они хорошо в обычном браузере работают
    * http://localhost:8080/constructor-data/stove-types
    * http://localhost:8080/constructor-data/stove-types/1/components
    * http://localhost:8080/constructor-data/components/1/options
    * http://localhost:8080/constructor-data/addons
# Полезные команды
как видишь, пока не добавил

# Идеи
## Рефакторинг
* может стоит перенести логику model в БД?
* вынести Precision и Scale во что-то другое
* может перенести default значения в БД?
* очистить лишние `import`-ы
* вот есть как -- делаю репозиторий, и относительно него будто сервис\
но ведь я использовал их и в других сервисах, не одноименных\
мне получается использовать через сервис или через репозиторий нормально?
* обработать исключения
* логирование