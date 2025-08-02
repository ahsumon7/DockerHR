# HR Application Docker Setup

This project contains a Dockerized HR Application with the following services:
- PostgreSQL database (`pgdbhr`)
- Spring Boot backend (`hr-backend`)
- Angular frontend served via NGINX (`hr-frontend`)

---

## Prerequisites

Make sure you have the following installed on your machine:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## How to Run the Application

1. **Clone the repository**

```bash
git clone https://github.com/ahsumon7/DockerHR.git
cd DockerHR
docker-compose up --build
