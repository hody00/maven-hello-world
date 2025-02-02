### Solution Explanation

#### **Step 1 - update application code**
##### **Changes to `pom.xml`**:
1. **Version Update**: Updated version from `1.0-SNAPSHOT` to `1.0.0`.
2. **Java Version Update**: Updated Java version from `1.7` to `21`.
3. **Added `exec-maven-plugin`**: Included to run the application directly through Maven with the `java` goal.

##### **Changes to `App.java`**:
1. **Hard-Coded Message**: Prints a default message: `"Hello World! from: Hodaya"`.
2. **Properties File**: Loads `app.properties` to read the `app.message` property, e.g., `"Hello World! from app.properties"`.
3. **CLI Arguments**: If arguments are passed, they are appended to the default message, e.g., `"Hello World! from: cli args"`.

##### **Addition of `app.properties`**:
1. **Properties File**: Added `app.properties` with `app.message=App Properties` to externalize configuration.

#### Example Usage
To compile and set custom values:
```bash
mvn clean compile
java -cp target/classes com.myapp.App "cli args"
```
