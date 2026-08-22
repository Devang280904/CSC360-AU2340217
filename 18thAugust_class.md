# Lecture 3

**Date:** 2nd August 2026

How to draw a Square?

Given:

- Center of square = `(Cx, Cy)`
- Length of square = `l`

First we need to find the 4 corner points of the square.

Since the center is given, distance from center to each side will be `l/2`.

Points:

- Top Left = `(Cx - l/2, Cy - l/2)`
- Top Right = `(Cx + l/2, Cy - l/2)`
- Bottom Left = `(Cx - l/2, Cy + l/2)`
- Bottom Right = `(Cx + l/2, Cy + l/2)`

Then simply join the points:

`Top Left → Top Right → Bottom Right → Bottom Left → Top Left`

Object Field:

An object field belongs to a particular object. Each object has its own separate copy of the field.

Example: In a `Human` class, `name` can be an object field because every human can have a different name. Homo sapiens can be a static field because it is common to all human objects.

