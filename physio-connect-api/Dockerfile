FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

FROM openjdk:20-ea-17-jdk as builder
WORKDIR /app
COPY --from=build /app/target/*.jar /app/pc-app.jar
RUN java -Djarmode=layertools -jar pc-app.jar extract

FROM openjdk:20-ea-17-jdk
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]