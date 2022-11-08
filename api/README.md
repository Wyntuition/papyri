# papyri api

See [the top-level README](../README.md)

## Build & run the API Quickstart 

1. Import the needed environment variables, i.e. in `.secrets` file
2. Type `./gradlew build run` or via the Gradle pane in IntelliJ (recommended)
1. View the [API via Swagger](http://localhost:8080/swagger/views/swagger-ui/)
2. Get an updated access token: Navidate to `http://localhost:8080/auth/login` and use response
1. Try it in Swagger or hitting an endpoint, i.e. `curl http://localhost:8080/crawler/apis`

Note: for now you have to manually start a RabbitMQ container if using messages: 

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```