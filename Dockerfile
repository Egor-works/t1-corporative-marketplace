# Используем официальный образ с OpenJDK 17
FROM eclipse-temurin:8 AS build

# Зависимости для Gradle
RUN apt-get update && apt-get install -y curl findutils

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /build

# Копируем файлы Gradle
COPY gradlew .
COPY gradle /build/gradle

# Копируем файлы проекта
COPY . .

# Даем права на выполнение Gradle wrapper м wait-for.sh
RUN chmod +x gradlew

# Сборка проекта
RUN ./gradlew build

FROM eclipse-temurin:8-ubi9-minimal

# Копируем jar файл из сборочного образа
COPY --from=build /build/config-service/build/libs/config-service-1.0-SNAPSHOT.jar /app/config-service.jar
COPY --from=build /build/discovery-service/build/libs/discovery-service-1.0-SNAPSHOT.jar /app/discovery-service.jar
COPY --from=build /build/gateway-service/build/libs/gateway-service-1.0-SNAPSHOT.jar /app/gateway-service.jar
COPY --from=build /build/cart-service/build/libs/cart-service-1.0-SNAPSHOT.jar /app/cart-service.jar
COPY --from=build /build/consumer-service/build/libs/consumer-service-1.0-SNAPSHOT.jar /app/consumer-service.jar
COPY --from=build /build/product-service/build/libs/product-service-1.0-SNAPSHOT.jar /app/product-service.jar
COPY --from=build /build/order-service/build/libs/order-service-1.0-SNAPSHOT.jar /app/order-service.jar