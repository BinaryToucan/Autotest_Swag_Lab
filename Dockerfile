FROM eclipse-temurin:21
LABEL authors="Milkis"

WORKDIR /app

COPY . .

RUN chmod +x gradlew

CMD ["./gradlew", "clean", "test"]