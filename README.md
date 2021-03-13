# papyri

Papyri is a tool to fetch, manage and own your data.

## Technical Overview 

It is a monorepo, consisting of API and UI microservices.

The API is a Micronaut, Java, and Gradle microservice, and the UI is a very stripped-down React app.

It is written using functional reactive programming (FRP) using Java, Vavr and JavaScript.

It is deployed onto a standalone Linux server with Docker, as well as onto a bare bones Kubernetes instance. It includes a development environment using Kubernetes (Minikube).

## Getting Started 

1. Clone this repo.

1. Install Java (_On Mac_: `brew install java`, _On Linux_: https://sdkman.io/install then `sdk install java`)

### Starting the API

1. `cd api`
1. Build and run: `./gradlew build run`

### Starting the API

1. Navigate to the `ui-pure/index.html` file

## Deployment 

The plan is to use Kubernetes to closely mirror the development environment from production (within reason!). For now, NGINX on a Linux box will be used.

A GitOps-style setup with ArgoCD will be evaluated for Kubernetes.  

# Contributing

//todo

## Development Norms

//todo

# Code of Conduct

//todo

# Reference

## Built using Micronaut

### Micronaut 2.4.0 Documentation

- [User Guide](https://docs.micronaut.io/2.4.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

### Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

