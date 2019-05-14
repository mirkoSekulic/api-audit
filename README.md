# Api audit

Api audit is a spring boot demo for logging api calls in database using spring aop. We choose which method to audit with *@Apiaudit* annotation. We save information about method name that is called, ip address, request type, user login and roles, method arguments,etc. Endpoint for audits is /audits .

## Installation

Use command:
```bash
mvn clean install -DskipTests
```

## Authentification

*login*: admin, password: *test1234*\
*login*: user, password: *test1234*



## Swagger
Swagger documentation is available on endpoing */api/swagger-ui.html* in dev profile.
