docker build -t test-papyri-api .

docker run -p 80:8080 test-papyri-api ./gradlew run
