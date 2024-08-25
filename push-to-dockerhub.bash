#!/bin/bash

# Проверяем, передан ли аргумент (тег)
if [ $# -eq 0 ]; then
    echo "Ошибка: не указан тег. Использование: $0 <тег>"
    exit 1
fi

TAG=$1

# Функция для обработки ошибок
handle_error() {
    echo "Ошибка, обратитесь к администратору"
    exit 1
}

# Сборка Docker образа
docker build -t 29_backend_t1 . || handle_error

# Назначение тегов
docker tag 29_backend_t1 jubastik/29_backend_t1:$TAG || handle_error
docker tag 29_backend_t1 jubastik/29_backend_t1:latest || handle_error

# Публикация образов на Docker Hub
docker push jubastik/29_backend_t1:$TAG || handle_error
docker push jubastik/29_backend_t1:latest || handle_error

echo "Операция успешно завершена"