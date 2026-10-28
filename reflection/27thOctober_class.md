# Class Notes — 27 October 2026

## Core Topics: Java Documentation, Entry Points, Automated Testing, and ServiceLoader

---

## 1. Why Javadoc is Needed

Javadoc is Java's standard documentation format for generating formal API specifications directly from source code comments. Unlike inline comments (`//` or `/* */`), which explain implementation mechanics, Javadoc defines the public contract of a module, class, interface, or method.

### Key Purposes:

* **Separation of Interface Contract from Implementation:**
  * Defines *what* a component does rather than *how* it does it.
  * Developers consuming a library or API can understand parameters, return values, and possible exceptions without reading internal method logic.
* **Standardized HTML Documentation Generation:**
  * The `javadoc` tool (or `mvn javadoc:javadoc`) parses doc comments and builds browsable HTML pages containing class hierarchies, package summaries, and index search.
  * Allows organizations to distribute complete API documentation without sharing proprietary source code.
* **IDE Tooling and Autocompletion Support:**
  * Modern IDEs (IntelliJ IDEA, Eclipse, VS Code) render Javadoc comments directly in tooltips during autocompletion, hover inspections, and parameter hint prompts.
* **Formal Contract Tags:**
  * `@param` — Documents expected inputs, valid bounds, and nullability.
  * `@return` — Specifies output values and special states (e.g., `null`, `Optional.empty()`, negative sentinel values).
  * `@throws` / `@exception` — Documents checked and unchecked exceptions thrown under failure conditions.
  * `@see` / `{@link ...}` — Provides cross-references to related classes or methods.
  * `@deprecated` — Warns developers about outdated APIs and suggests alternatives.

### Example:
```java
/**
 * Calculates the Euclidean distance between two points in a 2D plane.
 *
 * <p>Uses the standard formula: {@code sqrt((x2 - x1)^2 + (y2 - y1)^2)}.</p>
 *
 * @param target the target {@link Point} to compute distance to; must not be null
 * @return the non-negative scalar distance
 * @throws NullPointerException if {@code target} is null
 */
public double distanceTo(Point target) {
    Objects.requireNonNull(target, "Target point cannot be null");
    return Math.hypot(target.x - this.x, target.y - this.y);
}
```

---

## 2. Why the Main Class is Needed (Entry Point / Starter Class)

Every executable Java program requires a designated **Main Class** containing the standard `main` method. It serves as the single deterministic entry point where execution begins.

### The Standard Entry Signature:
```java
public static void main(String[] args)
```

### Signature Breakdown:
* **`public`**:
  * Must be visible to the Java Virtual Machine (JVM) runtime, which operates outside the application's package namespace.
* **`static`**:
  * Allows the JVM to invoke the entry point directly on the class without first instantiating an object. When the JVM starts, no instances exist in memory.
* **`void`**:
  * The method does not return a value to the caller. Process exit codes are communicated to the operating system using `System.exit(int)` when needed.
* **`String[] args`**:
  * Accepts command-line arguments passed from the shell or terminal (e.g., `java App --port=8080 --debug`).

### Role as a Starter / Bootstrapper:
In well-architected applications, the `Main` class functions strictly as an orchestrator or bootstrapper, not a container for business logic:
1. **Validates Inputs & CLI Flags:** Parses runtime arguments and sets initial configuration flags.
2. **Initializes Environment:** Sets up logging, database connections, or dependency injection containers.
3. **Hands Off Control to the Framework/Domain Layer:**
   * **CLI Tools:** Parses flags and calls service controllers.
   * **Swing Applications:** Dispatches UI creation to the Event Dispatch Thread (EDT) via `SwingUtilities.invokeLater()`.
   * **JavaFX Applications:** Delegates to `Application.launch(args)`.
   * **Spring Boot:** Invokes `SpringApplication.run(App.class, args)`.

### Example:
```java
public class SquareAppLauncher {
    public static void main(String[] args) {
        if (args.length > 0 && ("-h".equals(args[0]) || "--help".equals(args[0]))) {
            printUsage();
            return;
        }

        int size = (args.length > 0) ? Integer.parseInt(args[0]) : 200;

        // Transfer control to Swing UI thread
        SwingUtilities.invokeLater(() -> {
            SquareApp app = new SquareApp(size);
            app.setVisible(true);
        });
    }

    private static void printUsage() {
        System.out.println("Usage: java devang.SquareAppLauncher <square-size>");
    }
}
```

---

## 3. Why a Dedicated Test Folder Exists in Java (`src/test`) — Automated Testing

Standard Java build tools (Maven, Gradle) follow the convention of separating production code from test code:

```text
project-root/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/        # Production source code
    │   └── resources/   # Production configuration, assets, FXML
    └── test/
        ├── java/        # Unit and integration test source code
        └── resources/   # Test-only fixtures, mock datasets, dummy configs
```

### Core Reasons for a Dedicated `src/test/` Folder:

* **Clean Packaging (Artifact Isolation):**
  * When compiling and creating the final distribution artifact (JAR or WAR), Maven packages **only** `src/main/`.
  * The `src/test/` folder and test dependencies (JUnit, Mockito, AssertJ) are strictly excluded from the release build. This prevents binary bloat and keeps production distributions lean.
* **Automated Test Execution in Build Pipelines:**
  * Build tools automatically locate and run test classes under `src/test/java/` during the test phase (`mvn test`).
  * If any test fails, the build immediately aborts, preventing defective code from progressing to deployment.
* **Separation of Environments & Zero Classpath Contamination:**
  * Tests often need mock data, test database URLs, or stubs that should never exist in production. Placing them in `src/test/resources/` guarantees they are never packaged into production JARs.
* **Regression Protection:**
  * Automated unit tests provide an immediate verification harness. When classes are refactored or optimized, tests confirm that existing system behavior has not broken.
* **Living Code Specifications:**
  * Unit tests document the expected behavior, valid boundaries, and error cases of production classes with runnable, compiler-checked code examples.

### Example (JUnit 5 Test):
```java
package devang;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    @DisplayName("Should create square when positive dimension is provided")
    void testValidSquare() {
        Square square = new Square(100);
        assertEquals(100, square.getSize());
    }

    @Test
    @DisplayName("Should reject non-positive dimensions")
    void testInvalidSquare() {
        assertThrows(IllegalArgumentException.class, () -> new Square(-10));
    }
}
```

---

## 4. The `<p>` Tag in Javadoc Comments

Javadoc comments (`/** ... */`) are parsed and rendered as **HTML**. Because HTML ignores standard newline characters (`\n`) and collapses consecutive spaces, plain line breaks in source code comments do not produce visual paragraph breaks in generated documentation or IDE popups.

### Function of the `<p>` Tag:
* Defines a **paragraph break** in HTML.
* Inserts vertical spacing between separate blocks of explanatory text.

### Standard Javadoc Structure:
1. **Summary Sentence:**
   * The first sentence (ending with a period) is extracted to build the package/class summary table. It should be concise and does not require a `<p>` tag.
2. **Body Paragraphs:**
   * Detailed explanations, algorithms, preconditions, or usage notes that follow the summary sentence should be preceded by `<p>` (or enclosed in `<p>...</p>`).

### Syntax Comparison:

#### Without `<p>` (Rendered as a single run-on paragraph):
```java
/**
 * Processes financial transactions.
 * Line breaks here are ignored by the Javadoc parser.
 * This all merges into a single continuous block of text in HTML.
 */
```

#### With `<p>` (Rendered with clean paragraph breaks):
```java
/**
 * Processes financial transactions against external payment gateways.
 *
 * <p>This method connects over TLS 1.3 to authenticate merchant credentials.
 * If network timeout limits are exceeded, an automated retry policy executes.</p>
 *
 * <p>All operations are logged for compliance and auditing purposes.</p>
 *
 * @param transaction the transaction payload to submit
 * @return confirmation token from the payment gateway
 */
public String processTransaction(Transaction transaction) {
    // Implementation
}
```

### Common HTML Tags in Javadoc:
| Tag | Purpose | Example |
| :--- | :--- | :--- |
| `<p>` | Paragraph break | `<p>Additional details here.</p>` |
| `<ul>`, `<li>` | Bulleted lists | `<ul><li>First item</li></ul>` |
| `<code>` / `{@code}` | Monospace code text | `{@code true}` or `<code>null</code>` |
| `{@link}` | Clickable type link | `{@link java.util.List}` |
| `<strong>` / `<em>` | Bold and italic formatting | `<strong>Important:</strong>` |

---

## 5. Java ServiceLoader & The Service Provider Interface (SPI)

`java.util.ServiceLoader` (introduced in Java 6 and updated in Java 9 with modules) is Java's built-in mechanism for discovering and loading service implementations dynamically at runtime.

It implements the **Service Provider Interface (SPI)** pattern, enabling **Inversion of Control (IoC)** and **loose coupling**. Applications can interact with an abstract interface without having hardcoded compile-time dependencies on concrete implementation classes.

```
+-------------------------------------------------------------+
|                      Application Code                       |
|           Interacts ONLY with the Service Interface         |
+-------------------------------------------------------------+
                              |
                              | ServiceLoader.load(PaymentService.class)
                              v
+-------------------------------------------------------------+
|                  java.util.ServiceLoader                    |
|   Reads META-INF/services/ or JPMS module declarations      |
+-------------------------------------------------------------+
               /                              \
              v                                v
+----------------------------+   +----------------------------+
|    StripePaymentService    |   |    PayPalPaymentService    |
| (Classpath Implementation) |   | (Classpath Implementation) |
+----------------------------+   +----------------------------+
```

### The 4 Core Components of Java SPI:

1. **Service Interface (The Contract):**
   * A public interface or abstract class defining what functionality must be provided.
2. **Service Provider (Concrete Implementation):**
   * A class implementing the interface with a public, no-argument constructor (required for reflective instantiation).
3. **Provider Configuration (Registration):**
   * **Classpath:** A file located at `src/main/resources/META-INF/services/<fully.qualified.InterfaceName>` listing concrete implementation classes.
   * **Java 9+ Modules (JPMS):** Configured in `module-info.java` using `uses <Interface>;` and `provides <Interface> with <Implementation>;`.
4. **ServiceLoader Discovery Mechanism:**
   * The application calls `ServiceLoader.load(Interface.class)` to discover and lazily instantiate providers at runtime.

### Step-by-Step Implementation:

#### Step 1: Define the Service Interface
```java
package devang.service;

public interface NotificationService {
    String getChannelName();
    void sendNotification(String recipient, String message);
}
```

#### Step 2: Implement the Service Interface
```java
package devang.service.impl;

import devang.service.NotificationService;

public class EmailNotificationService implements NotificationService {
    @Override
    public String getChannelName() {
        return "EMAIL";
    }

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Dispatching Email to " + recipient + ": " + message);
    }
}
```

```java
package devang.service.impl;

import devang.service.NotificationService;

public class SmsNotificationService implements NotificationService {
    @Override
    public String getChannelName() {
        return "SMS";
    }

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Dispatching SMS to " + recipient + ": " + message);
    }
}
```

#### Step 3: Register Providers in `META-INF/services`
Create a text file at:
`src/main/resources/META-INF/services/devang.service.NotificationService`

File Content:
```text
devang.service.impl.EmailNotificationService
devang.service.impl.SmsNotificationService
```

#### Step 4: Load and Use via `ServiceLoader`
```java
package devang;

import devang.service.NotificationService;
import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        // Scans the classpath for registered providers
        ServiceLoader<NotificationService> services = ServiceLoader.load(NotificationService.class);

        for (NotificationService service : services) {
            System.out.println("Discovered provider: " + service.getChannelName());
            service.sendNotification("devang@example.com", "System build successful");
        }
    }
}
```

### Real-World Use Cases:
* **JDBC Drivers (`java.sql.Driver`):** Since Java 6, `DriverManager` automatically finds database drivers on the classpath using `ServiceLoader` without needing `Class.forName()`.
* **Logging Frameworks:** Facades like SLF4J use SPI to bind to backends (Logback, Log4j2) dynamically.
* **Pluggable Architectures:** Enables third-party plugins where dropping a JAR file into the classpath extends application features without modifying core code (**Open-Closed Principle**).

### Comparison: Direct Instantiation vs. Factory Pattern vs. ServiceLoader
| Characteristic | Direct Instantiation (`new`) | Factory Pattern | `ServiceLoader` (SPI) |
| :--- | :--- | :--- | :--- |
| **Coupling** | High compile-time coupling | Moderate (coupled to factory) | Zero compile-time coupling to implementations |
| **Extensibility** | Poor; requires source code modification | Moderate; requires factory updates | High; add new JAR/module to classpath |
| **Configuration** | Hardcoded | Code / properties file | `META-INF/services/` or `module-info.java` |
| **Loading Mode** | Eager | Determined by factory | Lazy instantiation during iteration |

---

## Summary Cheat Sheet

| Topic | Primary Function | Key Rule / Convention |
| :--- | :--- | :--- |
| **Javadoc** | Public API documentation & IDE tooltips | Focus on contracts (`@param`, `@return`, `@throws`), not internal implementation. |
| **Main Class** | Application starter / runtime entry point | `public static void main(String[] args)`; acts as bootstrapper, keep domain logic out. |
| **`src/test` Folder** | Automated regression testing | Omitted from production JARs; executed during build lifecycle (`mvn test`). |
| **`<p>` Tag** | Paragraph separation in Javadoc HTML | HTML collapses newlines; use `<p>` between separate descriptive paragraphs. |
| **`ServiceLoader`** | Runtime plugin & service discovery (SPI) | Decouples interface from implementation via `META-INF/services/` or JPMS. |
