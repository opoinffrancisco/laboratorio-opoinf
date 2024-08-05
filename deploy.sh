#!/bin/bash

# Define environment variables
export DB_DDLAUTO=update
export DB_DRIVER=org.postgresql.Driver
export DB_PASSWORD=LMKcdv60734
export DB_PLATAFORM=org.hibernate.dialect.PostgreSQLDialect
export DB_SHOWSQL=true
export DB_URL=jdbc:postgresql://node180456-laboratorio-opoinf.mircloud.us:11015/laboratorio-opoinf
export DB_USERNAME=webadmin
export JWT_ACCESS_TOKEN_EXPIRATION=3600000
export JWT_KEY=12345678910123456789101234567891023
export JWT_REFRESH_TOKEN_EXPIRATION=7200000
export MAIL_HOST=smtp.gmail.com
export MAIL_PASSWORD=wxomaipjcyanhiqq
export MAIL_PORT=587
export MAIL_SMTPAUTH=true
export MAIL_SMTPSTARTTLSENABLE=true
export MAIL_USERNAME=soporte.opoinf@gmail.com

# Print environment variables for verification
echo "DB_DDLAUTO=$DB_DDLAUTO"
echo "DB_DRIVER=$DB_DRIVER"
echo "DB_PASSWORD=$DB_PASSWORD"
echo "DB_PLATAFORM=$DB_PLATAFORM"
echo "DB_SHOWSQL=$DB_SHOWSQL"
echo "DB_URL=$DB_URL"
echo "DB_USERNAME=$DB_USERNAME"
echo "JWT_ACCESS_TOKEN_EXPIRATION=$JWT_ACCESS_TOKEN_EXPIRATION"
echo "JWT_KEY=$JWT_KEY"
echo "JWT_REFRESH_TOKEN_EXPIRATION=$JWT_REFRESH_TOKEN_EXPIRATION"
echo "MAIL_HOST=$MAIL_HOST"
echo "MAIL_PASSWORD=$MAIL_PASSWORD"
echo "MAIL_PORT=$MAIL_PORT"
echo "MAIL_SMTPAUTH=$MAIL_SMTPAUTH"
echo "MAIL_SMTPSTARTTLSENABLE=$MAIL_SMTPSTARTTLSENABLE"
echo "MAIL_USERNAME=$MAIL_USERNAME"

# Run Docker Compose
docker-compose up --build
