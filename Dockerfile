FROM bellsoft/liberica-openjre-alpine
ARG JAR_FILE=build/libs/fx-1.0.0.jar
WORKDIR /openpayd
COPY ${JAR_FILE} fx-app.jar
ENTRYPOINT ["java","-jar","/openpayd/fx-app.jar"]
