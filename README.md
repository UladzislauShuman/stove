# О чем речь
Это проект по "**Сбор заказов на печи**"\

# Как с папками работать
ну я думаю тут все очевидно
твоя папка -- `android`\
моя это `backend` и `frontend`(*которым я надеюсь буду заниматься*)

# Как с Git работать
* делаем `fork`-и
* и потом думаю просто создадим себе по ветке и потом будем `pull request`-ы делать 
* я создал новую ветку dev, от которой создал ветки new-backend-vlad(переименную в backend-vlad) и для frontend соответственно.
далее я распишу процесс базовой жизненной ситуации, как с ними работаем:
    * я сижу на backend или frontend `git checkout backend`
    * получаю все свежие изменения с сервера `git fetch origin`
    * и перемещаю свою коммиты на самую последнюю версию dev `git rebase origin/dev`
    * ну и пишем-пишем кодик и комитим-комитим: (я на backend) `git add .`, `git commit -m "..."`
* чтобы проверить, как работают front и back вместе, будем делать так
    * `git checkout dev`
    * `git merge backend` -- локально сливаем в dev
    * `git merge frontend`
    * тестируем
        * если все хорошо -- `git push origin dev`
        * если все плохо -- сбрасываем до состояния, что было на сервере `git reset --hard origin/dev`, и все изменения проводим в ветках frontend и backend, и потом повторяем процесс
* либо сливать через pull request из backend/frontend в dev, обновлять dev и тестировать
* pull request в main ветку из dev
# Ссылки:
* [Google Dock со всей той инфой](https://docs.google.com/document/d/1W9tu9caTSWD0fNBeu2v5KQYu0AHJdq9tT0mTlw7b8Tw/edit?usp=sharing)