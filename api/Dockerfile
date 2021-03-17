# Builds native image for GraalVM
FROM oracle/graalvm-ce:20.1.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/papyri
WORKDIR /home/app/papyri

RUN native-image --no-server -cp build/libs/papyri-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/papyri/papyri /app/papyri
ENTRYPOINT ["/app/papyri"]
