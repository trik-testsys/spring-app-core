[![Build and Test](https://github.com/trik-testsys/spring-app-core/actions/workflows/build.yml/badge.svg)](https://github.com/trik-testsys/spring-app-core/actions/workflows/build.yml)

# Spring App Core

A core Spring Boot parent dependency that provides a complete foundation for Spring Boot applications with common dependencies and configurations.

## Overview

Spring App Core is a Maven multi-module project that provides a standardized foundation for Spring Boot applications. It includes pre-configured dependencies, security settings, database configurations, and common utilities to accelerate development of new Spring Boot applications.

## Project Structure

```
spring-app-core/
├── core-commons/          # Core common library with shared utilities
├── core-ui/               # UI components
├── demo-app/              # Example application demonstrating usage
├── pom.xml                # Parent POM with common dependencies
└── README.md              # This file
```

### Modules

- **core-commons**: Contains shared utilities, configurations, and common Spring Boot components
- **core-ui**: UI-related components and frontend assets
- **demo-app**: Example application showing how to use the core modules

## Features

### Core Dependencies
- `Spring Boot` 3.5.4
- `Spring Security`
- `Spring Data JPA & JDBC`
- `Spring Data REST`
- `Spring Boot Actuator`
- `Kotlin` 2.1.20
- `Java` 17

### Database Support
- MySQL (primary database)
- H2 (for testing)
- JPA/Hibernate with automatic schema updates
- Connection pooling with HikariCP

### Security
- Spring Security integration
- Session management
- JDBC-based session storage

### Monitoring & Management
- Spring Boot Actuator for health checks and metrics
- Comprehensive logging configuration
- Error handling and custom error pages

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL (for production) or H2 (for development)

### Environment Variables
The application uses the following environment variables (with defaults):

```bash
# Database Configuration
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USERNAME=testsys
MYSQL_PASSWORD=p@ssw0rd

# Server Configuration
CONTEXT_PATH=/
LOG_FILE=./logs/server.log
```

### Running the Demo Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/trik-testsys/spring-app-core.git
   cd spring-app-core
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the demo application**
   ```bash
   ./mvnw -pl demo-app spring-boot:run
   ```

4. **Access the application**
   - Main application: http://localhost:8888
   - Actuator endpoints: http://localhost:8888/actuator

## Using Spring App Core in Your Project

### 1. Add as Parent POM

```xml
<parent>
    <groupId>trik.testsys</groupId>
    <artifactId>spring-app-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>
```

### 2. Include Core Commons

```xml
<dependency>
    <groupId>trik.testsys.sac</groupId>
    <artifactId>core-commons</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 3. Add `spring-boot-maven-plugin` with `mainClass` configured

```xml
<build>
  <plugins>
      <plugin>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
          <configuration>
              <mainClass>trik.testsys.sac.application.TSApp</mainClass>
          </configuration>
      </plugin>
  </plugins>
</build>

```

## Configuration

### Database Configuration
The application is pre-configured for MySQL with the following settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/testsys_db
    username: testsys
    password: p@ssw0rd
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    open-in-view: on
```

### Server Configuration
- Port: 8888
- Context path: Configurable via `CONTEXT_PATH` environment variable
- Session timeout: 3 hours

### Logging
- Log file: Configurable via `LOG_FILE` environment variable
- Log retention: 31 days
- Default log level: DEBUG for `trik.testsys` packages

## Development

### Building
```bash
# Build all modules
./mvnw clean install

# Build specific module
./mvnw -pl core-commons clean install

# Run tests
./mvnw test
```

### Testing
The project includes comprehensive test coverage. Run tests with:
```bash
./mvnw test
```

### Code Style
- Kotlin code style: Official
- Java version: 17
- Kotlin JVM target: 1.8

## CI/CD

The project includes GitHub Actions workflows for:
- **Build and Test**: Automated builds and testing on pull requests
- **Release**: Automated releases with version management
- **Version Updates**: Automated version bumping for patches and releases

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b dev/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin dev/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Authors

- **Roman Shishkin** - *Initial work* - [romashkin.2001@yandex.ru](mailto:romashkin.2001@yandex.ru)
- **Vyacheslav Buchin** - *Contributor* - [slava.slava2003@mail.ru](mailto:slava.slava2003@mail.ru)

## Support

For support and questions, please contact the development team or create an issue in the GitHub repository.

