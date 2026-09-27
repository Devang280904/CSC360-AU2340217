# Reflection — 17 September 2026

## Group 1 Collaboration: TriangleFX

Today's session focused heavily on advancing our collaborative Group 1 software project, **TriangleFX**, with a major emphasis on laying out our architectural boundaries and standardizing how we approach development milestones.

**Repository Link:** [Devang280904/CSC360-Group1](https://github.com/Devang280904/CSC360-Group1)

## Project Vision & Core Purpose

TriangleFX is engineered as an interactive JavaFX desktop program designed to bridge linear algebra concepts with graphical user interfaces. The core utility accepts a system of three linear equations and executes a multi-step pipeline:

1. **Parsing:** Converts raw user input strings into standardized linear equation forms ($Ax + By = C$).
2. **Computational Geometry:** Solves pairwise line intersections to locate potential triangle vertices.
3. **Topology Validation:** Evaluates the resulting coordinate set to ensure it constitutes a non-degenerate, valid geometric triangle (filtering out parallel lines, overlapping lines, or collinear vertex sets).
4. **Graphics Pipeline:** Renders the verified polygon dynamically onto a JavaFX canvas layout.
5. **Feedback Loop:** Manages coordinate labelling and surfaces contextual error alerts for syntax or impossible geometric constraints.

Representative user input scenarios include standard formulations like:
```text
x + y = 8
x - y = 2
x = 1
```

---

## Contributions & Milestones Completed

### 1. Requirements Engineering & Scope Governance
I took the lead in outlining the functional and non-functional bounds of the application to prevent feature creep. My documentation details:
- Exact validation rules for input fields supporting standard numeric coefficients (both integer and floating-point).
- Error-handling behaviors for edge cases such as division by zero during slope calculations, identical lines, and parallel boundaries.
- Architectural directives prioritizing loose coupling between UI event listeners, algebraic solvers, and rendering logic.

### 2. Repository Overhaul and Documentation Standards
To maintain high transparency across the repository, I revamped the core `README.md` file. Instead of treating the page as a static placeholder, I structured it to clearly delineate between **implemented documentation frameworks** and **unimplemented execution phases**. This ensures any external collaborator or teammate immediately grasps the current developmental horizon of the repository.

### 3. Formulation of a Phased Implementation Road Map
Rather than jumping straight into code without a unified vision, I engineered a comprehensive 8-stage roadmap to guide the team's upcoming sprints:

| Phase ID | Milestone Objective | Current State |
|---|---|---|
| **Phase 1** | Documentation Baseline & Architecture Scoping | Completed |
| **Phase 2** | Maven/JavaFX Project Skeleton & Build Configuration | Pending |
| **Phase 3** | Algebraic Equation Parser ($Ax + By = C$ Normalizer) | Pending |
| **Phase 4** | Geometry Engine (Pairwise Intersections & Degeneracy Filters) | Pending |
| **Phase 5** | JavaFX UI Layout & Component Bindings | Pending |
| **Phase 6** | Canvas Renderer & Coordinate Mapping System | Pending |
| **Phase 7** | Comprehensive Test Suite & Edge-Case QA | Pending |
| **Phase 8** | Refactoring, Performance Tuning, & Final Demo Prep | Pending |

### 4. Git Branching Discipline
- Developing features on dedicated feature branches.
- Keeping commit histories focused and descriptive.
- Utilizing peer review cycles before merging code or project blueprints into the primary `main` branch.

### 5. Architectural Alignment & Design Trade-offs
Our group evaluated multiple structural patterns for organizing the application modules. We intentionally avoided a monolithic architecture (where UI logic and math algorithms intertwine inside a single class). Instead, we settled on a clean separation of concerns:
- **Parser Module:** Pure Java string manipulation and regex evaluation.
- **Math/Geometry Module:** Pure mathematical matrix or simultaneous equation solvers completely decoupled from JavaFX dependencies.
- **View Module:** Canvas painting routines and event dispatching.

---

## Technical Audit & Git Activity Log

My recorded contributions on the repository under the handle `drumilbhati` reflect the initial foundational commits:

- **`e713d02`** — Established formal TriangleFX specifications and usage guidelines.
- **`e939eae`** — Refined the project README to accurately reflect the documentation phase.
- **`a9d7629`** — Introduced the comprehensive JavaFX implementation roadmap and tracking matrix.
- **`0bd8b87`** — Executed the merge of pull request #1 into `main`.

---

## Key Takeaways & Personal Insights

This planning phase underscored why front-loading software architecture pays off. Establishing a shared vocabulary and rigid module boundaries prevents duplicated effort and clears up misunderstandings regarding how mathematical models map onto graphical canvases. 

Key learnings from this cycle include:
- The importance of explicit error boundaries when designing text parsers for user-generated inputs.
- How clean separation of concerns facilitates isolated unit testing for mathematical formulas independently of UI frameworks.
- The value of treating documentation as code—keeping it precise, version-controlled, and transparent.

## Immediate Next Steps

With the scoping and roadmap phases locked in, our next operational target is to spin up the Maven project skeleton, establish the core directory layout, and begin writing the parser unit tests.
