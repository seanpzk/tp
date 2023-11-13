---
layout: default.md
title: "Sudarshan's Project Portfolio Page"
---

### Project: EduTrack

EduTrack is a **powerful** product that is designed for teaching assistants in the School of Computing (SoC) who type fast and have many classes and students to manage. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, EduTrack can get your student and class management tasks done faster than traditional GUI apps. It is written in Java and has more than 10 kLoC.

Given below are my contributions to the project. <br>
* **New Feature**: Added the ability to view a class and its students

  * **What it does:** Allows the user to view a class, the information stored about the class and the students in the class
  * **Justification:** This feature is fundamental to the product as it allows the user to view the information stored in the application. This feature is also a core feature in the product as it is part of the CRUD of the EduTrack application. To implement this feature well, it requires a good understanding and analysis of the relationship between class and students, where the viewing of a class should result in a cascading effect on the students of this class. It is required to work with all other CRUD operations to ensure that the information displayed is accurate and up to date.
  * **Highlights:** This feature had to be constantly monitored and updated as the other CRUD operations were being implemented. This was to ensure that the information displayed was accurate and up to date. This meant that I had to work closely with the other members of the team to ensure that the information displayed was accurate and up to date. This also allowed be to gain a great understanding of the application as a whole. <br>
* **New Feature**: Added the ability to list all classes

  * **What it does:** Allows the user to view all the classes stored in the application
  * **Justification:** This feature is fundamental to the product as it allows the user to choose which class that they want to view and update from all of their current classes.
  * **Highlights:** This feature was initially modified from the AB3's list command but eventually had to be done from scratch to better fit our implementation of classes. <br>
* **New Feature**: Updated the AB3 User Interface (UI) to match our product needs

  * **What it does:** Updated the UI to include sections for the class information, class list as well as the student list.
  * **Justification:** In order for users to be able to understand the information provided by the product, updating the UI was necessary as the existing AB3 UI did not provide us with ways to dipslay and update the new information such as class attendance and class notes.
  * **Highlights:** Understanding how the AB3 UI functioned took time to understand. On top of this, as we implemented new fields and features, the UI had to be constantly updated. <br>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=sudarshan2401&breakdown=true)<br>
* **Project management**:

    * Established and enforced internal and external deadlines for the team to ensure that the project was completed on time
    * Ensured that the team was on track by monitoring progress from GitHub and the project board <br>
* **Enhancements to existing features**:

  * Updated the AB3 UI to match our product needs
  * Updated the `help` command to match our product needs
  * Updated the `edit student` command to add additional fields such as class participation <br>
* **Documentation**:

  * User Guide:
    * **view class:** Added and maintained the documentation for the `view` feature in our user guide
    * **list class:** Added and maintained the documentation for the `list` feature in our user guide
    * **help:** Added and maintained the documentation for the `help` command in our user guide
    * **EduTrack's Graphical User Interface** section: Added and maintained the documentation for the UI changes made to EduTrack
    * **Known Issues:** Added and maintained the documentation for the known issues in our user guide such as the UI bugs regarding resizing of the application

  * Developer Guide:
    * Added use cases for the following features:
      * **view class:** Added and maintained the documentation for the `view` feature in our developer guide
      * **list class:** Added and maintained the documentation for the `list` feature in our developer guide
    * Added UML diagrams for `view` feature
      * Object Diagram
      * Sequence Diagram
      * Activity Diagram <br>
* **Community**:

  * PRs reviewed (with non-trivial review comments): (Pull Request [18](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/18), [34]((https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/34)), [#40](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/40), [#59](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/59), [#150](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/150))
    * Reported bugs and suggestions for other features such as the `edit student` command and the `add student` command due to linkage with the `view class` feature <br>
* **Miscellaneous Contributions**:

  * Created various UI mockups for the team to discuss and decide on the final UI (e.g. [UI mockup](../images/UiMockup.png))
