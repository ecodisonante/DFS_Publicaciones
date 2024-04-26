FROM openjdk:21-ea-24-oracle

WORKDIR /app

COPY target/publicaciones-2.1.jar app.jar
COPY wallet /app/oracle_wallet

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]