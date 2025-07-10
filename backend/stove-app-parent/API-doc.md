# API
тут будет все прописано в примерах
## Авторизация
* POST http://localhost:8080/api/v1/auth/register Body:
```json
{
  "full_name": "Иван Петров",
  "phone_number": "+375291112233",
  "email": "ivan.petrov@example.com",
  "password": "supersecretpassword"
} 
```
-- запрос на регистрацию. в ответ возвращается Jwt-токен
* POST http://localhost:8080/api/v1/auth/login Body:
```json
  {
      "email": "ivan.petrov@example.com",
      "password": "supersecretpassword"
  } 
```
-- если вы уже есть в системе, то чтобы получить
JWT токен нужно выполнить такой запрос

* GET http://localhost:8080/api/v1/users/me Authorization: JWT-токен
  -- получаете информацию о пользователе
  в формате JSON. пример ответа:
```json
  {
      "id": 1,
      "full_name": "Иван Петров",
      "email": "ivan.petrov@example.com",
      "phone_number": "+375291112233",
      "user_role": "CUSTOMER"
  }
```
## Конструктор
### получение публичных данных
* GET http://localhost:8080/constructor-data/stove-types
  -- публичный запрос (не требует авторизации). в ответе массив из видов Строения. \
  пример:
  ```json
  [
      {
          "id": 1,
          "name": "Помпейская печь",
          "description": "Классическая дровяная печь для пиццы и выпечки.",
          "base_price": 5000,
          "image_url": "https://example.com/images/pompei.jpg"
      }
  ]
  ```
* GET http://localhost:8080/constructor-data/stove-types/1/components --
  запрос на получение Компонент из которых состоит Строение. \
  пример ответа для Строение id=1 из прошлого ответа:
```json
[
  {
    "id": 1,
    "name": "Основание",
    "description": "На чем будет стоять ваша печь.",
    "is_required": false,
    "allow_multiple_choices": false,
    "component_options": null
  },
  {
    "id": 2,
    "name": "Купол",
    "description": "Материал основной камеры горения.",
    "is_required": false,
    "allow_multiple_choices": false,
    "component_options": null
  },
  {
    "id": 3,
    "name": "Утепление купола",
    "description": "Как долго печь будет держать тепло.",
    "is_required": false,
    "allow_multiple_choices": false,
    "component_options": null
  },
  {
    "id": 4,
    "name": "Облицовка",
    "description": "Внешний вид вашей печи.",
    "is_required": false,
    "allow_multiple_choices": false,
    "component_options": null
  }
]
```

* GET http://localhost:8080/constructor-data/components/1/options
  -- запрос на получение Опций, которые можно применить к компоненту id=1 \
  пример ответа:
```json
[
    {
        "id": 1,
        "name": "На кирпичном постаменте",
        "price_modifier": 2500,
        "image_url": "https://example.com/images/base_brick.jpg",
        "is_default": false
    },
    {
        "id": 2,
        "name": "На металлическом каркасе",
        "price_modifier": 3500,
        "image_url": "https://example.com/images/base_metal.jpg",
        "is_default": false
    },
    {
        "id": 3,
        "name": "На готовом прицепе",
        "price_modifier": 7000,
        "image_url": "https://example.com/images/base_trailer.jpg",
        "is_default": false
    }
]
```

* GET http://localhost:8080/constructor-data/addons
  -- запрос на получение Дополнений к Конфигурации в Конструкторе \
  пример ответа:
```json
[
    {
        "id": 1,
        "name": "Доставка в пределах 50 км",
        "description": "Привезем все материалы на ваш участок.",
        "price": 100
    },
    {
        "id": 2,
        "name": "Строительство дымохода (за метр)",
        "description": "Цена указана за 1 метр кирпичного дымохода.",
        "price": 250
    }
]
```

* GET http://localhost:8080/api/v1/public/portfolio?stoveTypeId=1&size=5&page=0&sort=title,asc -- публичный API для получения Конфигураций из Портфолио (сделанные
  Печником). выдается постранично. можно задать параметрами Тип строения, Сколько на странице, Номер страницы и параметры Сортировки
  (все эти параметры не обязательны)
  пример ответа на запрос, который я прописал
```json
{
  "content": [
    {
      "id": 1,
      "title": "Помпейская печь в итальянском стиле",
      "main_image_url": ".../portfolio_1.jpg",
      "completion_date": "2024-08-15",
      "stove_type_name": "Помпейская печь"
    }
  ],
  "page": {
    "size": 5,
    "number": 0,
    "totalElements": 1,
    "totalPages": 1
  }
}
```
* GET http://localhost:8080/api/v1/public/portfolio/1 -- получает Конкретный проект из Портфолио с бОльшим количеством
  информации.
  пример ответа:
```json
{
    "id": 1,
    "title": "Помпейская печь в итальянском стиле",
    "description": "Построена в августе 2024 года...",
    "main_image_url": ".../portfolio_1.jpg",
    "completion_date": "2024-08-15",
    "configuration": {
        "id": 1,
        "name": "Классическая помпейская печь в беседке",
        "is_template": true,
        "is_locked": false,
        "created_at": "2025-07-10T14:31:46.305785",
        "total_price": 7601.00,
        "stove_type": {
            "id": 1,
            "name": "Помпейская печь",
            "description": "Классическая дровяная печь для пиццы и выпечки.",
            "base_price": 1001.00,
            "image_url": "https://pushkapech.ru/wp-content/uploads/2024/11/255c97cb-c291-4876-a921-0aa51ce24a24.jpg"
        },
        "components": [
            {
                "component_name": "Основание",
                "chosen_option": {
                    "id": 101,
                    "default": false,
                    "name": "На кирпичном постаменте",
                    "price_modifier": 2500.00,
                    "image_url": ".../base_brick.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Купол",
                "chosen_option": {
                    "id": 103,
                    "default": false,
                    "name": "Купол из шамотного кирпича",
                    "price_modifier": 1800.00,
                    "image_url": ".../dome_yellow.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Утепление",
                "chosen_option": {
                    "id": 105,
                    "default": false,
                    "name": "Утеплитель - керамоволокно",
                    "price_modifier": 800.00,
                    "image_url": ".../ins_ceramic.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Дымоход",
                "chosen_option": {
                    "id": 108,
                    "default": false,
                    "name": "Дымоход \"Сэндвич\" (сталь)",
                    "price_modifier": 1500.00,
                    "image_url": ".../pipe_sandwich.jpg",
                    "is_default": false
                }
            }
        ],
        "addons": [],
        "locked": false,
        "template": true
    }
}
```
### с авторизацией
* POST http://localhost:8080/api/v1/configurations Auth: JWT, Body: (пример)
```json
{
  "stove_type_id": 1,
  "name": "Моя ЛУЧШАЯ печь для пиццы",
  "choices": [
    { "option_id": 1 }, 
    { "option_id": 4 },
    { "option_id": 7 }
  ],
  "addons": [
    { "addon_id": 1 },
    { "addon_id": 2 }
  ]
}
```
-- запрос на создание Конфигурации \
пример ответа:
```json
{
    "id": 1,
    "name": "Моя ЛУЧШАЯ печь для пиццы",
    "is_template": false,
    "is_locked": false,
    "created_at": "2025-06-29T00:14:07.088565",
    "total_price": 9950,
    "stove_type": {
        "id": 1,
        "name": "Помпейская печь",
        "description": "Классическая дровяная печь для пиццы и выпечки.",
        "base_price": 5000,
        "image_url": "https://example.com/images/pompei.jpg"
    },
    "components": [
        {
            "component_name": "Основание",
            "chosen_option": {
                "id": 1,
                "name": "На кирпичном постаменте",
                "price_modifier": 2500,
                "image_url": "https://example.com/images/base_brick.jpg",
                "is_default": true
            }
        },
        {
            "component_name": "Купол",
            "chosen_option": {
                "id": 4,
                "name": "Из красного огнеупорного кирпича",
                "price_modifier": 1500,
                "image_url": "https://example.com/images/dome_red.jpg",
                "is_default": true
            }
        },
        {
            "component_name": "Утепление купола",
            "chosen_option": {
                "id": 7,
                "name": "Вермикулит + цемент",
                "price_modifier": 600,
                "image_url": "https://example.com/images/ins_vermiculite.jpg",
                "is_default": false
            }
        }
    ],
    "addons": [
        {
            "id": 1,
            "name": "Доставка в пределах 50 км",
            "description": "Привезем все материалы на ваш участок.",
            "price": 100
        },
        {
            "id": 2,
            "name": "Строительство дымохода (за метр)",
            "description": "Цена указана за 1 метр кирпичного дымохода.",
            "price": 250
        }
    ]
}
```

* GET http://localhost:8080/api/v1/configurations/1 -- запрос на получение конкретной
  конфигурации \
  пример ответа: созданная Конфигурация выше

## Заказы
### с авторизацией
* прежде всего регистрируемся и логинимся. с полученным токеном продолжаем работу
* создаем конфигурацию заказа
* POST http://localhost:8080/api/v1/orders Auth: JWT, Body:
```json
{
  "configuration_id": 1,
  "customer_name": "Иван Заказчиков",
  "customer_phone": "+375441234567",
  "object_address": "Минская обл., д. Тестовая, ул. Программная, д. 1",
  "customer_comment": "Прошу связаться со мной после 18:00 для уточнения деталей."
}
```
в результате получаем
```json
{
    "id": 1,
    "status": "PLACED",
    "final_price": 0,
    "created_at": "2025-07-02T18:20:46.47761",
    "configuration": {
        "id": 1,
        "name": "Моя ЛУЧШАЯ печь для пиццы",
        "is_template": false,
        "is_locked": true,
        "created_at": "2025-07-02T18:19:08.525535",
        "total_price": 9950,
        "stove_type": {
            "id": 1,
            "name": "Помпейская печь",
            "description": "Классическая дровяная печь для пиццы и выпечки.",
            "base_price": 5000,
            "image_url": "https://example.com/images/pompei.jpg"
        },
        "components": [
            {
                "component_name": "Основание",
                "chosen_option": {
                    "id": 1,
                    "name": "На кирпичном постаменте",
                    "price_modifier": 2500,
                    "image_url": "https://example.com/images/base_brick.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Купол",
                "chosen_option": {
                    "id": 4,
                    "name": "Из красного огнеупорного кирпича",
                    "price_modifier": 1500,
                    "image_url": "https://example.com/images/dome_red.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Утепление купола",
                "chosen_option": {
                    "id": 7,
                    "name": "Вермикулит + цемент",
                    "price_modifier": 600,
                    "image_url": "https://example.com/images/ins_vermiculite.jpg",
                    "is_default": false
                }
            }
        ],
        "addons": [
            {
                "id": 2,
                "name": "Строительство дымохода (за метр)",
                "description": "Цена указана за 1 метр кирпичного дымохода.",
                "price": 250
            },
            {
                "id": 1,
                "name": "Доставка в пределах 50 км",
                "description": "Привезем все материалы на ваш участок.",
                "price": 100
            }
        ]
    }
}
```
теперь наша конфигурация имеет параметр `"is_locked": true` \
это значит, что изменить конфигурацию (PUT) уже не получиться
(на нее ссылаются и это вызовет проблемы)
если мы повторим эту операцию снова, то получим ответ:
```json
{
    "status_code": 500,
    "timestamp": "2025-07-02T18:22:13.454105",
    "message": "Конфигурация с таким id уже занята",
    "path": "/api/v1/orders"
}
```
* GET http://localhost:8080/api/v1/orders Auth: JWT -- запрос на получение списка ваших заказов:
```json
[
    {
        "id": 1,
        "status": "PLACED",
        "final_price": 0,
        "created_at": "2025-07-02T18:20:46.47761",
        "configuration": {
            "id": 1,
            "name": "Моя ЛУЧШАЯ печь для пиццы",
            "is_template": false,
            "is_locked": true,
            "created_at": "2025-07-02T18:19:08.525535",
            "total_price": 9950,
            "stove_type": {
                "id": 1,
                "name": "Помпейская печь",
                "description": "Классическая дровяная печь для пиццы и выпечки.",
                "base_price": 5000,
                "image_url": "https://example.com/images/pompei.jpg"
            },
            "components": [
                {
                    "component_name": "Основание",
                    "chosen_option": {
                        "id": 1,
                        "name": "На кирпичном постаменте",
                        "price_modifier": 2500,
                        "image_url": "https://example.com/images/base_brick.jpg",
                        "is_default": false
                    }
                },
                {
                    "component_name": "Купол",
                    "chosen_option": {
                        "id": 4,
                        "name": "Из красного огнеупорного кирпича",
                        "price_modifier": 1500,
                        "image_url": "https://example.com/images/dome_red.jpg",
                        "is_default": false
                    }
                },
                {
                    "component_name": "Утепление купола",
                    "chosen_option": {
                        "id": 7,
                        "name": "Вермикулит + цемент",
                        "price_modifier": 600,
                        "image_url": "https://example.com/images/ins_vermiculite.jpg",
                        "is_default": false
                    }
                }
            ],
            "addons": [
                {
                    "id": 2,
                    "name": "Строительство дымохода (за метр)",
                    "description": "Цена указана за 1 метр кирпичного дымохода.",
                    "price": 250
                },
                {
                    "id": 1,
                    "name": "Доставка в пределах 50 км",
                    "description": "Привезем все материалы на ваш участок.",
                    "price": 100
                }
            ]
        }
    }
]
```

* POST http://localhost:8080/api/v1/favorites/1 Auth: JWT -- запрос на добавления Конфигурации в список Избранных пользователем.
* GET http://localhost:8080/api/v1/favorites Auth: JWT -- получить список Избранных Конфигураций пользователя.
  Ответ поступает в виде списка с объектами в детализированном формате
  Пример ответа:
```json
[
    {
        "id": 1,
        "name": "Классическая помпейская печь в беседке",
        "is_template": true,
        "is_locked": false,
        "created_at": "2025-07-10T14:31:46.305785",
        "total_price": 7601.00,
        "stove_type": {
            "id": 1,
            "name": "Помпейская печь",
            "description": "Классическая дровяная печь для пиццы и выпечки.",
            "base_price": 1001.00,
            "image_url": "https://pushkapech.ru/wp-content/uploads/2024/11/255c97cb-c291-4876-a921-0aa51ce24a24.jpg"
        },
        "components": [
            {
                "component_name": "Основание",
                "chosen_option": {
                    "id": 101,
                    "default": false,
                    "name": "На кирпичном постаменте",
                    "price_modifier": 2500.00,
                    "image_url": ".../base_brick.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Купол",
                "chosen_option": {
                    "id": 103,
                    "default": false,
                    "name": "Купол из шамотного кирпича",
                    "price_modifier": 1800.00,
                    "image_url": ".../dome_yellow.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Утепление",
                "chosen_option": {
                    "id": 105,
                    "default": false,
                    "name": "Утеплитель - керамоволокно",
                    "price_modifier": 800.00,
                    "image_url": ".../ins_ceramic.jpg",
                    "is_default": false
                }
            },
            {
                "component_name": "Дымоход",
                "chosen_option": {
                    "id": 108,
                    "default": false,
                    "name": "Дымоход \"Сэндвич\" (сталь)",
                    "price_modifier": 1500.00,
                    "image_url": ".../pipe_sandwich.jpg",
                    "is_default": false
                }
            }
        ],
        "addons": [],
        "locked": false,
        "template": true
    }
]
```
* DELETE http://localhost:8080/api/v1/favorites/1 -- удалить Конфигурацию из списка Избранных по Id.
