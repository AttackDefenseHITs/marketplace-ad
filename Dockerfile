FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn -B -DskipTests clean install

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/SpringAuth-0.0.1.jar /opt/service.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://database:5429/user1
ENV POSTGRES_USER=user1
ENV POSTGRES_PASSWORD=user1
EXPOSE 8080
CMD ["java", "-jar", "/opt/service.jar"]