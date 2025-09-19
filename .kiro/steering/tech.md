# Technology Stack

## Core Framework

- **Spring Boot 3.5.0** - Main application framework
- **Java 21** - Programming language and runtime
- **Maven** - Build system and dependency management

## Key Dependencies

- **Spring WebSocket** - WebSocket support for real-time communication
- **Lombok 1.18.38** - Code generation for boilerplate reduction
- **Spring Boot Test** - Testing framework

## Build System

Maven is used for dependency management and building. Key files:

- `pom.xml` - Project configuration and dependencies
- `mvnw` / `mvnw.cmd` - Maven wrapper scripts

## Common Commands

### Building and Running

```bash
# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Package the application
./mvnw package

# Run the application
./mvnw spring-boot:run

# Clean build artifacts
./mvnw clean
```

### Development

```bash
# Run in development mode with auto-reload
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Skip tests during build
./mvnw package -DskipTests
```

## Development Environment

- **DevContainer** support with Java 21 and Maven
- **VS Code** extensions for Java development, Spring Boot, and Lombok
- **Docker** container for consistent development environment

## Code Style

- Use **Lombok** annotations to reduce boilerplate code
- Follow Spring Boot conventions and patterns
- WebSocket handlers for real-time communication
