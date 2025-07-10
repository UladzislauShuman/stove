# Версии

## todo
мы переехали в другой [файл](todo.md)

## v0.4.2
* добавил конфигурацию CORS, чтобы frontend мог делать запрос к backend.
разрешено для порта 3000
* исправил проблему с записью ролей
* добавил API для Favorites и Portfolio
* readme.md разбил на файлы

## v0.4.1
* внедрил миграцию через `Liquibase`
* добавил тестовых данных

## v0.4.0
* добавил минимально необходимый функционал для работы с заказами
  * создать
  * посмотреть
* настроил кафку на новее версию

## v0.3.1
* рефакторинг 
* добавил логирование
* добавил обработку исключений (локальную и глобальную)

## v0.3.0
* добавил API для работы с конфигуратором
* чтобы протестировать посмотри тут [тут](#как-протестировать)
* еще разбил на две "независимые" папки -- потенциально выглядят как два разных
микросервиса

## v0.2.0
* добавил endpoint-ы для запроса данных, необходимых для отображения
элементов в конструкторе\
  (Компоненты, Опции компонентов, Дополнения(Addons), Виды строений)
* добавил немного тестовых данных через Java
* чтобы протестировать посмотри тут [тут](#как-протестировать)

## v0.1.0
* добавил аутентификацию и авторизацию
* чтобы протестировать посмотри тут [тут](#как-протестировать) 

## v0.0.0
* добавил все необходимые зависимости в *pom.xml* 
* Настроил *PostgreSQL* и приложение успешно его видит
* так же накинул зависимостей и property-файлов для других функций
  (*Kafka*, *Zookeeper*, *Prometheus*, *Liquibase*)

# Как запустить проект
* как обычно -- выполнить клонирование репозитория и перейти в папку проекта
```git 
  git clone <...>
  cd stove-app-parent
```
* перешли в `stove-app-parent`,  запускаем `PostgreSQL`, `Kafka` + `Zookeeper` через `Docker` из родительской папки проекта
```
  docker-compose up -d
```
* проверяем, что все контейнеры запущены (иногда при одновременном запуске всего
ложиться кафка. для решения этой проблемы -- до 2 минут включительно запускайте и запускайте)
* настраиваем переменные окружения ("в разработке")
* запускаем приложение (рекомендую через `Intellij IDEA`)
* тестовая прослушка топиков `Kafka` из консоли:
```
docker-compose exec kafka /usr/bin/kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic new_orders \
  --from-beginning \
  --property print.headers=true \
  --property print.key=true \
  --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

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
* **Конфигуратор**
  * все тот же [Postman](https://vladlox-7644620.postman.co/workspace/vladlox's-Workspace~b809a4f1-7ddc-4ff0-8e7a-a131f87758aa/request/44627851-59ed8c01-aac4-467f-9991-e0ba512eb602?action=share&creator=44627851&ctx=documentation)
  * иначе опять [Gemini](https://docs.google.com/document/d/1XAxtrv3bLI4KJ0ialwsE9vLOIO4CSRZnopScChQDBO0/edit?usp=sharing)
  * либо посмотреть в раздел API с авторизацией
  * Но прежде всего прошу
    * Регаете пользователя
    * логинетесь
    * полученный токен используете для авторизации (в Gemini все расписано)
* **Заказы**
  * все тот же [Postman](https://vladlox-7644620.postman.co/workspace/vladlox's-Workspace~b809a4f1-7ddc-4ff0-8e7a-a131f87758aa/request/44627851-59ed8c01-aac4-467f-9991-e0ba512eb602?action=share&creator=44627851&ctx=documentation)
  * иначе опять [Gemini](https://docs.google.com/document/d/1SNt8ANuayPGzH1ZPFmo3BW9ikggqWcMXpbmdGGZZj4Q/edit?usp=sharing)
  * еще можно глянуть в API с авторизацией

# Полезные команды
как видишь, пока не добавил

# API
мы переехали в другой [файл](API-doc.md)

# Как устроен проект
*пока что это не все* \
как тут можно заметить, в проекте есть два основых package:
* `security`
  * хранит в себе весь необходимый функционал для аутентификации и авторизации пользователя
* `constructor`
  * реализацию функционала Выдачи необходимой информации для формирования Конструктора 
* `order`
  * хранит в себе функционал, что реализует операции над заказами

## Как устроен `security`
* `exception` -- хранит классы-исключения
  * [EmailAlreadyExistsException.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/exception/EmailAlreadyExistsException.java)
  -- выбрасывается, когда при регистрации используется Email, который уже занят и т.д.
  * [ResourceNotFoundException.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/exception/ResourceNotFoundException.java)
  * [UserNotFoundException.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/exception/UserNotFoundException.java)
* `controller` -- хранит контроллеры, что обрабатывают запросы. 
  * [AuthController.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/controller/AuthController.java) -- для аутентификации (register и login)
  * [UserController.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/controller/UserController.java) -- для работы с пользователем 
* `dto` -- хранит все DTO классы. о каждом чутка по подробнее
  * [UserDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/dto/UserDto.java) -- DTO для передачи пользователя
  * [LoginRequest.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/dto/LoginRequest.java) 
-- DTO с всех необходимой информацией для логина
  * [RegisterRequest.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/dto/RegisterRequest.java)
-- аналогично, только для регистрации
  * [AuthResponse.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/dto/AuthResponse.java)
    -- ответ при успешном входе (возвращает токен)
* `model` -- классы-модели для взаимодействия с БД
* `repository` -- хранит в себе JPA-интерфейсы для доступа к данным из БД
* `service` -- хранит в себе сервисы
  * [AuthService.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/service/AuthService.java)
    * обрабатывает request-ы dto и возвращает токен
  * [CustomUserDetailsService.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/service/CustomUserDetailsService.java)
    * реализация UserDetailsService для нашего сценария
  * [UserService.java](src/main/java/by/shumpanov/stove/stove_app_parent/security/service/UserService.java)
    * поиск пользователя по почте
    * обновление пользователя

## Как устроен `constructor`
* `exception` -- хранит классы-исключения
  * [ForbiddenException.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/exception/ForbiddenException.java)
-- выбрасывается, когда в логике программы выполняется изменения объекта, который нельзя изменять
    (нельзя изменять дату создания аккаунта и т.д.)
* `config` -- хранит файлы конфигурации. на данный момент там
лежит конфиг на Демо данные.
* `controller` -- хранит контроллеры, что обрабатывают запросы. 
  * [ConstructorDataController.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/controller/ConstructorDataController.java)
-- обрабатывает запросы на выдачу данных о формировании Конструктора
  * [ConfigurationController.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/controller/ConfigurationController.java) 
-- возвращают ConfigurationResponse (DTO такой). CRUD операции по работе с Configuration
  * [FavoriteController.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/controller/FavoriteController.java)
-- тоже возвращает ConfigurationResponse, отвечает за взаимодействие со списком Избранных у пользователей
  * [PortfolioController.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/controller/PortfolioController.java)
-- возвращает PortfolioItemSummaryDto или PortfolioItemDetailDto, для взаимодействия со списком Портфолио
* `dto` -- хранит все DTO классы. о каждом чутка по подробнее
  * [CreateConfigurationRequest.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/CreateConfigurationRequest.java)
    Основной DTO для создания или обновления конфигурации.
    Он является "контейнером", который собирает ID типа печи (stoveTypeId), название (name)
    и два списка: choices и addons.
  * [ChoiceDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/ChoiceDto.java)
    Вспомогательный DTO внутри CreateConfigurationRequest.
    Содержит ID выбранного варианта (optionId) для конкретного компонента.
  * [AddonForConfigurationRequest.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/AddonForConfigurationRequest.java)
    Вспомогательный DTO внутри CreateConfigurationRequest.
    Содержит ID выбранной дополнительной услуги (addonId)
  * [ConfigurationResponse.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/ConfigurationResponse.java)
    Детализированный DTO для ответа клиенту.
    Он агрегирует полную информацию о конфигурации,
    включая рассчитанную totalPrice, информацию о типе печи (StoveTypeDto),
    список доп. услуг (List<AddonDto>) и,
    что самое важное, красиво сгруппированный список компонентов
    с выбранными опциями (List<ChosenComponentDto>)
  * [ChosenComponentDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/ChosenComponentDto.java)`
    Вспомогательный DTO внутри ConfigurationResponse.
    Делает ответ удобным для фронтенда,
    объединяя название компонента (componentName)
    и полную информацию о выбранной опции (chosenOption типа ComponentOptionDto).
    
  * [StoveTypeDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/StoveTypeDto.java)
    Хранит информацию, необходимую для Frontend, чтобы сформировать
    элемент "Тип печи" в Конструкторе
  * [AddonDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/AddonDto.java):
    Тоже самое, но для Дополнительной услуги
  * [ComponentDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/ComponentDto.java)
    Тоже самое, но для Компонента
  * [ComponentOptionDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/ComponentOptionDto.java)
    Тоже самое, но для конкретных вариантов Компонента
  * [PortfolioItemDetailDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/PortfolioItemDetailDto.java)
    dto, что хранит в себе более детализированную информацию о Конфигурации из Портфолио
  * [PortfolioItemSummaryDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/dto/PortfolioItemSummaryDto.java)
    хранит в себе минимальную информацию о Конфигурации из Портфолио (отобразить кнопку, например)
* `model` -- классы-модели для взаимодействия с БД
* `repository` -- хранит в себе JPA-интерфейсы для доступа к данным из БД
* `service` -- хранит в себе сервисы
  * [ConfigurationService.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/service/ConfigurationService.java)
    отвечает за:
    * Создание конфигурации: 
    Принимает `CreateConfigurationRequest`, создает и сохраняет сущность `Configuration`, 
    а затем в цикле создает и сохраняет дочерние сущности `ConfigurationChoice` и `ConfigurationAddon`.
    * Обновление: 
    Находит существующую конфигурацию, проверяет права доступа и статус блокировки, удаляет старые дочерние сущности и создает новые.
    * Расчет цены: 
    Содержит логику для динамического расчета `totalPrice` на основе базовой цены типа печи и модификаторов цен всех выбранных опций и аддонов.
    * Сборку ответа: 
    Агрегирует данные из нескольких сущностей для формирования сложного `ConfigurationResponse`.
  * [ConstructorDataService.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/service/ConstructorDataService.java)
    Простой сервис, который через репозитории достает справочные данные 
    и с помощью мапперов преобразует их в DTO для ConstructorDataController
  * [FavoriteService.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/service/FavoriteService.java)
    сервис для работы с списком Избранных у Пользователя (взять все из списка, добавить новый или удалить)
  * [PortfolioService.java](src/main/java/by/shumpanov/stove/stove_app_parent/constructor/service/PortfolioService.java)
    сервис для работы с Портфолио. предназначен только чтобы получать данные и никак их не изменять
* `util`
  * `mapper` -- хранит в себе мапперы

## Как устроен `order` (в разработке)
* `exception` -- хранит классы-исключения
  * [ConfigurationAlreadyUsedException.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/exception/ConfigurationAlreadyUsedException.java)
* `config` -- хранит файлы конфигурации. 
* `controller` -- хранит контроллеры, что обрабатывают запросы.
  * [OrderController.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/controller/OrderController.java)
    принимает запросы на публикацию и получения заказов
* `dto` -- хранит все DTO классы. о каждом чутка по подробнее
  * [CreateOrderRequest.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/dto/CreateOrderRequest.java)
  -- dto-класс для работы с запросами на оформление заказа
  * [OrderCreatedEventDto.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/dto/OrderCreatedEventDto.java)
  -- сообщение, которое мы отправляем в Kafka
  * [OrderResponse.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/dto/OrderResponse.java)
  -- dto для класса Order, который возвращается в качестве ответа на http-запрос
* `model` -- классы-модели для взаимодействия с БД
* `repository` -- хранит в себе JPA-интерфейсы для доступа к данным из БД
* `service` -- хранит в себе сервисы
  * [OrderService.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/service/OrderService.java) 
  -- содержит в себе функции:
    * создать заказ
    * найти все заказы пользователя
* `kafka` -- классы для работы с `Kafka`
  * [KafkaConfig.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/kafka/KafkaConfig.java)
  -- класс конфигурации Кафки, в котором содержится описание создания топика
  * [KafkaProducerService.java](src/main/java/by/shumpanov/stove/stove_app_parent/order/kafka/KafkaProducerService.java)
  -- сервис для отправки сообщения в Кафку

# Интересная информация
* На этапе компиляции (до того, как Spring вообще увидит ваш код) 
Lombok сканирует ваш класс.
Он находит все поля, помеченные как final 
(или поля, помеченные @NonNull, но final — это лучшая практика).
Он автоматически генерирует публичный конструктор, 
который принимает все эти final поля в качестве аргументо
Начиная с Spring 4.3, действует правило: если у бина есть только один конструктор, 
Spring автоматически использует его для внедрения зависимостей, 
и аннотация @Autowired над этим конструктором становится необязательной.
т.е. -- @Autowired на конструкторе
* делать валидацию DTO стоит для тех, что
используются в качестве объектов запроса
* метод JpaRepository.saveAndFlush