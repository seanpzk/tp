---
  layout: default.md
  title: "Wang Helin's Project Portfolio Page"
---

## revdrag's Project Portfolio Page

### Project: EduTrack

#### Overview

EduTrack is a **powerful desktop app** designed for SOC tutors, allowing them to streamline their teaching tasks and effortlessly track their student records. The user interacts with it using a Command Line Interface (CLI), and it has a Graphical User Interface (GUI) created with JavaFX. It is written in Java, and has about 13 kLOC.

#### Summary of Contributions
Given below are my contributions to the project.

*  **Code Contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=revdrag&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)
* **New Feature**: Added the ability to add students to classes.
  * What it does: Allows the user to add students into the classes they created prior.
  * Justification: This feature improves the product significantly because it allows users to track student's records such as their name, student number and memo.
  * Highlights: There were different design considerations when implementing this enhancement. To restrict users from creating invalid student numbers, it uses regex validation but that would require it to be a non-null value.
    Instead of requiring a valid student number at the point of student creation, because there can be reasons such as the user not having the information at hand or deem not necessary to keep track, the feature will instead populate it with a default value of `A0000000Z` for the user to edit in the future.
* **New Feature**: Added the ability to edit student records.
  * What it does: Allows the user to make changes to student records such as name, student number and memo.
  * Justification: This feature improves the product significantly as the user is able to keep track of their student records and write personalised memos for them.
  * Highlights: Because the adding student feature does not allow any change in student record such as student number or memo, it is necessary for an edit feature for users to edit such information.
* **New Feature**: Added sample data generation to EduTrack.
  * What it does: Populates EduTrack on launch with sample data for first time users or developers.
  * Highlights: This feature improves the product as users are now able to tinker around with EduTrack before they start using the application. This will allow them to familiarise themselves with how the GUI looks like in a typical EduTrack application. They will also be able to experiment with all the commands available and perform some manual testing with the help of the sample data.
* **Project Management**:
  * Setup GitHub Team Organisation and Repository
  * Setup Project Website: Migrate to MarkBind and set up GitHub Pages, helped partially to change names and links from AddressBook to EduTrack on the website such as `index.md`. (Pull Requests [#1](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/1), [#30](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/30))
  * Add `Appendix - Planned Enhancements` into website layout for easier navigation (Pull Request [#153](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/153))
* **Enhancements to existing features**:
  * Added the ability to store `Memo` information (was initially `Note` but changed to de-conflict `Prefix` used) in `Student` and `Class` objects. (Pull Request [#58](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/58))
  * Added the ability to store `Id` information in `Student` objects. (Pull Request [#58](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/58))
  * Updated many of the test cases related to AddressBook's json file testing to EduTrack. (Pull Requests [#42](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/42), [#58](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/58), [#63](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/63))
* **Documentation**:
  * User Guide:
    * Help to improve the structure of the UG by splitting our features into 3 subsections and added the About, Glossary, FAQ sections. (Pull Request [#73](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/73))
    * Added documentation for the features `add /s` and `edit /s`. (Pull Requests [#30](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/30), [#73](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/73), [#87](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/87))
  * Developer Guide:
    * Added use cases. (Pull Request [#35](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/35))
    * Added implementation details for the Edit Student feature. (Pull Request [#63](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/63))
    * Updated the section `Appendix - Instructions for manual testing` (Pull Request [#149](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/149))
* **Community**:
  * Pull Request reviews: [#57](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/57), [#64](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/64), [#85](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/85), [#146](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/146), [#156](https://github.com/AY2324S1-CS2103T-T15-3/tp/pull/156)
  * Reported bugs and provided suggestions whenever possible for other teams in the class during PE-D. (Issues created: [Repo](https://github.com/revdrag/ped/issues))
