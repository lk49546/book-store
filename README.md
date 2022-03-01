# BookStore

## Instalation guide

1. ./mvnw clean install
2.  docker-compose --env-file ./.env up -d 

## Usage guide

### Swagger links:

- Book Inventory: http://localhost:8082/webjars/swagger-ui/index.html
- Book Purchase: http://localhost:8083/webjars/swagger-ui/index.html
- Loyalty Program: http://localhost:8084/webjars/swagger-ui/index.html

## Architecture

### Book Inventory

![alt text](https://i.ibb.co/FhT7RLM/Package-book.png)

### Book Purchase

![alt text](https://i.ibb.co/3zbMSCZ/Order.png)

### Loyalty Program

![alt text](https://i.ibb.co/x8wZM6b/Package-loyalty.png)
