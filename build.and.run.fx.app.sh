#!/usr/bin/env sh

echo "#1 Building the App..." && \
./gradlew clean build && \

cp ./build/libs/fx-1.0.0.jar ./fx-app.jar && \

echo "#2 Starting the App..." && \
java -jar ./fx-app.jar
