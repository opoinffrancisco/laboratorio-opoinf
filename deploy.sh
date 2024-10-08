#!/bin/bash


# Print environment variables for verification
echo "MOSTRAR VARIABLES DE ENTORNO CONFIGURADAS"
echo " "
echo "BASE DE DATOS"
echo "DB_DDLAUTO=$DB_DDLAUTO"
echo "DB_DRIVER=$DB_DRIVER"
echo "DB_PASSWORD=$DB_PASSWORD"
echo "DB_PLATAFORM=$DB_PLATAFORM"
echo "DB_SHOWSQL=$DB_SHOWSQL"
echo "DB_URL=$DB_URL"
echo "DB_USERNAME=$DB_USERNAME"
echo " "
echo "JSON WEB TOKEN (JWT)"
echo "JWT_ACCESS_TOKEN_EXPIRATION=$JWT_ACCESS_TOKEN_EXPIRATION"
echo "JWT_KEY=$JWT_KEY"
echo "JWT_REFRESH_TOKEN_EXPIRATION=$JWT_REFRESH_TOKEN_EXPIRATION"
echo " "
echo "ENVIO DE EMAIL (PROTOCOLO SMTP)"
echo "MAIL_HOST=$MAIL_HOST"
echo "MAIL_PASSWORD=$MAIL_PASSWORD"
echo "MAIL_PORT=$MAIL_PORT"
echo "MAIL_SMTPAUTH=$MAIL_SMTPAUTH"
echo "MAIL_SMTPSTARTTLSENABLE=$MAIL_SMTPSTARTTLSENABLE"
echo "MAIL_USERNAME=$MAIL_USERNAME"
echo " "

# Run Docker Compose
docker-compose up -d
