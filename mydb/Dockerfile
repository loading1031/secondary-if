FROM mysql:8.0

COPY init.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD=Todwnl@7706
ENV MYSQL_DATABASE=mydb

CMD ["--character-set-server=utf8mb4","--collation-server=utf8mb4_unicode_ci"]
