FROM postgres:latest
MAINTAINER Kapil Kataria <katariakapil@gmail.com>

ENV POSTGRES_DB rpm

COPY create-schema.ddl         /docker-entrypoint-initdb.d/01-create-schema.sql