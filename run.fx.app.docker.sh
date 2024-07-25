#!/usr/bin/env sh

echo "#1 Building the FX App..." && \
./gradlew clean build && \

echo "#2 Cleaning the existing container..." && \
docker container stop fx-app && docker container rm fx-app && \

echo "#3 Building the container..." && \
docker buildx build -t fx-app . && \

echo "#4 Running the App..." && \
docker run --name fx-app -d -p 8080:8080 fx-app && \

echo "#5 Your App is running..."
