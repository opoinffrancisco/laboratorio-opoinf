FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/laboratorio_opoinf-0.0.1.jar
COPY ${JAR_FILE} laboratorio_opoinf.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_laboratorio_opoinf.jar"]