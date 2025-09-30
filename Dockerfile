FROM maven:3-amazoncorretto-21-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN ["mvn", "dependency:go-offline"]

COPY src ./src

RUN ["mvn", "clean", "package", "-DskipTests"]

FROM amazoncorretto:21-alpine3.21-jdk AS runtime

WORKDIR /app

COPY --from=build /app/target/drivecore-1.0.0.jar /app/drivecore-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "/app/drivecore-1.0.0.jar"]