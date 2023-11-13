---
layout: default.md
title: "Sean Phua Zeng Kiat's Project Portfolio Page"
---

### Project: EduTrack

EduTrack is a powerful desktop app designed for SOC tutors. It allows them to streamline their teaching tasks and effortlessly track their class and student records.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java and has about 12 kLoC.

Given below are my contributions to EduTrack.

* **New Features**:
  * Added the ability to remove a student from his or her class.
    * <u>What it does</u>
    <br>It allows users to easily remove a student from their class.
    * <u>Justification</u>
    <br>This feature streamlines class roster updates. It is useful when a student leaves the class and is no longer 
    under the user or when the user allocates a student to a class wrongly.

  * Added the ability to simulate the start of a lesson.
    * <u>What it does</u>
    <br>It helps users to update multiple class records when initiating a new lesson. It automatically increments the total lesson count and resets the attendance sheet for marking student attendance.
    * <u>Justification</u>
    <br>This feature streamlines class record updates, eliminating the need for multiple manual commands.

  * Added the ability to manually set the number of lessons conducted for a class.
    * <u>What it does</u>
      <br>It allows users to set the specific number of lessons conducted for a class.
    * <u>Justification</u>
      <br>EduTrack can track the number of lessons conducted for a class by the number of times the `Start Lesson` command is used. However, if the user unintentionally uses `Start Lesson` command, this allows the user to correct the number of lessons conducted easily.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=seanpzk&breakdown=true)

* **Enhancements to existing features**:
    * Wrote tests for existing features to increase coverage by
      * 2.32% : [#44](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/44)
      * 2.03% : [#138](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/138)
      * 2.53% : [#151](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/151)
      
* **Documentation**:
    * User Guide:
        1. Added and updated documentation of the feature `removing a student`: [\#25](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/25), [\#64](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/64), [\#67](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/67)
        2. Added and updated documentation of the feature `starting a lesson`: [\#64](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/64), [\#67](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/67)
        3. Added and updated documentation of the feature `setting number of lessons of a class`: [#85](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/85)
        4. Tweaked documentation of command format from [original UserGuide](https://github.com/nus-cs2103-AY2324S1/tp/blob/master/docs/UserGuide.md): [\#25](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/25)
        5. Improve user-centricity and tone: [#151](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/151)
        6. Improve grammar for final submission : [#161](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/162)
    * Developer Guide:
        1. Added UML diagrams for implementation of `Remove Student` feature: [\#56](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/56), [\#64](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/64), [\#67](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/67), [#144](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/144)
        2. Added and updated use cases for `Remove a student`, `Modify an existing student record`, and `Use auto-save feature`: [\#25](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/25), [#151](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/151)
        3. Adjusted the description and UML diagram for `Storage Component` [#151](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/151)
        4. Improve grammar for final submission : [#161](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/162)
* **Contribution to team**:
  * Responsible for overall project coordination
    * Setting agendas for weekly meet-ups
    * Preparing skeletal [documentation](https://docs.google.com/document/d/1nNcM_SvI3pcRtNDLubyRG98JFneY5IetiGIi4dhietE/edit?usp=sharing) before discussions
* **Community**:
  * PRs reviewed (with non-trivial review comments): [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88), [#61](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/61), [#40](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/40), [#36](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/36)
  * Reported [bugs & flaws](https://github.com/seanpzk/ped/issues) with extensive elaboration to ease the process of bug fixing for [another team](https://github.com/AY2324S1-CS2103T-T10-3/tp) (taken from Practical Exam Dry Run).
  
* **Key Contributions to the Developer Guide**:
  * Implementation of [Remove Student feature](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#remove-student-feature)
  * Adjustment of [Storage Component](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#storage-component)
  * UML Diagrams 
    * [Remove Student Mechanism](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#remove-student-mechanism)
    * [Execution of a RemoveStudentCommand](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#execution-of-a-removestudentcommand)
    * [Relationship between objects involved](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#relationship-between-key-variables)
    * [Remove Student Workflow](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#remove-student-workflow)
    * [Storage Component for EduTrack](https://ay2324s1-cs2103t-t15-3.github.io/tp/DeveloperGuide.html#storage-component)
  


