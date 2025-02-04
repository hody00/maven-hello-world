FROM openjdk:21-jdk-slim
WORKDIR /app

ARG COPY_APP_PROPERTIES="false"
ARG IMAGE_TAG
ENV IMAGE_TAG=${IMAGE_TAG:-latest}
ENV JAR_FILE="myapp-${IMAGE_TAG}.jar"

# Non-root user for security
RUN addgroup --system maven && adduser --system --ingroup maven hello
USER hello

# Copy the pre-built JAR from the CI/CD pipeline and app.properties file if exist
COPY "artifacts/$JAR_FILE" "myapp.jar"
RUN if [ "$COPY_APP_PROPERTIES" = "true" ]; then cp app.properties app.properties; fi

# ENTRYPOINT ["sh", "-c", "sleep 300"]
ENTRYPOINT ["java", "-jar", "myapp.jar"]
CMD ["default-cli-arg"]
