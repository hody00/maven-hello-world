FROM openjdk:21-jdk-slim
WORKDIR /app

ARG IMAGE_TAG
ENV IMAGE_TAG=${IMAGE_TAG:-latest}

LABEL RELEASE_VERSION="${IMAGE_TAG}"

# Non-root user for security
RUN addgroup --system maven && adduser --system --ingroup maven hello
USER hello

# Copy the pre-built JAR from the CI/CD pipeline and app.properties file if exist
COPY "target/myapp-${IMAGE_TAG}.jar" "myapp.jar"
COPY "*.properties" .

# ENTRYPOINT ["sh", "-c", "sleep 300"]
ENTRYPOINT ["java", "-jar", "myapp.jar"]
CMD ["default-cli-arg"]


# java -jar myapp.jar "cli args" "test"
# docker build --build-arg IMAGE_TAG=1.1.0-SNAPSHOT -t hody00/maven-hello-world:1.1.0-SNAPSHOT .
# docker run -it --rm -v $(pwd)/app.properties:/app/app.properties hody00/maven-hello-world:1.2.3
# docker run -it --rm -v $(pwd)/app.properties:/app/app.properties hody00/maven-hello-world:1.2.3 "test"
