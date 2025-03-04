# Microservices Deployment

This repository contains three microservices: Account Service, Marketplace Service, and Wallet Service. Each service is containerized using Docker and can be deployed using Minikube.

## Change
Don't forget to comment/uncomment the relevant portions from `application.properties` to run either in `minikube`, `docker` or `balance`.

## Services

### Account Service

- **Port:** 8080
- **Docker Image:** `account-service:1.0`
- **Description:** Manages user accounts and authentication.

### Marketplace Service

- **Port:** 8081
- **Docker Image:** `marketplace-service:1.0`
- **Description:** Manages marketplace operations such as listing and purchasing items.

### Wallet Service

- **Port:** 8082
- **Docker Image:** `wallet-service:1.0`
- **Description:** Manages user wallets and transactions.

## Prerequisites

- Docker
- Minikube
- kubectl

## Deployment

### Using Minikube

1. Start Minikube:
    ```sh
    minikube start --driver=docker
    ```

2. Set Minikube Docker environment:
    ```sh
    eval $(minikube -p minikube docker-env)
    ```

3. Build Docker images:
    ```sh
    cd account-service
    docker build -t account-service:1.0 .
    cd ..

    cd marketplace-service
    docker build -t marketplace-service:1.0 .
    cd ..

    cd wallet-service
    docker build -t wallet-service:1.0 .
    cd ..
    ```

4. Deploy services:
    ```sh
    kubectl apply -f account-service/deployment.yaml
    kubectl apply -f account-service/service.yaml

    kubectl apply -f marketplace-service/deployment.yaml
    kubectl apply -f marketplace-service/service.yaml
    kubectl apply -f marketplace-service/hpa.yaml

    kubectl apply -f wallet-service/deployment.yaml
    kubectl apply -f wallet-service/service.yaml
    ```

5. Set up port forwarding:
    ```sh
    kubectl port-forward service/account-service 8080:8080 &
    kubectl port-forward service/marketplace-service 8081:8080 &
    kubectl port-forward service/wallet-service 8082:8080 &
    ```

### Using Docker

1. Run Docker containers:
    ```sh
    docker run -p 8080:8080 --rm --name app1 --add-host=host.docker.internal:host-gateway account-service:1.0 &
    docker run -p 8081:8080 --rm --name app2 --add-host=host.docker.internal:host-gateway marketplace-service:1.0 &
    docker run -p 8082:8080 --rm --name app3 --add-host=host.docker.internal:host-gateway wallet-service:1.0 &
    ```

### Using Localhost

```sh
cd account-service
mvn clean package
mvn spring-boot:run

cd marketplace-service
mvn clean package
mvn spring-boot:run

cd wallet-service
mvn clean package
mvn spring-boot:run
```

## Accessing Services

- **Account Service:** http://localhost:8080
- **Marketplace Service:** http://localhost:8081
- **Wallet Service:** http://localhost:8082

## Configuration

Each service has its own [application.properties](http://_vscodecontentref_/0) file located in `src/main/resources/`. These files contain configuration settings such as server port, database URL, and other properties.

## H2 Database

The services use H2 Database for data storage. The H2 Console can be accessed at `/h2-console` endpoint of each service.

## License

This project is licensed under the MIT License.