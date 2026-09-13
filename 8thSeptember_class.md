# Class Reflection — 08 September 2026

### Java Collections

Collections are basically used when we have to store multiple objects together. Instead of creating separate variables, we can use things like List, Set, Queue and Map depending on our requirement.

Some common ones are:

- List → ArrayList, LinkedList
- Set → HashSet, LinkedHashSet, TreeSet
- Queue / Deque → ArrayDeque, LinkedList, PriorityQueue
- Map → HashMap, LinkedHashMap, TreeMap

List allows duplicate values and keeps the order. ArrayList is normally used when we need fast access using index.

Set is used when duplicate values are not required. HashSet does not guarantee any order, LinkedHashSet keeps insertion order and TreeSet keeps the elements sorted.

Queue is generally based on FIFO, but PriorityQueue works according to priority instead of simply insertion order. Deque is useful when we want to add or remove elements from both sides.

Map is little different because it stores data in key-value form. Also Map is not actually a child of Collection interface.

### Collection views

Some methods don't create a completely new collection. They give us a view of the existing collection.

For example:

~~~java
List<Integer> part = list.subList(1, 4);
~~~

Here `part` is connected with the original list, so changes can affect the original list also.

For Map we have views like:

~~~java
map.keySet()
map.values()
map.entrySet()
~~~

These allow us to work with keys, values or key-value pairs.

There is also a difference between an unmodifiable view and a copy. `Collections.unmodifiableList()` gives a view of the original list, while `List.copyOf()` creates an unmodifiable copy.

### Exceptions

We also went through exception handling. An exception basically means something unexpected happened while the program is running and the normal flow gets disturbed.

The main things used for handling exceptions are:

- try
- catch
- finally
- throw
- throws

For example, IOException and SQLException are checked exceptions. Things like NullPointerException and IllegalArgumentException are unchecked exceptions.

`throw` is used when we want to actually throw an exception, while `throws` is written in the method declaration to tell that the method may throw that exception.

### Logging and debugging

Instead of using `System.out.println()` everywhere, logging can be used to understand what is happening inside an application.

Some logging options in Java are `java.util.logging`, Log4j2 and SLF4J.

Different levels can be used depending on the importance of the message, like debug, info, warning and error.

Debugging is more useful when we don't understand why the code is behaving in a certain way. We can put a breakpoint and execute the program step by step. While debugging we can check variable values, call stack and which method is currently running.

There are also conditional breakpoints and exception breakpoints which can save time.

### Events in AWT and Swing

After that we discussed event handling. In GUI applications, an event happens when the user does something like clicking a button, selecting something or pressing a key.

The basic idea is:

event source -> event object -> listener

For example, a Button can be the source and an ActionListener can listen for the button click.

AWT and Swing follow the delegation event model, where the component doesn't handle everything itself. The event is passed to the registered listener.

There are different listener interfaces for different events. Adapter classes are also available for some listener interfaces, especially when the interface has many methods and we only need one or two of them.

### Swing and threads

Swing has an Event Dispatch Thread (EDT). Swing UI work should normally be done on this thread.

For longer tasks, we should not block the EDT because then the UI can become frozen. `SwingWorker` can be used for background work and then update the UI safely.

### Event hierarchy

Java AWT events have a hierarchy. At the top we can have `EventObject`, then `AWTEvent`, and below it different types of events.

We should also not confuse an event class with a listener. Event object contains information about what happened, while listener is used to respond to that event.

### Master-detail UI

Master-detail layout means we have one side showing a list of items and another side showing details of the selected item.

For example, left side can show a list of students and after selecting one student, the right side shows that student's details.

In Swing this can be made using components like `JList`, `JTable`, `JPanel` and `JSplitPane`. A `ListSelectionListener` can be used to detect when the selected item changes.

