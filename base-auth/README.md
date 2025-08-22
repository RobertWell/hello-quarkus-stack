# Quarkus Basic Authentication Server

A Quarkus server implementation with basic HTTP authentication protecting API endpoints.

## Features

- **Protected API endpoints** under `/api/*` requiring basic authentication
- **Public health check endpoints** at `/health/*` for monitoring
- **Prometheus metrics** at `/q/metrics` for observability
- **Kotlin implementation** with Quarkus framework

## Authentication

- **Username:** `admin`
- **Password:** `admin`

## Endpoints

### Protected Endpoints (Require Authentication)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/introduction` | GET | Welcome message and service info |
| `/api/status` | GET | API status and authentication confirmation |
| `/api/info` | GET | Application information and available endpoints |

### Public Endpoints (No Authentication Required)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/health` | GET | Overall health status |
| `/health/ready` | GET | Readiness probe |
| `/health/live` | GET | Liveness probe |
| `/q/metrics` | GET | Prometheus metrics |

## Usage

### Start the Server

```bash
mvn quarkus:dev
```

The server will start on `http://localhost:8080`

### Test Protected Endpoints

```bash
# This will fail with 401 Unauthorized
curl http://localhost:8080/api/introduction

# This will succeed
curl -u admin:admin http://localhost:8080/api/introduction
```

### Test Public Endpoints

```bash
# These work without authentication
curl http://localhost:8080/health
curl http://localhost:8080/health/ready
curl http://localhost:8080/health/live
curl http://localhost:8080/q/metrics
```

### Run Automated Tests

A Python test script is provided to test all endpoints:

```bash
python3 test_api.py
```

Optional parameters:
```bash
python3 test_api.py --url http://localhost:8080 --username admin --password admin
```

## Configuration

The basic authentication is configured in `src/main/resources/application.properties`:

```properties
# Basic Authentication Configuration
quarkus.security.users.embedded.enabled=true
quarkus.security.users.embedded.plain-text=true
quarkus.security.users.embedded.users.admin=admin
quarkus.security.users.embedded.roles.admin=api-user
```

## Dependencies

- Quarkus 3.20.1
- Kotlin 1.9.24
- SmallRye Health for health checks
- Micrometer for Prometheus metrics
- Elytron Security for basic authentication