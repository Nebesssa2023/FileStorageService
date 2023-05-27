Spring Boot FileStorageService Application

СУБД MySQL. 

Ограничения:

-Размер файлов не должен превышать размер 100KB 

-Пропускать файлы с расширениями .txt и .csv

Для скачивания файла указать id в ендпоинте download.

При первом запуске приложения в application.properties:

spring.jpa.hibernate.ddl-auto = create

при последующих запусках:

spring.jpa.hibernate.ddl-auto = none