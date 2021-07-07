# papyri api

## Build & run the API 

1. Type `./gradlew build run`. 
1. View the [API via Swagger](http://localhost:8080/swagger/views/swagger-ui/)
1. Try it in Swagger and hitting an endpoint, i.e., 

    `curl http://localhost:8080/crawler/apis`

1.Ensure the app has access to these via *environment variables*:
   ```bash 
   SPOTIFY_CLIENT_ID=<ENTER>
   SPOTIFY_CLIENT_SECRET=<ENTER>
   ```
