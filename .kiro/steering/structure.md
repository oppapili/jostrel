# Project Structure

## Root Directory

```
jostrel/
├── src/                    # Source code
├── target/                 # Build output (generated)
├── .mvn/                   # Maven wrapper files
├── .devcontainer/          # Development container configuration
├── .github/                # GitHub workflows and templates
├── .kiro/                  # Kiro AI assistant configuration
├── pom.xml                 # Maven project configuration
├── mvnw / mvnw.cmd        # Maven wrapper scripts
└── .gitignore             # Git ignore rules
```

## Source Code Organization

```
src/
├── main/
│   ├── java/io/github/oppapili/jostrel/
│   │   ├── JostrelApplication.java     # Main Spring Boot application
│   │   ├── config/                     # Configuration classes
│   │   │   └── WebSocketConfig.java    # WebSocket configuration
│   │   ├── handler/                    # Request/WebSocket handlers
│   │   │   └── WebSocketHandler.java   # Main WebSocket handler
│   │   ├── model/                      # Data models and DTOs
│   │   │   ├── Event.java              # Nostr event model
│   │   │   ├── Filter.java             # Event filter model
│   │   │   ├── Message.java            # WebSocket message model
│   │   │   ├── MessageDeserializer.java # JSON deserialization
│   │   │   ├── MessageType.java        # Message type enumeration
│   │   │   └── Subscription.java       # Subscription model
│   │   └── service/                    # Business logic services
│   │       └── SubscriptionManager.java # Manages client subscriptions
│   └── resources/
│       └── application.properties      # Application configuration
└── test/
    └── java/io/github/oppapili/jostrel/ # Test classes (mirrors main structure)
```

## Package Conventions

- **Base package**: `io.github.oppapili.jostrel`
- **config**: Spring configuration classes and beans
- **handler**: WebSocket and HTTP request handlers
- **model**: Data transfer objects, entities, and domain models
- **service**: Business logic and service layer components

## Naming Conventions

- Classes use PascalCase (e.g., `WebSocketHandler`)
- Packages use lowercase with dots (e.g., `io.github.oppapili.jostrel`)
- Configuration files use kebab-case when applicable
- Follow Spring Boot naming conventions for components

## Key Files

- `JostrelApplication.java` - Main application entry point
- `WebSocketConfig.java` - WebSocket endpoint configuration
- `WebSocketHandler.java` - Handles Nostr protocol WebSocket messages
- `SubscriptionManager.java` - Manages client subscriptions and filtering
- Model classes represent Nostr protocol entities (Event, Filter, etc.)
