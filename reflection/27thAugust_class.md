# Class Reflection (27 August 2026)

---

## Topics Covered

- Install
- JAR
- CI/CD Pipeline
- UTF-8
- Release
- JUnit
- Version
- Control
- FXML

---

## My Understanding

### 1. Install

Install basically means installing all the required dependencies which are needed for our project.

If the required dependency is already available locally, then it may not need to download it again. Otherwise, Maven can download the required dependency from a public repository and install/store it so that our project can use it.

So basically, install makes sure that all the things required for our code to run are available.

---

### 2. JAR

JAR stands for Java Archive.

A JAR file is somewhat similar to a ZIP file, because it can contain multiple files together in one package.

It can contain:

- .class files
- Packages
- Resources
- Configuration files
- Metadata

It is mainly used to package a Java application or Java library so that it can be easily distributed and used.

So instead of giving many different .class files separately, we can package them together inside a JAR file.

Java Code -> Compilation -> .class files -> JAR -> Easy to use

---

### 3. CI/CD Pipeline

CI/CD is used to automate the process of building, testing and deploying software.

#### CI - Continuous Integration

Suppose there is already an existing project or architecture and I make some changes in it, like adding new files, modifying existing code or removing something.

When I push these changes, the CI pipeline can automatically:

1. Get the latest changes
2. Build/compile the project
3. Run tests
4. Check whether the changes are breaking anything
5. Integrate the changes with the existing code

The main idea of CI is to make sure that the new changes work properly with the existing project.

For example:

Developer makes changes -> Push code -> CI pipeline -> Build -> Run tests -> If everything is successful -> Changes can be integrated


---

### 4. CD - Continuous Deployment

CD stands for Continuous Deployment.

After the code has been successfully built and tested, CD can automatically deploy the new version of the application.

For example, suppose our application is currently running with version 1.0.

I make some changes and the pipeline successfully builds and tests them.

Then CD can deploy the new version so that the deployed application is updated with the latest code.


Code -> Build -> Test -> Deploy -> Updated Application

So in simple terms:

CI → Build and test the changes

CD → Deliver/deploy the changes

---

### 5. UTF-8

UTF-8 stands for Unicode Transformation Format - 8.

It is an encoding format which is used to represent characters as bytes so that computers can store and process text.

It supports a very large number of characters from different languages.

For example:

English, Hindi, German, Japanese


These characters are encoded into bytes which the computer can understand and store.

UTF-8 is very commonly used because it can represent characters from many different languages while also being compatible with ASCII for basic English characters.

One important thing I understood is that character set and character encoding are not exactly the same thing.

Unicode is the character set/standard which defines characters and their code points, while UTF-8 is one way of encoding those characters into bytes.

---

### 6. Release

Release is basically the process of preparing a particular version of the software so that it can be distributed or used.

In Maven, version information can be defined in the pom.xml.

For example:

xml
<version>1.0.0</version>


Also, Java version can be configured in the Maven project.

For example, if the project is supposed to use Java 8, we can configure Maven so that the project is compiled using Java 8 rather than just automatically using whatever newer Java version is installed.

This is important because a project might have been developed and tested with a particular Java version and changing the compiler version can sometimes cause compatibility issues.

---

### 7. JUnit

JUnit is a testing framework for Java.

It is used to write automated tests for our Java code.

For example, if I have a function:


int add(int a, int b)

I can create a JUnit test to check whether:


add(2, 3) = 5


or not.

So instead of manually checking every function again and again, we can write tests which can automatically check our code.

The tests can verify whether the expected output is coming or not.

---

### 8. Why JUnit is Important

I also understood why automated testing is useful instead of only doing manual testing.

Suppose there is a button in our application which is not working.

If I manually test it, I might think that the problem is with the button itself.

But actually, that button might be calling a function from another class/file and the actual problem could be inside that function.

With JUnit, we can directly test individual methods and identify which part of the code is failing.

For example:

Button ->  Calls Function A -> Function A calls Function B -> Function B has an error

Manual testing may only show that the button is not working.

But automated unit tests can help us identify that Function B is failing.

This makes debugging and maintaining large projects much easier.

---

### 9. Version

Version basically tells us **which particular release or state of a software/library we are using**.

For example:

JavaFX 21
JavaFX 22
JavaFX 23


These are different versions.

When using a library like JavaFX, it is important to manage the versions properly.

If different JavaFX modules are being used, they should generally be compatible and should use the same JavaFX version.

For example:


javafx-controls → 21
javafx-fxml     → 21
javafx-graphics → 21

Using compatible versions avoids dependency conflicts and unexpected errors.

---

### 10. Control

In JavaFX, a Control is basically a UI component which allows the user to interact with the application.

Examples include:

- Button
- TextField
- Label
- CheckBox
- RadioButton
- ListView

For example:


Button
TextField
CheckBox


are all UI elements which can be used to create the interface of a JavaFX application.

So when we say controls, we are basically talking about the interactive or user-interface components provided by JavaFX.

---

### 11. FXML

FXML is an XML-based language used with JavaFX for defining the user interface.

The main benefit is that instead of creating the complete UI using Java code, we can define the structure of the UI separately in an FXML file.

For example, instead of writing everything using Java:

Create Button
Set Button text
Set Button position
Create Layout
Add Button


we can define the UI structure inside an FXML file.

FXML provides a more declarative way of creating the JavaFX user interface.

For example:

FXML -> Defines UI structure -> JavaFX loads FXML -> UI is created

This also helps in separating the UI design from the application logic.

