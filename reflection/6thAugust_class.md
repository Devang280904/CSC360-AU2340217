1. What is GitHub?

Answer:
GitHub is a platform used for hosting, managing, and collaborating on code. It uses Git for version control, so multiple developers can work on the same project, track changes, and maintain different versions of the code.

2. How is Computer Graphics different from Image Processing?

Answer:
Computer Graphics is mainly about creating and generating images using mathematical models, shapes, lines, curves, and other graphical elements.

Image Processing is about taking an existing image and modifying or analyzing it using algorithms, such as resizing, filtering, enhancement, or noise removal.

Simple difference:

Graphics → Create an image
Image Processing → Process an existing image
3. What are the geometric primitives of Computer Graphics?

Answer:
Geometric primitives are the basic building blocks used to create graphics. Common primitives include:

Points
Lines
Curves
Circles and arcs
Polygons
Areas or surfaces

Complex objects can be created by combining these basic primitives.

4. Describe graphical frameworks in Java: AWT, Swing, and JavaFX.

Answer:

AWT (Abstract Window Toolkit):
It is Java's older GUI framework. It provides components such as buttons, labels, text fields, and windows. AWT components are generally platform-dependent because they use native OS components.

Swing:
Swing is built on top of AWT and provides more advanced and flexible GUI components. Components like JFrame, JButton, JPanel, etc. are commonly used.

JavaFX:
JavaFX is a more modern framework for creating rich graphical user interfaces. It supports features such as animations, CSS styling, charts, multimedia, and modern UI components.

In short:
AWT → older/basic
Swing → more flexible GUI
JavaFX → modern/rich GUI

5. What design pattern is used for creating a user interface, such as clicking a button?

Answer:
For GUI interactions, Java uses the Event-Driven Programming model.

For example, when we click a button, an event is generated. We attach an event listener to the button, and the listener executes a particular method when that event occurs.

Example:

button.addActionListener(e -> {
    System.out.println("Button clicked");
});

Here:

Button click → Event
ActionListener → Listener
Code inside → Event handler
6. What is the difference between static and interactive graphics?

Answer:

Static graphics are graphics that do not respond to user interaction. They remain the same even if the user moves the cursor or performs an action.

Interactive graphics respond to user input such as mouse movement, mouse clicks, keyboard input, etc.

Example:

Static → A displayed graph or image
Interactive → A graph that changes when we click or drag something
7. What is SSH vs HTTPS?

Answer:

Both SSH and HTTPS provide secure communication, but they are used for different purposes.

SSH (Secure Shell):

Used for secure remote access to computers and servers.
Commonly used with GitHub for authentication and repository operations.
Uses SSH keys such as public and private keys.

HTTPS (HyperText Transfer Protocol Secure):

Used mainly for secure communication between a client/browser and a web server.
GitHub can also use HTTPS for cloning and pushing repositories, usually with authentication tokens.

Simple difference:

SSH → Secure remote access / Git authentication

HTTPS → Secure web communication

8. What happens when we use ssh-keygen?

Answer:
ssh-keygen is used to generate an SSH key pair.

It generally creates:

Private key → kept secret on our computer
Public key → can be shared with services like GitHub

On Linux, the keys are usually stored inside the .ssh directory in the user's home directory.

For example:

ssh-keygen
cd ~/.ssh

~ represents the user's home directory.

9. How are curves connected to Calculus?

Answer:
Calculus provides mathematical tools to analyze curves.

Derivative → tells us the slope or rate of change at a particular point on a curve.
Integral → helps calculate the area under a curve.
Optimization → helps find maximum and minimum points of a curve.

So, calculus helps us understand the shape, slope, area, and important points of curves.

10. What is the difference between public and private keys?

Answer:

SSH uses a pair of keys:

Public Key:

Can be shared with others.
For GitHub, we add our public key to our GitHub account.
It helps GitHub identify and authenticate our computer.

Private Key:

Must be kept secret.
It remains on our computer.
It should never be shared with anyone.

Easy way to remember:

Public key → Share it
Private key → Keep it private

11. Explanation of your Perpendicular Lines Java program

Your program uses Java Swing and Java 2D Graphics to draw one main line and another line perpendicular to it.

Main idea

You first define two points:

double x1 = 100, y1 = 300;
double x2 = 400, y2 = 100;

These two points define the main line.

Then you calculate its midpoint:

double midX = (x1 + x2) / 2;
double midY = (y1 + y2) / 2;

So the perpendicular line will pass through the midpoint.

Finding the direction of the main line
double dx = x2 - x1;
double dy = y2 - y1;

Here:

dx = change in x
dy = change in y

So (dx, dy) represents the direction vector of the original line.

Finding the length
double currentLength = Math.hypot(dx, dy);

This calculates:

dx
2
+dy
2
	​


which is the length of the direction vector.

Finding the perpendicular direction

This is the most important part:

double px = -dy / currentLength * perpLength;
double py =  dx / currentLength * perpLength;

If the original direction vector is:

(dx,dy)

then a perpendicular vector can be obtained as:

(−dy,dx)

because their dot product is zero:

(dx)(−dy)+(dy)(dx)=0

Therefore, the two vectors are perpendicular.

Finding the endpoints of the perpendicular line
double px1 = midX + px;
double py1 = midY + py;


double px2 = midX - px;
double py2 = midY - py;

This creates two points on opposite sides of the midpoint.

Then:

g2d.drawLine((int) px1, (int) py1,
             (int) px2, (int) py2);

draws the perpendicular line.
