# give-wealth-level-gif
Сервис показывает GIF в зависимости от изменения курса валюты по сравнению со вчерашним днем

## Запуск с помощью Docker:

1. Для начала работы должен быть установлен Docker Desktop, для установки следуйте инструкции с https://docs.docker.com

2. Открыть командную консоль и скачать образ, выполнив команду `docker pull creed3033/give-wealth-level-gif:latest`

3. Cкачать образ, выполнив команду `docker pull creed3033/give-wealth-level-gif:latest`

4. Запустить приложение, выполнив в командной строке `docker run -dp 8080:8080 creed3033/give-wealth-level-gif:latest`

#### Адрес для взаимодействия с сервисом по умолчанию: http://localhost:8080/api/gif

Для получения ответа нужно в параметрах передать код валюты, по отношению к которой сравнивается курс  
Пример запроса: http://localhost:8080/api/gif/EUR

#### Powered by Giphy.com
