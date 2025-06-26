# Версии

## v1
* добавил аутентификацию и авторизацию
* чтобы протестировать посмотри тут [тут](#как-протестировать) 

## v0
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

# Полезные команды
как видишь, пока не добавил