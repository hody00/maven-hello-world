### **Bootstrap Guide**
This guide outlines the steps to configure and deploy **MyApp** on Kubernetes.

### **Prerequisites**
1. **Kubernetes Cluster**: Ensure you have a running Kubernetes cluster.
2. **Command Line Tools**: Make sure you have `helm`, `mvn`, and `java` installed and configured.
3. **DockerHub Credentials**: You will need a DockerHub token to pull the private Docker image.
4. **GitHub Access**: The project's repository is public on GitHub.

### **Java Application Overview**
The Java application is designed to print a message using three different methods:
1. **Hardcoded**: The message is embedded directly in the source code.
2. **Properties File**: The message is retrieved from a properties file (`app.properties`).
3. **CLI Arguments**: The message can be passed as command-line arguments when running the application.

### **Testing Setup**
After running the `mvn package` command, there are automated tests to validate the application’s behavior:
- Property Validation: The application ensures that only properties from the `app.properties` file are accepted, with the property name being `app.message`.
- Output Validation: The tests verify that the output is correct for each of the three approaches as mentioned above.

### **Dynamic Versioning**
In your `pom.xml`, the version of the application can be dynamically set using a revision property. This ensures the version is automatically updated based on changes, without the need to manually edit the `version` tag for every new build.
```xml
<properties>
    <revision>1.0.0</revision> <!-- Automatically set the version here -->
</properties>
<version>${revision}</version>
```

### CI/CD Process
This project includes a CI/CD process with two main jobs:

1. Build Job: This job:
  * Generates the necessary artifacts.
  * Uploads them to the required storage (e.g., GitHub).
  * Runs tests to validate the functionality of the application.
  * Updates the version in the pom.xml and commits the changes.

2. Deploy Job: This job:
  * Builds the Docker image.
  * Pushes the image to a private DockerHub repository.

### **Helm Chart Deployment**

The application is deployed using a **Helm chart**, which is highly configurable. Several values can be overridden in the `values.yaml` file, providing flexibility for different environments:

- **Image Tag**: Specify the version/tag of the Docker image.
- **Entry Point**: Customize the entry point to adjust how the application starts.
- **CMD Arguments**: Pass custom arguments to the application at runtime.
- **Scheduling**: Configure scheduling options such as `affinity`, `tolerations`, and `nodeSelector` for better resource management.
- **app.properties file**: The `app.properties` file is mounted as a volume, and it can be easily overridden in the `values.yaml` file. This decouples the application logic from its configuration, making it easier to manage across different environments.

#### **Two Main Approaches for Running the Application**

1. **Run and Restart**: Let the application finish its execution and restart. You can view the output of the default values (e.g., hardcoded message or properties) in the logs.
2. **Run Continuously**: Modify the entry point and CMD arguments to include a `sleep` or `while` loop, allowing the application to run continuously without restarting. This is ideal for long-running services or monitoring tasks.

**Note**: The Helm chart assumes a pre-existing Kubernetes secret with access to the private DockerHub repository. (The next section will explain how to create the secret.)

### **Commands**

Here are some essential commands for managing the application:

```bash
# Set a dynamic version in the pom.xml
mvn versions:set-property -Dproperty="revision" -DnewVersion=1.0.1-SNAPSHOT

# Retrieve the current project version
mvn help:evaluate -Dexpression=project.version -q -DforceStdout

# Clean, compile, and package the application
mvn clean compile
mvn package -DskipTests

# Run tests with a specific jar file
mvn test -DjarFilePath=target/myapp-1.0.1-SNAPSHOT.jar

# Run the application with command-line arguments
java -cp target/classes com.myapp.App "cli args"
java -jar target/myapp-1.0.1-SNAPSHOT.jar "cli1" "cli2"

# Get the next major version of the project
mvn build-helper:parse-version help:evaluate -Dexpression="parsedVersion.nextMajorVersion" -q -DforceStdout

# Build the Docker image with a specific tag
docker build --build-arg IMAGE_TAG=1.0.1-SNAPSHOT -t hody00/maven-hello-world:1.0.1-SNAPSHOT .

# Run the Docker container interactively with a custom entry point
docker run -it --rm --entrypoint=/bin/sh hody00/maven-hello-world:1.0.1-SNAPSHOT

# Run the Docker container with command-line arguments
docker run -it --rm hody00/maven-hello-world:1.0.1-SNAPSHOT "arg1" "arg2"

# Create the Kubernetes Docker registry secret
kubectl create secret -n default docker-registry dockerhub-secret \
  --docker-username=hody00 \
  --docker-password="dckr_pat_***" \
  --dry-run=client -o yaml > secret.yaml

# Deploy the Helm chart (upgrade if installed, install if not)
helm upgrade --install hello-world -n default .

# Check the logs of the deployed application
kubectl logs deploy -n default -f
```
