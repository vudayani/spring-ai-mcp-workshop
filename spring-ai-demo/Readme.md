# Spring_ai_demo

## Description
Spring_ai_demo is a Spring Boot application demonstrating integration with OpenAI's GPT models using the Spring AI framework. It provides REST endpoints to interact with AI models and includes configuration for chat, memory, and vector support.

## Project Structure

src/main/java/com/example/spring_ai_demo/ 
├── SpringAiDemoApplication.java 
├── config/ 
│ ├── ChatClientConfig.java 
│ ├── MemoryConfig.java 
│ └── VectorConfig.java 
├── controller/ 
│ └── AIController.java 
└── service/ 
├── CICDTools.java 
└── LogLoader.java 
src/main/resources/ 
└── application.properties

## Requirements
- Java 17+
- Maven 3.8+
- OpenAI API Key

## Setup

1. Clone the repository.
2. Set your OpenAI API key in `src/main/resources/application.properties`:
    ```
    spring.ai.openai.api-key=<YOUR_OPENAI_API_KEY>
    ```
3. Build the project:
    ```
    mvn clean install
    ```
4. Run the application:
    ```
    mvn spring-boot:run
    ```

## Usage
- The application exposes REST endpoints (see `AIController.java`) for interacting with the AI model.
- Configure model and other options in `application.properties`.

## License
This project is for demonstration purposes only.
