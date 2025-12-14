# University SOA – Service-Oriented University Management System


University SOA is an academic project that implements a complete **University Management System** based on **Service-Oriented Architecture (SOA)** principles. The system is built using distributed services that communicate through **REST and SOAP APIs**, secured with **JWT authentication**, and deployed using **Docker and Docker Compose**. This project was developed as part of the **SOA & Web Services module** and aims to demonstrate the design, implementation, and deployment of a scalable and loosely coupled service-based architecture.

By separating business domains into independent services, University SOA ensures interoperability between heterogeneous technologies while improving maintainability, scalability, and security in a university information system.

## System Architecture

The architecture of University SOA follows a layered and service-oriented approach. A **client layer** (Postman or a future frontend application) interacts with the system through a centralized **API Gateway** implemented using **Spring Cloud Gateway**. The API Gateway serves as a single entry point, handling request routing and service exposure.

Behind the gateway, the system is composed of multiple independent services, each responsible for a specific business domain. These services include an **Authentication Service** developed with Spring Boot and JWT security, a **Student Service** implemented with Node.js and REST APIs, a **Grade Service** also implemented with Node.js and REST APIs, a **Course Service** developed with Spring Boot using SOAP web services, and a **Billing Service** developed with Spring Boot using SOAP. Each service is containerized and communicates over HTTP, ensuring loose coupling and independent scalability.

## Technology Stack


<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white"/>
  <img src="https://img.shields.io/badge/Express.js-000000?style=for-the-badge&logo=express&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/REST%20API-02569B?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/SOAP%20Web%20Services-5A5A5A?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/API%20Gateway-Spring%20Cloud-6DB33F?style=for-the-badge"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker%20Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
</p>

# Clone the project
git clone https://github.com/HMotez/University-SOA.git

cd University-SOA


# Run services individually


# Authentication Service
cd services/auth-service

mvn spring-boot:run


# Course Service (SOAP)
cd services/course-service

mvn spring-boot:run


# Billing Service (SOAP)
cd services/billing-service

mvn spring-boot:run



# Student Service (REST)
cd services/student-service

npm install

npm start


# Grade Service (REST)
cd services/grade-service

npm install

npm start


# API Gateway
cd services/api-gateway

mvn spring-boot:run



# Run all services with Docker


cd docker

docker-compose up --build


## Testing and Validation

All REST and SOAP services were thoroughly tested using **Postman** to validate request routing, authentication mechanisms, and service responses. Test screenshots and validation evidence are available in the `tests/` directory of the repository, demonstrating correct behavior and successful interactions between services through the API Gateway.



## Postman API Testing (Evidence)

All services of the **University SOA System** were validated using **Postman** to ensure correct behavior, secure communication, and proper request routing through the API Gateway. The screenshots below are taken directly from the `Tests/` directory and demonstrate successful executions for REST and SOAP services.

### Auth Service – Postman Tests

<p align="center">
  <img src="Tests/Auth-Service/Screenshot%202025-12-11%20200049.png" width="30%" />
  <img src="Tests/Auth-Service/Screenshot%202025-12-11%20200753.png" width="30%" />
  <img src="Tests/Auth-Service/Screenshot%202025-12-11%20201057.png" width="30%" />
</p>

---

### Billing Service – Postman Tests

<p align="center">
  <img src="Tests/Billing-Service/Screenshot%202025-12-11%20212425.png" width="30%" />
  <img src="Tests/Billing-Service/Screenshot%202025-12-11%20212522.png" width="30%" />
  <img src="Tests/Billing-Service/Screenshot%202025-12-11%20212625.png" width="30%" />
</p>

---

### Course Service – Postman Tests

<p align="center">
  <img src="Tests/Course-service/Screenshot%202025-12-11%20203052.png" width="30%" />
  <img src="Tests/Course-service/Screenshot%202025-12-11%20203221.png" width="30%" />
  <img src="Tests/Course-service/Screenshot%202025-12-11%20204217.png" width="30%" />
</p>

---

### Grade Service – Postman Tests

<p align="center">
  <img src="Tests/Grade-Service/Screenshot%202025-12-11%20201926.png" width="30%" />
  <img src="Tests/Grade-Service/Screenshot%202025-12-11%20202031.png" width="30%" />
  <img src="Tests/Grade-Service/Screenshot%202025-12-11%20202115.png" width="30%" />
</p>

---

### Docker Deployment – Validation Tests

<p align="center">
  <img src="Tests/Docker/Screenshot%202025-12-11%20225433.png" width="40%" />
  <img src="Tests/Docker/Screenshot%202025-12-11%20225544.png" width="40%" />
</p>

---

All test screenshots are organized by service inside the `Tests/` directory to ensure clarity, traceability, and professional documentation of the system validation process.




