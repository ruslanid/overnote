```
spring:
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  datasource:
    url: "jdbc:postgresql://localhost/<database_name>"
    username:
    password:
    initialization-mode: never
```
