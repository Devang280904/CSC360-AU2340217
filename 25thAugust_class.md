# Class Reflection (25 August 2026)

## Topics Covered

- Upstream
- .gitignore
- pom.xml
- Target folder
- Accessible class
- Threads
- Thread-safe and non-thread-safe
- Java Swing and threads

## My Understanding

### 1. Upstream

Upstream basically means the original GitHub repository from where we have cloned our particular repository.

So if I have cloned a project from someone else's GitHub repo, that original repo is called the upstream repository.

---

### 2. .gitignore

.gitignore is used to mention those files which we don't want to push or give publicly in our GitHub repository.

For example, files which contains sensitive information or files which are not required to be uploaded can be added in .gitignore.

So basically Git will ignore those files while doing the commit.

---

### 3. pom.xml

pom.xml is mainly used in a Maven project. It contains the configuration and information required for our Java project.

Instead of manually giving different commands again and again, we can define things inside pom.xml using XML.

It can contain information like:

- Which Java version we are using
- Which external dependencies are required
- How the project should be build
- Project information and configuration
- What plugins should be used

So in our project, pom.xml basically tells Maven how our Java project is structured and what things are required for compiling and running it.

Before running or compiling the project, we should also check if there is any error in the pom.xml, because if the Maven configuration itself is wrong then the project can give errors.

---

### 4. Target Folder

When we compile/build our Java project, we can see that a target folder gets created.

Inside this folder, Maven stores the generated files.

For example, our source code contains .java files, but after compilation these are converted into .class files.

.class files contain the Java bytecode, which is then executed by the JVM.

So we can think something like:

.java file -> Compilation -> .class file (bytecode) -> JVM ->Program execution
