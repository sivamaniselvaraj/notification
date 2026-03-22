# Notification Service

## Description
Consumes order events from Kafka and sends notifications

## Run
mvn clean package
docker build -t notification-service .
docker run -p 8083:8083 notification-service