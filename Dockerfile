FROM eclipse-temurin:21
LABEL authors="Milkis"

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew dependencies

CMD ["./gradlew", "clean", "test"]