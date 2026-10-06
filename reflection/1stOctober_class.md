# Reflection — 29 September 2026

## Group 1 project: TriangleFX refinement
In this class, our Group 1 project TriangleFX reached its final stage. The main application was already working, so our focus was on preparing the **PPT**, project demonstration, and technical questions that could be asked during the presentation.
We reviewed the complete project and discussed how to explain the mathematical calculations, JavaFX interface, testing, and project structure in a simple way.
Preparing the Presentation
We planned the **PPT** around the main parts of the project:
- Introduction and problem statement
- Mathematical model
- Finding line intersections
- Checking whether a valid triangle is formed
- Project architecture
- JavaFX interface
- Coordinate transformation
- Testing
- Live demonstration
- Future improvements
We decided to use diagrams and screenshots instead of putting too much code on the slides. This would make the presentation easier for others to understand.
Explaining the Mathematical Process
We prepared a simple explanation of how the application works.
A line is represented as:
ax + by = c

The application takes three lines and finds their pairwise intersections: Line 1 + Line 2 → Point 1 Line 2 + Line 3 → Point 2 Line 3 + Line 1 → Point 3

These three points are then checked to see whether they form a valid triangle. We also discussed the determinant used to find intersections: D = a₁b₂ - a₂b₁

If the determinant is zero or very close to zero, the lines do not have a unique intersection.
For checking the triangle, we use its area. If the area is zero, the points do not form a proper triangle.
Planning the Live Demo
Instead of entering random values during the presentation, we planned a few fixed examples:
- Right triangle – to show normal working.
- Equilateral triangle – to show calculations with decimal values.
- Parallel lines – to show error handling.
- Concurrent lines – to show that the application rejects a zero-area triangle.
- Custom input – to show that users can enter their own values.
This helped us prepare a smoother demonstration and avoid unexpected problems.
### Technical Questions
We also prepared answers for some possible questions.
One question was why we use standard form ax + by = c instead of slope-intercept form. The main reason is that standard form can also represent vertical lines.
Another question was about floating-point tolerance. Since computer calculations can have small errors, the program uses a small tolerance instead of checking values for exact equality with zero.
We also discussed why the raw matrix input panel was removed. Removing it made the interface simpler and reduced unnecessary input options.
### Coordinate Transformation
We reviewed how mathematical coordinates are displayed on the JavaFX canvas.
In a normal graph, positive Y goes upward, while in JavaFX the Y-coordinate increases downward. Therefore, the application converts the mathematical coordinates before drawing the triangle.
This helped me understand how the mathematical calculations are connected to the final visual output.
What I Learned
This class helped me understand:
- How to explain a technical project clearly.
- How line intersections are calculated.
- Why triangle validation is necessary.
- How floating-point errors are handled.
- How mathematical coordinates are converted for JavaFX.
- Why testing both valid and invalid cases is important.
- How to prepare for technical questions during a presentation.
- Why a planned demonstration is better than using random inputs.
### Final Reflection
The main thing I learned from this class was that completing a project is not only about making the code work. We also need to be able to explain the project and justify our design choices.
Preparing the **PPT** and demonstration made me look at TriangleFX as a complete system, from user input and mathematical calculations to testing and final visualisation. It also gave me more confidence in explaining the work our group had done.
