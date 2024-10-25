Пример использования Rest-запросов RestClient ("manager") для обращения к сервису ("catalogue") связанному с бд postgres
+ пример использования защиты "spring-boot-starter-security" со стороны сервера.

см. https://github.com/alex-kosarev/sc24/tree/SC24EP04-oauth

в docker две б.д.: "catalogue" - хранит продукты, "manager" - хранит пользователей

docker run --name catalogue-db -p 5434:5432 -e POSTGRES_DB=catalogue -e POSTGRES_USER=catalogue -e POSTGRES_PASSWORD=catalogue postgres
docker run --name manager-db -p 5433:5432 -e POSTGRES_DB=manager -e POSTGRES_USER=manager -e POSTGRES_PASSWORD=manager postgres

Два приложения:
web-приложение "manager-app" (порт 8080) посылает запросы через ProductsRestClient приложению "catalogue-service" (порт 8081) связанному с бд "catalogue".

Для входа в web-приложение "manager-app" используются:
логин = user
пароль нужно взять из Run window при запуске в InteligeIdea