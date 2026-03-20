# Notification Service

## Description
Consumes order events from Kafka and sends notifications

## Run
mvn clean package
docker build -t notification-service .
docker run notification-service