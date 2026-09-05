# Class Reflection (01 September 2026)


### 1. Triangle and equations

How equations can be used to represent a triangle. We need 3 equations to properly form a triangle. If we only take 2 equations, they will only give us 2 lines, and the third line can be different in many ways, so there can be infinitely many possibilities.

The general form we is:

**Ax = b**

For example:

**2x + 3y = 5**

Here, x is taken as a vector like [x,y], which basically represents a column matrix.

To find the triangle points, we can take 2 equations at a time and solve them to get one point `(x,y)`. Then again take another combination of 2 equations and find another point. Doing this for all the combinations gives us 3 points, and by connecting these points we get the triangle.

Also, Maven comes in context. It helps in formulating/handling things so that we dont have to directly worry about the numerical part of the project.

### 2. Stack and Queue

basic difference between **Stack and Queue**.

- Queue -> FIFO (First In First Out)
- Stack -> LIFO (Last In First Out)

 Better with the example of **CTRL + Z**. When we undo something, we want to remove the last action which we performed, not the first action. So stack is more suitable for this because the latest operation will be available on the top.

For example, if actions are:

`A -> B -> C`

Then using CTRL + Z should first remove `C`, then `B`, then `A`.

So this is why stack is used in situations like undo operations.

### 3. Tree

A tree contains nodes which have a parent - child relationship. A node can be a parent or a child depending on its position in the tree.

One important thing is that each child can have atmost one parent. But one parent can have multiple children.

In a binary tree, a parent can have atmost 2 children.

