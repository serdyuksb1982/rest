## Project from learning Spring Boot
------------------------
## Описание проекта:
* java - 17;
* gradle;
* Spring boot;
# Описание функционла:
* Сохранение не в базе данных - временное решение;
* Основные сущности: Client, Order;
* Методы контоллеров согласно CRUD;
* Добавлены Interseptors;

## OpenApi Swagger 2

Зависимость Spring Boot от Gradle Swagger:
* Для Spring Boot 3:
  Чтобы использовать Swagger 3 в вашем проекте Gradle, вам нужно добавить зависимость springdoc-openapi-starter-webmvc-ui в файл build.gradle вашего проекта:

  implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.0.3'
* Для Spring Boot 2:
  В более ранней версии Spring Boot вы можете использовать зависимость springdoc-openapi-ui:

    implementation group: 'org.springdoc', name: 'springdoc-openapi-ui', version: '1.
* Запустите и проверьте
  Запустите Spring Boot project. Откройте браузер с URL:
  http://localhost:8080/swagger-ui/index.html
* Откройте http://localhost:8080/v3/api-docs, вы увидите документ в формате Json