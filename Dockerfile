# Используем официальный образ с OpenJDK 17
FROM openjdk:8-jdk-slim

# Зависимости для Gradle
RUN apt-get update && apt-get install -y curl findutils

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы Gradle
COPY gradlew .
COPY gradle /app/gradle

# Копируем файлы проекта
COPY . .

# Даем права на выполнение Gradle wrapper м start.sh
RUN chmod +x gradlew

# Сборка проекта
RUN ./gradlew build