#!/bin/bash

#Define VARIABLES DE ENTORNO
 # Base de datos
export DB_DDLAUTO=update
export DB_DRIVER=org.postgresql.Driver
export DB_PASSWORD=LMKcdv60734
export DB_PLATAFORM=org.hibernate.dialect.PostgreSQLDialect
export DB_SHOWSQL=true
export DB_URL=jdbc:postgresql://node180456-laboratorio-opoinf.mircloud.us:11015/laboratorio-opoinf
export DB_USERNAME=webadmin
 # JSON WEB TOKENS (JWT)
export JWT_ACCESS_TOKEN_EXPIRATION=3600000
export JWT_KEY=12345678910123456789101234567891023
export JWT_REFRESH_TOKEN_EXPIRATION=7200000
 # Email
export MAIL_HOST=smtp.gmail.com
export MAIL_PASSWORD=wxomaipjcyanhiqq
export MAIL_PORT=587
export MAIL_SMTPAUTH=true
export MAIL_SMTPSTARTTLSENABLE=true
export MAIL_USERNAME=soporte.opoinf@gmail.com


# Run Docker Compose
docker-compose up --build