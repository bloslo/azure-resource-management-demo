# Azure Resource Management

A sample Spring Boot application that can create a virtual machine
or an Azure Kubernetes Service (AKS).

## Build Docker image

To build a Docker image run the following command:

```shell
./mvnw clean package jib:dockerBuild
```

Run the newly built image:

```shell
docker run -p 8080:8080 bloslo/azure-resource-management
```

## Run

In order to run the application without building a container, execute the
following command:

```shell
./mvnw spring-boot:run
```
