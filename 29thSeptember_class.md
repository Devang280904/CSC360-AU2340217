# Reflection — 29 September 2026

## Group 1 Project: Working on TriangleFX

In this class, I continued working with my Group 1 team on **TriangleFX**, our JavaFX-based application for constructing and analysing a triangle from three linear equations.

At this stage, the project was already functional, so my focus was less on creating the application from scratch and more on understanding how the different parts of the system work together, improving the overall presentation of the project, and contributing to the final refinement of the application.

---

## What I worked on

My work during this session mainly involved understanding and improving the existing project rather than developing one completely independent feature.

The main areas I worked with were:

- Reviewing the existing project structure and implementation.
- Improving the project documentation.
- Reviewing changes made by team members.
- Helping with the refinement of the JavaFX interface.
- Understanding how the mathematical calculations are connected to the UI.
- Checking how invalid inputs and degenerate cases are handled.
- Removed one unnecessary feature of parsing the text.

One important part of the work was learning to look at the project as a complete system rather than as separate classes.

---

## Contribution to the repository

One of my documented contributions was the **README** improvement represented by commit `86afa42`, titled:

**“Reapply *Refactor: Remove raw matrix paste feature*.”**

The update involved removing the unnecessary feature and making the repository easier for another developer to understand.

I also participated in integrating team changes through pull request . This was useful because it gave me practical experience with reviewing and merging work through GitHub instead of treating the main branch as the place where everyone directly makes changes.

---

## Understanding what TriangleFX actually does

While working on the project, I understood the application more clearly from the user's perspective.

The basic idea is that the user provides three linear equations. These equations are interpreted as three lines, and the application calculates where the lines intersect.

For example, each line can be represented as:

```text 
ax + by = c
```

The three equations can therefore be represented in matrix form as:

```text 
A × X = B
```

where `A` contains the coefficients of `x` and `y`, `X` represents the unknown coordinates, and `B` contains the constants.

The application then considers the three possible pairs:

```text
Line 1 + Line 2 Line 2 + Line 3 Line 3 + Line 1
 ```

The intersection of each pair gives a potential vertex of the triangle.

Therefore:

```text 
L1 ∩ L2 → Vertex 1
L2 ∩ L3 → Vertex 2
L3 ∩ L1 → Vertex 3
 ```

If these three points form a proper triangle, the application can calculate properties such as its area, perimeter, side lengths, and coordinates.

---

## Understanding the mathematics behind the application

One of the most useful parts of this session was connecting the mathematical concepts with their actual implementation.

For two equations:

a₁x + b₁y = c₁ a₂x + b₂y = c₂

the program uses the determinant:

D = a₁b₂ - a₂b₁

to determine whether the two lines have a unique intersection.

When:

D ≠ 0

the equations have one unique solution, meaning that the two lines intersect at exactly one point.

When the determinant is zero or sufficiently close to zero, the lines do not provide a unique intersection. Depending on the equations, they may be parallel or coincident.

This becomes important because TriangleFX cannot construct a normal triangle if the required intersection points do not exist uniquely.

I also understood why the application needs to consider floating-point tolerance. Mathematical calculations performed using computer numbers can contain small precision errors, so checking whether a value is exactly zero is not always reliable.

---

## From equations to a triangle

The complete process can be understood as a sequence:

```text
User enters coefficients
          ↓
Input is validated
          ↓
Coefficients are converted into line objects
          ↓
Each pair of lines is analysed
          ↓
Intersection points are calculated
          ↓
Triangle validity is checked
          ↓
Geometric properties are calculated
          ↓
Results are displayed in JavaFX
```

Understanding this sequence helped me see why different responsibilities are kept in different parts of the code.

The interface should mainly deal with interaction and presentation, while the mathematical calculations should remain independent from JavaFX wherever possible.

---

## Interface improvements

Another area of refinement was the JavaFX interface.

The goal was to make the application easier to operate without making the user understand unnecessary technical details.

The preset selector was improved so that its appearance matched the application's dark theme more consistently. Different states such as the selected value, popup items, and hover behaviour needed to be considered separately.

This showed me that a JavaFX component is sometimes made up of multiple visual elements. Styling only the main control does not necessarily produce the expected result for the dropdown contents.

The interface was also simplified by removing the raw matrix-paste section from the visible workflow.

Instead of presenting multiple ways to provide the same information, the application focuses more directly on the coefficient input fields and preset examples.

I found this interesting because I initially thought that adding more input methods would automatically make an application more flexible. In practice, too many options can make a small application harder to understand.

---

## Following the application through the code

I spent time tracing how information travels through the application.

The project can broadly be understood using four areas:

### 1. Input and parsing

The user's values are collected from the interface and converted into numerical data.

This stage also checks whether the entered information is valid.

Examples of possible problems include:

- Missing values
- Non-numeric input
- Invalid coefficients
- Incorrect matrix structure
- A line where both coefficients are zero

Instead of allowing these problems to cause unexpected program failures, they should be converted into understandable feedback for the user.

### 2. Geometry

The geometry-related code performs the actual mathematical calculations.

It is responsible for finding intersections and determining whether the calculated points can form a valid triangle.

### 3. Models

Classes representing concepts such as points, lines, and triangles make the rest of the application easier to understand.

For example, instead of passing separate `x` and `y` values everywhere, a point can be represented as a single object containing those coordinates.

This is a simple example of how object-oriented programming can make mathematical code more readable.

### 4. JavaFX presentation

The JavaFX portion handles the visible application.

It collects input, responds to button clicks, displays calculated results, and draws the triangle.

Keeping this separate from the mathematical calculations also makes the geometry code easier to test independently.

---

## How the triangle is displayed

The application does not directly draw the mathematical coordinates onto the JavaFX canvas.

There is an important difference between the coordinate systems.

In the usual Cartesian plane:

```text
    +y
    ↑
    |
--------+--------→ +x
    |
    ↓
```

However, JavaFX canvas coordinates increase downward along the y-axis.

Therefore, the application needs to transform mathematical coordinates before drawing them.

The rendering system also adjusts the displayed region so that triangles with different sizes can remain visible within the canvas.

The final visualization includes elements such as:

- Coordinate axes
- Grid/reference lines
- Tick information
- Triangle boundaries
- Filled triangle region
- Vertex markers
- Vertex labels

This helped me understand that displaying mathematical data is not simply a matter of drawing the calculated values. A transformation between the mathematical model and the screen representation is also required.

---

## Testing and handling unusual cases

Another important learning point was seeing how testing can be used for mathematical software.

Some of the cases that need to be considered are not just normal valid triangles.

For example:

- Two lines intersect normally.
- Two lines are parallel.
- Two lines are coincident.
- Three lines meet at one point.
- The calculated vertices are repeated.
- The vertices are collinear.
- The triangle has extremely small dimensions.
- Input contains invalid values.
- Floating-point calculations produce values close to zero.

These cases are important because an application can appear correct when tested only with simple examples while still failing for mathematically unusual inputs.

Keeping the geometry calculations outside the JavaFX interface makes these cases easier to test using automated unit tests.

---

## GitHub collaboration

Working with the repository also gave me experience beyond writing code.

I learned more about how a team can use GitHub to divide work and combine changes.

The workflow involved:

```text
Create/modify files
       ↓
Commit changes
       ↓
Push changes
       ↓
Review changes
       ↓
Pull request
       ↓
Merge into main
```

Working with a pull request made me understand that collaborative development is not only about producing code. It also involves reviewing changes, checking whether they fit the existing project, and integrating them without unnecessarily disturbing other work.

---

## What I learned from this session

This session helped me understand several concepts more practically.

### Mathematical concepts

I improved my understanding of:

- Representing lines using equations.
- Matrix representation of linear equations.
- Determinants and line intersections.
- Conditions for parallel and coincident lines.
- Triangle validity.
- Distance and perimeter calculations.
- Area calculation.
- Floating-point precision.

### Java and JavaFX

I gained a better understanding of:

- Organising classes according to their responsibilities.
- Using Java objects to represent mathematical entities.
- Handling JavaFX events.
- Customising JavaFX controls.
- Drawing objects on a canvas.
- Converting between coordinate systems.

### Software engineering

The project also taught me about:

- Writing useful documentation.
- Maintaining a readable **README**.
- Providing reproducible build instructions.
- Automated testing.
- Git commits.
- Pull requests.
- Code integration.

---

## Current state of the project

At this point, TriangleFX had become a functional JavaFX application rather than just a project structure or prototype.

The project included:

- Maven-based project configuration
- JavaFX user interface
- Matrix coefficient input
- Preset examples
- Line-intersection calculations
- Triangle validation
- Area and perimeter calculations
- Coordinate-based visualisation
- Automated tests
- Documentation and screenshots
- GitHub-based collaboration

The application was therefore in a stage where refinement and presentation were becoming as important as adding completely new functionality.

---

## Possible next steps

Although the core workflow was functional, there are still several ways the project could be improved.

Some possible additions are:

- Allowing users to directly enter equations such as `x + y = 8`.
- Showing the three original lines on the graph.
- Providing better accessibility and keyboard navigation.
- Adding more JavaFX-level integration tests.
- Allowing users to export the generated diagram.
- Moving more styling rules into a dedicated **CSS** file.
- Providing additional example presets for demonstration.

These improvements could make the application more useful without changing its main mathematical purpose.

---

## Key Takeaways

Before going through the code carefully, it was easy to think of the project as simply a JavaFX program that draws a triangle. After tracing the complete workflow, I understood that several separate stages are involved: accepting the input, validating it, converting it into mathematical objects, solving the equations, checking the resulting geometry, and finally presenting the result visually.

I realised that documentation is part of the development process rather than something that should only be written at the end. A well-structured **README** can make it much easier for another person to build, test, understand, and contribute to a project.
