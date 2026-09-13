# Class Reflection (08 September 2026)


## Notes

### 1. Java Collections Framework

Today we learned about **Collections in Java**. Basically collections are used when we want to store and manage multiple objects together.

Some of the main collections are:

* List
* Set
* Queue
* Map

Each one is used for different purpose.

---

### 2. List

A **List** stores elements in an ordered way and it also allows duplicate values.

Some common List classes are:

* `ArrayList`
* `LinkedList`

For example, if we have:

~~~java
List<String> names = new ArrayList<>();
names.add("Devang");
names.add("Rahul");
names.add("Devang");
~~~

Here duplicate `"Devang"` is allowed.

`ArrayList` is generally good when we need fast access using index.

`LinkedList` is useful when we are doing more insertions and deletions in the list.

---

### 3. Set

A **Set** is used when we don't want duplicate elements.

Some common Set implementations are:

* `HashSet`
* `LinkedHashSet`
* `TreeSet`

For example:

~~~java
Set<Integer> numbers = new HashSet<>();

numbers.add(10);
numbers.add(20);
numbers.add(10);
~~~

Here `10` will only be stored once.

The main difference between them is:

* `HashSet` - no guaranteed order
* `LinkedHashSet` - maintains insertion order
* `TreeSet` - stores elements in sorted order

---

### 4. Queue and Deque

A **Queue** is mainly used when elements need to be processed one by one, normally in FIFO order.

FIFO means **First In First Out**.

Some classes which can be used are:

* `ArrayDeque`
* `LinkedList`
* `PriorityQueue`

`PriorityQueue` is a little different because elements are processed according to their priority instead of just insertion order.

`Deque` means Double Ended Queue. We can add or remove elements from both sides.

---

### 5. Map

A **Map** stores data in **key-value pairs**.

For example:

~~~java
Map<Integer, String> students = new HashMap<>();

students.put(1, "Devang");
students.put(2, "Rahul");
~~~

Here `1` and `2` are keys and names are values.

Some common Map classes are:

* `HashMap`
* `LinkedHashMap`
* `TreeMap`

One important thing I learned is that **Map is not a part of the Collection interface hierarchy**. It is a separate part of the Collections Framework.

---

### 6. Collection Views

We also learned about **collection views**.

A view means we are seeing a part of the original collection instead of creating a completely new collection.

For example, `subList()` gives a view of a part of a List.

~~~java
List<Integer> numbers = new ArrayList<>();
numbers.add(10);
numbers.add(20);
numbers.add(30);
numbers.add(40);

List<Integer> part = numbers.subList(1, 3);
~~~

Here `part` contains `20` and `30`.

The important thing is that this is connected with the original list. So changes can affect the original collection also.

---

### 7. Map Views

Maps also provide some views like:

* `keySet()`
* `values()`
* `entrySet()`

For example:

~~~java
map.keySet();
map.values();
map.entrySet();
~~~

`keySet()` gives all keys.

`values()` gives all values.

`entrySet()` gives key-value pairs.

These are useful when we want to iterate over a Map.

---

### 8. Unmodifiable View vs Copy

We also saw the difference between an **unmodifiable view** and a copy.

For example:

~~~java
List<Integer> list = new ArrayList<>();
list.add(10);
list.add(20);

List<Integer> view = Collections.unmodifiableList(list);
~~~

Here `view` does not allow us to directly modify it, but it is still connected to the original list.

Whereas:

~~~java
List<Integer> copy = List.copyOf(list);
~~~

creates an unmodifiable copy.

So basically, **view is connected with original collection, while copy is separate**.

---

## 9. Exceptions in Java

Then we learned about **Exceptions**.

Exception is basically a problem that happens during program execution and can disturb the normal flow of the program.

There are mainly:

* Checked exceptions
* Unchecked exceptions

### Checked Exceptions

These are checked by the compiler.

Examples:

* `IOException`
* `SQLException`

We normally have to handle them using `try-catch` or declare them using `throws`.

### Unchecked Exceptions

These happen during runtime.

Examples:

* `NullPointerException`
* `IllegalArgumentException`
* `IndexOutOfBoundsException`

These are generally caused by problems in the code or invalid input.

---

### 10. try, catch, finally, throw and throws

We can handle exceptions using `try` and `catch`.

~~~java
try {
    // code which can cause exception
}
catch (Exception e) {
    // handle exception
}
~~~

`finally` is used for code which we want to execute whether exception happens or not, like closing resources.

`throw` is used to actually throw an exception.

~~~java
throw new IllegalArgumentException();
~~~

`throws` is used in a method declaration to tell that the method can throw an exception.

~~~java
void readFile() throws IOException {
}
~~~

---

### 11. Exceptions and Errors

We also discussed that **Errors are different from Exceptions**.

For example:

`OutOfMemoryError`

This means JVM does not have enough memory.

Errors are generally not something that we normally try to handle in our application.

---

## 12. Logging

After exceptions, we learned about **logging**.

Logging means recording information about what is happening inside our application.

Some logging options in Java are:

* `java.util.logging`
* Log4j2
* SLF4J

There are different logging levels like:

* INFO
* WARNING
* ERROR
* DEBUG

Logging is useful when we want to find what happened in our application, specially when there is some error.

One thing we should take care is that we should not put sensitive information like passwords in logs.

---

## 13. Debugging

Debugging means finding and fixing problems in our program.

Some things we can use while debugging are:

* Breakpoints
* Step over
* Step into
* Variables
* Call stack
* Conditional breakpoints

A breakpoint basically pauses the program at a particular line so we can check what is happening.

We can also check variable values and the call stack to understand where the problem is coming from.

For multithreaded applications, we can also inspect different threads.

---

## 14. Events in AWT and Swing

Then we learned about **Events and Event Handling** in Java AWT and Swing.

An event happens when the user does something with the application.

For example:

* Clicking a button
* Pressing a key
* Moving the mouse
* Selecting something

Java uses something called the **Delegation Event Model** for handling these events.

Basically there are few important parts:

* Event source
* Event object
* Listener
* Event handler

---

### 15. Event Source

The **event source** is the component where the event happens.

For example, a Button can be an event source.

~~~java
Button button = new Button("Click");
~~~

When the user clicks the button, an event is generated.

---

### 16. Event Listener

A listener waits for a particular event.

For example, `ActionListener` is used for action events like button clicks.

~~~java
button.addActionListener(e -> {
    System.out.println("Button clicked");
});
~~~

Here the listener is waiting for the button action and then executes the code.

There are different listeners for different types of events like:

* `ActionListener`
* `MouseListener`
* `KeyListener`
* `WindowListener`

---

### 17. Adapter Classes

Some listener interfaces have many methods.

For example, `MouseListener` has multiple methods.

If we don't need all of them, adapter classes can make things easier because we can override only the method we need.

This avoids writing unnecessary methods.

---

## 18. Swing and Event Dispatch Thread

We also learned that Swing uses an **Event Dispatch Thread (EDT)** for handling UI events.

Swing components are generally not thread-safe, so UI updates should normally be done on the EDT.

For example:

~~~java
SwingUtilities.invokeLater(() -> {
    // update Swing UI here
});
~~~

For long-running work, we should not block the EDT because it can make the UI freeze.

`SwingWorker` can be used for background tasks while keeping the UI responsive.

---

## 19. Java Event Class Hierarchy

We also saw the event class hierarchy.

It is basically:

~~~text
Object -> EventObject -> AWTEvent -> Different AWT/Swing Events
~~~

`EventObject` is the base class for events.

`AWTEvent` is used as the base class for AWT events.

There are different event classes below it for different types of actions.

One thing I understood here is that **event objects and event listener interfaces are not the same thing**.

Event object contains information about what happened, while listener is used to receive and handle that event.

---

## 20. Master-Detail UI

At the end we learned about **Master-Detail UI layout**.

Basically the UI is divided into two parts:

* Master - shows the list of items
* Detail - shows information about the selected item

For example, in an email application, the list of emails can be the master and the selected email content can be the detail.

In Swing we can create this kind of UI using components like:

* `JList`
* `JTable`
* `JPanel`
* `JSplitPane`

For example:

~~~text
+----------------------+----------------------+
|      Master          |       Detail         |
|                      |                      |
|   Item 1             |   Details of Item 1  |
|   Item 2             |                      |
|   Item 3             |                      |
|                      |                      |
+----------------------+----------------------+
~~~

When we select something from the master list, the detail section changes according to the selected item.

`ListSelectionListener` can be used to detect selection changes.

