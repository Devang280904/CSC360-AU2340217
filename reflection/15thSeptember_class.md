# Class Reflection — 10 September 2026
# Project Implementation - Group 1

## 1. System Overview & Objectives
Our development objective was to engineer a comprehensive Java desktop program capable of processing coordinate geometry equations, solving linear matrix systems, and presenting interactive visual outputs through a JavaFX interface. 

The core functional milestones achieved during development include:
* Validating spatial coordinate points to confirm valid triangle formation.
* Computing exact intersection points for pairs of linear equations.
* Representing systems of three linear equations in matrix format ($Ax = b$).
* Constructing an intuitive JavaFX data-entry grid and validation framework.

---

## 2. Geometric Triangle Validation
To determine whether three spatial coordinates $P_1(x_1, y_1)$, $P_2(x_2, y_2)$, and $P_3(x_3, y_3)$ create a nondegenerate triangle, we evaluate whether they lie on the same straight line (collinearity). Collinear points yield zero polygon area.

### Signed Area Computation
Twice the signed area ($D$) is calculated via vertex expansion:
$$D = x_1(y_2 - y_3) + x_2(y_3 - y_1) + x_3(y_1 - y_2)$$
$$\text{Area} = \frac{|D|}{2}$$

* **$D = 0$:** Degenerate condition; points are collinear (or identical) and cannot form a triangle.
* **$D \neq 0$:** Valid triangle configuration. Furthermore, the sign of $D$ dictates orientation ($D > 0$ counterclockwise, $D < 0$ clockwise).

### Java Verification Snippet
Because floating-point numbers (`double`) accumulate precision errors, exact zero comparisons are unreliable. We enforce an `EPSILON` threshold:

```java
private static final double EPSILON = 1.0e-9;

private static boolean formsTriangle(
        double x1, double y1, 
        double x2, double y2, 
        double x3, double y3) {

    double twiceSignedArea =
        x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);

    return Math.abs(twiceSignedArea) > EPSILON;
}
```

---

## 3. Linear Systems & Intersection Analysis
Lines are defined in standard linear form ($ax + by = c$) rather than slope-intercept form to natively support vertical lines without undefined slope exceptions.

Expressed as a matrix equation ($Ax = b$):
$$\begin{bmatrix} a_1 & b_1 \\ a_2 & b_2 \end{bmatrix} \begin{bmatrix} x \\ y \end{bmatrix} = \begin{bmatrix} c_1 \\ c_2 \end{bmatrix}$$

### Cramer's Rule & Determinants
The coefficient matrix determinant $D = a_1b_2 - a_2b_1$ determines line relationships:
* **Unique Intersection ($D \neq 0$):** Solved via Cramer's rules ($x = \frac{c_1b_2 - c_2b_1}{D}$, $y = \frac{a_1c_2 - a_2c_1}{D}$).
* **Parallel & Coincident ($D = 0$):** Evaluated using augmented determinants ($D_x = c_1b_2 - c_2b_1$, $D_y = a_1c_2 - a_2c_1$). If $D, D_x, D_y \approx 0$, lines are coincident. If $D \approx 0$ while $D_x$ or $D_y \neq 0$, lines are parallel.

### Data Models
```java
record Line(double a, double b, double c) {}
record Point(double x, double y) {}
```
Any line configuration where both coefficients ($a$ and $b$) are zero is flagged as invalid prior to computation.

---

## 4. Overdetermined Systems ($3 \times 2$) & Consistency Checks
Introducing a third line creates an overdetermined system (three equations, two unknowns), which rarely shares a single exact intersection point. Potential outcomes include a common intersection, a triangle formed by pairwise intersections, parallel alignments, or coincident overlaps.

To test for a true common intersection:
1. Compute the unique intersection of a non-parallel line pair.
2. Substitute the candidate coordinates into the third line equation.
3. Validate against a tolerance boundary: $|a_3x + b_3y - c_3| \le \text{tolerance}$.

---

## 5. JavaFX UI Integration & Input Hygiene
The graphical interface leverages a `GridPane` layout to ingest parameters for three distinct lines:

| Line Designation | X Coefficient | Y Coefficient | Constant Term |
| :--- | :--- | :--- | :--- |
| **Line 1** | `a₁` | `b₁` | `c₁` |
| **Line 2** | `a₂` | `b₂` | `c₂` |
| **Line 3** | `a₃` | `b₃` | `c₃` |

### Validation Pipeline
Input text fields undergo strict sanitization before matrix conversion arrays (`coefficients` and `constants`) are compiled:
* Empty string rejection.
* Numeric parsing via `Double.parseDouble()` wrapped in try-catch blocks.
* Range validation checking for finite bounds (`Double.isFinite()`).
* Coefficient check eliminating zero-vector lines.

```java
private double readNumber(TextField field, String fieldName) {
    String text = field.getText().trim();
    if (text.isEmpty()) {
        throw new IllegalArgumentException(fieldName + " is required.");
    }
    try {
        double val = Double.parseDouble(text);
        if (!Double.isFinite(val)) {
            throw new IllegalArgumentException(fieldName + " must be finite.");
        }
        return val;
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException(fieldName + " must be a valid number.");
    }
}
```

---

## 6. Software Architecture
To ensure high testability and maintainability, the project follows a strict separation of concerns across packages:

```text
src/main/java/
├── application/   → GeometryApplication.java (JavaFX lifecycle)
├── controller/    → GeometryController.java  (UI events & data flow)
├── model/         → Line.java, Point.java    (Immutable records)
└── service/       → GeometryService.java     (Pure mathematical core)
```

By decoupling computational math from UI event handlers, unit testing can thoroughly verify geometric edge cases (collinear points, parallel lines, common intersections, and matrix inconsistencies) independently of the JavaFX runtime.
