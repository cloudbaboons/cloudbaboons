FROM mysql:5.5.44
MAINTAINER Kapil Kataria <cloudbaboons@gmail.com>


COPY create-user-and-schema.ddl /docker-entrypoint-initdb.d/01-create-user-and-schema.sql
COPY create-schema.ddl          /docker-entrypoint-initdb.d/02-create-schema.sql