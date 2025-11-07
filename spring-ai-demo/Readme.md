# Spring_ai_demo

## Description
Spring_ai_demo is a Spring Boot application demonstrating integration with OpenAI's GPT models using the Spring AI framework. It provides REST endpoints to interact with AI models and includes configuration for chat, memory, and vector support.

This module supports both in-memory and PostgreSQL-based vector stores for embedding storage and retrieval.

## Project Structure
```code
src/main/java/com/example/spring_ai_demo/ 
├── SpringAiDemoApplication.java 
├── config/ 
│   ├── ChatClientConfig.java 
│   ├── MemoryConfig.java 
│   └── VectorConfig.java 
├── controller/ 
│   └── AIController.java 
└── service/ 
    ├── CICDTools.java 
    └── LogLoader.java 
src/main/resources/ 
├── application.properties
├── dev-docs/
│   ├── backend_build_failed.log
│   ├── commit_history.log
│   ├── deployment_failed.log
│   ├── deployment_success.log
│   ├── docker_build_failed.log
│   ├── Dockerfile
│   ├── jenkins_pipeline.log
│   ├── merge_conflict.log
│   ├── OrderServiceTest_failed.log
│   └── UserControllerTest_failure.log
├── prompt/
│   └── build-analysis.st
├── static/
└── templates/
```

## Requirements
- Java 17+
- Maven 3.8+
- OpenAI API Key
- (Optional) PostgreSQL database for persistent vector store

## Vector Store Options

### In-Memory Vector Store (Default)
The application is pre-configured to use an in-memory vector store for quick prototyping and development. No additional setup is required.

### PostgreSQL Vector Store Example
To use PostgreSQL as a persistent vector store, follow these steps:

1. Add the following dependencies to your `pom.xml`:
    ```xml
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-pgvector-store</artifactId>
        <version>0.8.0</version> <!-- Use the latest compatible version -->
    </dependency>
    ```

2. Update your `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    spring.ai.vectorstore.type=pgvector
    spring.ai.vectorstore.pgvector.table-name=ai_vectors
    spring.ai.vectorstore.pgvector.dimension=1536 # or your embedding dimension
    ```

3. Example Java Configuration (`VectorConfig.java`):
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
    import javax.sql.DataSource;

    @Configuration
    public class VectorConfig {
        @Bean
        public PgVectorStore pgVectorStore(DataSource dataSource) {
            return new PgVectorStore(dataSource, "ai_vectors", 1536); // adjust dimension as needed
        }
    }
    ```

4. Ensure your PostgreSQL database has the `pgvector` extension enabled:
    ```sql
    CREATE EXTENSION IF NOT EXISTS vector;
    ```

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
- Choose your vector store (in-memory or PostgreSQL) as described above.

## License
This project is for demonstration purposes only.
