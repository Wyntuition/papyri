## TODO - build jar?

##TODO switch gradle image so doesn't re-download gradle etc
#FROM openjdk:15-jdk-alpine
FROM ghcr.io/graalvm/graalvm-ce:latest as graalvm
RUN gu install native-image
COPY . /usr/bin/papyri
WORKDIR /usr/bin/papyri
#RUN ./gradlew build
RUN native-image --no-server -cp build/libs/*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
COPY --from=graalvm /usr/bin/papyri/build/libs /usr/bin/papyri
CMD ["/usr/bin/papyri/papyri", "-Xmx68m"]
