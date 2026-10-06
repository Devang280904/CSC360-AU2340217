# Class Reflection (03 September 2026)



### 1. ASCII Tree

ASCII tree is basically a way of representing a tree using normal text characters instead of actually drawing the tree using graphics.

#### Printing vs Drawing a Tree

There is a difference between printing and drawing a tree.

- Printing means using normal text characters directly in the terminal.
- Drawing means using a graphical interface where shapes and lines are visually rendered on the screen.

For example, in a terminal we can use characters like `|`, `-`, `+` etc. to show the structure of the tree.

The main benefit of printing is that we don't need any graphical interface for it.

#### Why Use Text/Printing?

Text is more robust and reliable compared to depending on a GUI.

For example, when we are working on a remote machine or an embedded system, there might not be any monitor or graphical display available.

In such cases, we can still communicate with the computer using the terminal.

So basically text is one of the most common and reliable ways of communicating with a computer because standard input and output can work through text.

---

### 2. Remote Connection - SSH
`ssh` stands for Secure Shell and it is used from the client side to connect to a remote computer.

For example:
Client Machine -> ssh -> Remote Server -> sshd
     
`sshd` is the daemon which runs in the background on the remote server and listens for incoming SSH connections.

Here, the `d` basically stands for daemon.

So in simple terms:

- `ssh` -> used by the client to connect
- `sshd` -> runs on the server and accepts the connection

---

### 3. ASCII Tree Implementation

While representing a normal binary tree, we generally use `/` and `\` to show diagonal branches.

But in an ASCII tree, we can use simple characters like:

- `|`
- `-`
- `+`

to represent the hierarchy and connection between different nodes.

This type of representation is very useful in terminal based applications because it can be displayed without requiring any graphical interface.

---

### 4. Common Elements Between 2 Lists

Suppose we have two lists and we want to show the user which elements are common between them.

There can be different ways of displaying this information.

#### Full Display

The first way is to simply show **every item from both lists**.

The problem with this is that if the lists are very large, the user will have to go through everything just to find the elements which are common.

For example:
List 1 -> A B C D E F G H
List 2 -> P Q C D R S T


The user has to check the whole thing to find that `C` and `D` are common.

#### Using `...`

Another way is to hide the elements which are not important and use `...` to represent skipped items.

For example:
A B ... C D ... G
P Q ... C D ... T

This makes the output shorter and allows the user to focus more on the elements which are related or matching.

So basically, `...` can be used when we don't want to display a long sequence of items which are not relevant.

---

### 5. Custom Splash Screen

A splash screen is the screen which is shown when an application is starting or initializing.

When we start an application, there can be many things happening in the background like loading configuration, dependencies, resources, etc.

Instead of showing a blank screen or making the user think that the application is stuck, we can show a splash screen.

For example, when starting an IDE, we might see a screen containing:

- Application logo
- Application name
- Version number
- Build information
- Loading/progress bar

This tells the user that the application is actually starting in the background.

So the main purpose of a splash screen is to give immediate feedback to the user while the application is loading.

---

### 6. Objects

An object is basically an entity which can contain multiple related properties together.

Instead of keeping different values separately, we can group them into a single object.

For example, if we have a tree node, instead of keeping its value, color, name and size separately, we can keep all of them inside one object.


This makes it easier to manage related information because all the properties belong to the same entity.

So basically, an object helps us to **group related data together in one place**.

