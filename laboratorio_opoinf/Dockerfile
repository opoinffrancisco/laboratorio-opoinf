FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/laboratorio-opoinf-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} laboratorio_opoinf.jar
EXPOSE 80
EXPOSE 443
ENTRYPOINT ["java", "-jar", "laboratorio_opoinf.jar"]