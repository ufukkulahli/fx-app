# FX APP

---

## API Use Documentation

See the file located at `docs/API.md`

---

## Logs

See your console for the logs!

---

## Running the App

`You need 'jre' to run the app first!`

You can run the App which already shipped (a jar file) with this project folder.

First, allow script to run:
```
chmod +x run.fx.app.sh
```

Then, run the App:
```
./run.fx.app.sh
```

---

## Building and Running the App

`You need 'Gradle' and 'jre' to run the app first!`

You can build the App from the source and run it as a jar file.

First, allow script to run:
```
chmod +x build.and.run.fx.app.sh
```

Then, run the App:
```
./build.and.run.fx.app.sh
```

---
## Running the App With Docker

`You need 'Docker' and 'Gradle' tools to build and run the app first!`

There is a script in place for running the App called `run.fx.app.docker.sh` for convenience.
It is in the externalRateResponse directory of the project.

First, allow script to run:
```
chmod +x ./run.fx.app.docker.sh
```



Then, run the App:
```
./run.fx.app.docker.sh
```

---

## Ping the App:

See if everything is OK!

Request:
```
curl http://localhost:8080/api/v1/healthcheck
```

Response:
```
{
    "IsExternalFXAPIOK": true,
    "IsInMemoryDBOK": true
}
```

---

## API Interaction and Documentation with Swagger

After running the App (any preferred way) just hit the endpoint in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

---

## H2 In-Memory DB Interaction

After running the App (any preferred way) just hit the endpoint in your browser:

```
http://localhost:8080/h2-console
```

Credentials are:

```
JDBC URL: jdbc:h2:mem:fxappdb
User Name: sa
Password: (empty)
```

You will see tables:
- `currency_conversion` that holds the records for history purposes that you can query.
- `fxrate_cache` that holds the rates for faster serving without futher external API calls.
