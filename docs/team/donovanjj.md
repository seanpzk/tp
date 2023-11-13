---
layout: default.md
title: "Donovan Chan Jia Jun's Project Portfolio Page"
---

### Project: EduTrack

EduTrack is a desktop app for managing classes designed for tutors, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
Whether you're juggling multiple students or just need quick access to individual student data, EduTrack empowers you with effortless control and organization

Aside from just being able to have a centralised platform to manage your class and students, you can have the ability to do it anywhere as long as you have your device with EduTrack installed! No hassle is needed to connect to externet networks to access your data.

Given below are my contributions to the project.

* **New Feature**: Added the ability to remove a class from EduTrack
  * **What it does:** Allows the user to remove existing classes, so that information in EduTrack remains up to date
  * **Justification:** This feature is important to the product as it enables the user to correct mistakes made when creating a new class
  * **Highlights:** This is a core feature of the product as it is part of the CRUD of EduTrack. To implement this feature well, it requires a good understanding and analysis of the relationship between class and students. After a great deal of analysis, I created a cascading effect where the deletion of a class would result in the deletion of the students of the class as well

* **New Feature**: Added the ability to mark a student present for a lesson in EduTrack
  * **What it does:** Allows the user to mark a student present if they attended their lesson, this enables the user to keep track of their student's attendance
  * **Justification:** This feature is important because it serves as the main functionality of our application. Which is to enable users with a convenient way to track the present and overall attendance of their students
  * **Highlights:** This feature was challenging as I had to weigh the pros and cons of two different implementation methods. The more challenging solution is to maintain a state of all lessons which would have been difficult to implement and moved the project towards a "morph" methodology. Thus I decided to stick with a simpler approach which requires integration with another feature of the application (Total class attendance)

* **New Feature**: Added the ability to mark a student absent for a class in EduTrack
  * **What it does:** Allows the user to mark a student absent if they did not attend their lesson, this enables the user to keep track of their student's attendance
  * **Justification:** This feature is important because it serves as the main functionality of our application. Which is to enable users with a convenient way to track the present and overall attendance of a student
  * **Highlights:** Similar to the previous feature, this feature was challenging as I had to weigh the pros and cons of two different implementation methods. The more challenging solution is to maintain a state of all lessons which would have been difficult to implement and would have moved the project towards a "morph" methodology. Thus I decided to stick with a more simple approach which requires integration with another feature of the application (Total class attendance).

* **New Feature**: Added the ability to mark all students in a class present in EduTrack
  * **What it does:** Allows the user to easily mark all students in their class present for a lesson, which will be very convenient in the event most of all their students attend their class
  * **Justification:** This feature adds a lot of convenience to the user especially when they have many students to mark present
  * **Highlights:** I was able to build on the mark student present for this command. This greatly reduced code required and complexity to implement this feature which made the code easier to maintain

* **Non-feature**: Added test cases for the mark all students absent in a class command and its parser in EduTrack

* **Code contributed**: [RepoSense Link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=donovanjj&breakdown=true)

* **Project management**:
  * Managed releases of `v1.2`, `v1.3`, `v1.3.trial` and `v1.4` on Github
  * Established well-defined internal deadlines for every milestone, to ensure everyone in the team knows when to do assigned tasks by
  * Managed the milestones and issues for all iterations which helped to ensure that tasks are clearly segregated and defined for each member
  * Co-led discussions during weekly team meetings
  * Planned out final product demo video structure

* **Documentation**:
  * User Guide:
    * **remove class:** Added and maintained the `remove class` feature in the user guide (Pull request [#27](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/27), [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88))
    * **mark student present:** Added and maintained the `mark` feature in the user guide (Pull request [#72](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/72), [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88))
    * **mark student absent:** Added and maintained the `unmark` feature in the user guide (Pull request [#72](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/72), [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88))
    * **mark all students present:** Added and maintained the `markall` feature in the user guide (Pull request [#72](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/72), [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88))
  * README:
    * Updated the README to remain consistent with our project (Pull request [#27](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/2), [#88](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/88))
  * Developer Guide:
    * Added use cases for the following:
      * **Remove a class** (Pull request [#27](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/27))
      * **Adding and removing a lesson to a class schedule which will be removed** (Pull request [#27](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/27))
      * **Mark a student present for a lesson** (Pull request [#154](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/154/files))
      * **Mark all students present in a class** (Pull request [#154](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/154/files))
    * Added the following items for the `mark` student present command:
      * Implementation
      * Design consideration
      * Sequence diagram
      * UML Object diagram
      * Activity diagram

* **Community**:
  * **PR reviewed (with non-trivial review comments)**: (Pull request [#39](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/39), [#138](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/138))
  * **Brought up bug in `remove class` command** (Issue [#81](https://github.com/AY2324S1-CS2103T-T15-3/tp/issues/81))
  * **Brought up bug in `view class` command parser**
