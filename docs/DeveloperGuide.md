---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# EduTrack Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

* [JavaFX](https://openjfx.io/)
* [Jackson](https://github.com/FasterXML/jackson)
* [JUnit5](https://github.com/junit-team/junit5)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<box type="tip">

**Tip:** The `.puml` files used to create diagrams used in this document can be found in the [diagrams](https://github.com/AY2324S1-CS2103T-T15-3/tp/tree/master/docs/diagrams). Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</box>


### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClassListPanel`, `StudentListPanel` , `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- has specific updates based on certain commands e.g. `ListCommand` will update the `listPanelPlaceholder` to replace the `StudentListPanel` with the `ClassListPanel` and vice versa.
- depends on some classes in the `Model` component, as it displays `Student` and `Class` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/RemoveClassSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `remove /c 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `RemoveClassCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `EduTrackParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `RemoveClassCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to remove a class).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `EduTrackParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddStudentCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddStudentCommand`) which the `EduTrackParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddStudentCommandParser`, `RemoveClassCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the class administrative data i.e., all `Class` objects (which are contained in a `UniqueClassList` object) and `Student` objects (which are contained in a `UniqueStudentList`).
- stores the currently 'selected' `Class` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Class>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both EduTrack data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `EduTrackStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**
This section describes some noteworthy details on how certain features are implemented.

### Mark student attendance
#### Implementation

<puml src="diagrams/MarkStudentObjectDiagram.puml" alt="MarkStudentObjectDiagram" />


Step 1. The user launches the application

Step 2. The user executes `view /c 1` command to view the students in the 1st class in EduTrack.

Step 3. The user executes `mark /s 1 /c CS2103T` to mark the 1st student in the class CS2103T.

Step 4. `MarkStudentPresentCommandParser` creates a new `MarkStudentPresentCommand` with the required fields.

Step 5. `LogicManager` calls `MarkStudentPresentCommand#excecute()`.

Step 6. `MarkStudentPresentCommand` calls `Student#duplicateStudent` to create a duplicate Student, `duplicateS`.

Step 7. `MarkStudentPresentCommand` calls `Model#markStudentPresent`.

Step 8. `Model#markStudentPresent` calls `Student#markStudentPresent` to mark the `duplicateS` as present, this updates both `sLessonsAttended` and `sCurrentLessonAttendance`.

Step 9. `Model#markStudentPresent` calls `EduTrack#setStudent` to set `s` as the newly updated `duplicateS` in `EduTrack`'s `globalStudentList`.

Step 10. `Model#markStudentPresent` calls the `Model#setStudentInClass` to set `s` attached to the Class as the updated `duplicateS`.

Step 11. `Model#markStudentPresent` calls the `Model#updateFIlteredStudentList` to update the GUI of Students shown to user.

<box type="info" seamless>

**Note:** If the command fail its execution, both the StudentList in EduTrack as well as StudentList in the class remain unchanged. An error message will be printed to notify the user.

</box>

The following sequence diagram shows how the MarkStudentPresent operation works:

<puml src="diagrams/MarkStudentSequenceDiagram.puml" alt="MarkStudentSequenceDiagram"/>

The following activity diagram shows what happens when a use executes the MarkStudentPresentCommand:

<puml src="diagrams/MarkStudentActivityDiagram.puml" alt="MarkStudentActivityDiagram" />

#### Design considerations:

**Aspect: Method of calculating number of lessons attended

- **Alternative 1 (current choice): Maintain an overall counter for the number of lessons the student attended.
  - Pros: Easy to implement.
  - Cons: Unable to store the individual state of each lesson. Determining which lesson user attended is not possible.
- **Alternative 2: Maintain the state of each individual lesson with an array.
  - Pros: Enable users to view or modify the state of each individual lesson. May lead to more consistency in data, since the number of lessons and length of array is related.
  - Cons: Difficult to implement. More memory usage, predefined maximum lessons required for array creation, although using ArrayList would be possible as well.

This section describes some noteworthy details on how certain features are implemented.


### Edit Student feature
#### Implementation
The `edit /s` command is facilitated by creating an `EditStudentCommand` depending  on the given input.
This command will update the student's details and update the `model` accordingly.

The edit student command accepts:
- Compulsory parameters
  - Student's index in the class
  - Class name of the student's class
- At least one of these parameters
  - Name
  - Id
  - Memo
  - Class Participation

The following activity diagram summarizes what happens when a user executes an `edit /s` command.
<puml src="diagrams/EditStudentActivityDiagram.puml" alt="EditStudentActivityDiagram" />

Given below is an example usage scenario and how the edit student operation behaves at each step.

Step 1. A valid command `edit /s 1 /c CS2103T /n John` is given as a user input. This invokes `LogicManager#execute()`, which calls `EduTrackParser#parseCommand()` to parse the above command into command word `edit /s` and command argument `/s 1 /c CS2103T /n John`.

Step 2. `EditStudentCommandParser` is initialised based on the parse results and `EditStudentCommandParser#parse()` is called. `EditStudentCommandParser#parse()` will call `ArgumentTokenizer#tokenize()` to identify all the prefixes such as `Index` of the Student being edited as well as the `ClassName` the student is supposed to be in. (ie. `1` and `CS2103T` respectively). It will also obtain an `ArgumentMultimap` of prefixes to their respective arguments (ie. `/n` is mapped to `John`).

Step 3. `EditStudentCommandParser#parse()` then initialises and EditStudentDescriptor that stores the details to edit the student with. Thus, `EditStudentDescriptor#setName()` will be called to store `John` as the `Name` to be edited.

Step 4. `EditStudentCommandParser#parse()` then initialises an EditStudentCommand with the `Index`, `ClassName` and `EditStudentDescriptor` as an argument. `EditStudentCommand#execute()` is then called, which creates a new `Student` and copies over the details to be edited from the `EditStudentDescriptor` into both the `UniqueStudentList` of the `Class` and `EduTrack`.

Step 5. After checking that the new `Student` is not a duplicate using `Class#hasStudentInClass()` and `Student#isSameStudent()`, `Model#setStudent` and `Model#setStudentInClass` will be called to replace the specified `Student`. Finally, `Model#updateFilteredStudentList()` is called to reflect the changes made to the `Student`.

Step 6. `CommandResult` is then initialised with the `String` containing `MESSAGE_EDIT_PERSON_SUCCESS` and `Student` which will then be returned.

The following sequence diagram shows how the edit student operation works:
<puml src="diagrams/EditStudentSequenceDiagram.puml" alt="EditStudentSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `EditStudentCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

---

### Remove Student feature
#### Implementation

The removal of Student implements the following operation:
- `EduTrack#removeStudent(Student s)`  — Removes the Student s from List of Students in EduTrack that tracks all students.
- `Class#removeStudent(Student s)`  — Removes the Student s from List of Students in Class that tracks all students in Class.

These operations are exposed in the `Model` interface as `Model#deleteStudent(Student s)` and `Model#deleteStudent(Student s, Class sClass)`.

Given below is an example usage scenario of the remove student mechanism at a high level:

Step 1. The user inputs `remove /s 1 /c CS2103T` command to remove the first `Student` in the class named "CS2103T".

Step 2. `Logic`is called upon to execute the above command, it is passed to an `EduTrackParser` object which in turns 
create a `RemoveStudentCommandParser` and uses it to parse the command.

Step 3. This results in `RemoveStudentCommand`, referred as `cmd` in this scenario, which is executed by the `LogicManager`.

Step 4. `cmd` communicates with the `Model` when executed to remove the first `Student` in `Class` CS2103T.

Step 5. The result of the command execution is encapsulated as a `CommandResult` object which is returned back to `Logic`.

The scenario is depicted by this sequence diagram. For execution of `cmd`, refer to this [sequence diagram](#execution-of-a-removestudentcommand).
##### Remove Student Mechanism
<puml src="diagrams/RemoveStudentSequenceDiagramPart1.puml" alt="RemoveStudentSequenceDiagramPart1" />
**Note:** The lifeline for `RemoveStudentCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
The mechanism to execute a `RemoveStudentCommand` is elaborated in the below walkthrough.
This is a list of variables used in the walkthrough for clarity.

**Variables used:**

`cmd` - `RemoveStudentCommand` communicates with `Model` to remove a `Student`

`s` - `Student` to be removed.

`sName` - `Name` representing `s`.

`sClass` - `Class` of `s`.

`studentClassName` - `ClassName` representing `sClass`.

`sClassStudentList` - `UniqueStudentList` in `sClass` containing all its `Student`

`globalStudentList` - `UniqueStudentList` in `EduTrack` containing all `Student` across all `Class`.

The relationship between variables can be summarised by this object [diagram](#relationship-between-key-variables).

##### Relationship between key variables
<puml src="diagrams/RemoveStudentObjectDiagram.puml" alt="RemoveStudentObjectDiagram" />

**Walkthrough**

Step 1. `LogicManager` calls `RemoveStudentCommand#execute()`.
 
Step 2. `cmd` calls `Model#getClass(studentClassName)` to get `sClass`.

Step 3. `cmd` calls `Model#getStudentList(sClass)` to get the `sClassStudentList` from `sClass`.

Step 4. `cmd` calls `Model#getStudent(sClassStudentList, 1)` to get `s`, the first student in the `sClassStudentList`.

Step 5. `cmd` calls `Model#getStudentName(s)` to get `sName`.

Step 6. `cmd` calls  `Model#deleteStudent(s, sClass)` to remove `s` from `sClassStudentList`.

Step 7. `cmd` calls `Model#deleteStudent(s)` to remove `s` from `globalStudentList`.

Step 8. The result of the `cmd`'s execution is encapsulated as a `CommandResult` object.

Step 9. `cmd` returns `CommandResult` to `LogicManager`.

The walkthrough can be summarised by this sequence diagram. (Some details are omitted in the diagram)

##### Execution of a `RemoveStudentCommand`
<puml src="diagrams/RemoveStudentSequenceDiagramPart2.puml" alt="RemoveStudentSequenceDiagramPart2" />
**Note:** The lifeline for `Student` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
The following activity diagram summarises what happen when a user removes a Student:

##### Remove Student Workflow
<puml src="diagrams/RemoveStudentActivityDiagram.puml" alt="RemoveStudentActivityDiagram" />

**Implementation reasoning:**
1. `RemoveStudentCommand` leverages multiple methods from other classes to enhance abstraction, ultimately promoting higher cohesion within the system.
2. `RemoveStudentCommand` is responsible for deletion of `s` from `globalStudentList` under `EduTrack` to enable creation of `Student` with same `Name` in the future. This is necessary because `EduTrack` enforces the uniqueness of student names in the `globalStudentList`.

### View Class feature

#### Implementation

The View Command feature allows users to list all students in a specified class. It is implemented as follows:

- `Model#updateFilteredClassList(Predicate<Class> predicate)` — Updates the filtered class list based on a given predicate. This is used to display the specified class along with its students.

- `Model#updateFilteredStudentList(Predicate<Student> predicate)` — Updates the filtered student list based on a given predicate. In this case, it filters students belonging to the specified class.

- `Model#getClassByIndex(Index classIndex)` — Retrieves the class object based on the provided index.

- `Class#getStudentList()` — Gets the list of students associated with a specific class.

- `Class#getClassName()` — Gets the name of the class for display purposes.

- `CommandResult` — Returns a command result with a success message indicating the class name.

The workflow of the View Command feature is outlined below:

1. The user invokes the View Command with a specified class index.

2. The command is executed, calling the appropriate methods in the `Model`.

3. The `Model` updates the filtered class list to be reset to all classes.

4. The specified class is retrieved using the class index.

5. The `Model` updates the filtered class list again, this time with a predicate that filters for the specified class.

6. The filtered student list is updated to show only students belonging to the specified class.

7. The command returns a success message with the name of the class.

**Variables used:**

- `classIndex` - The index of the class specified by the user.

- `classToView` - The class object corresponding to the specified index.

- `classToTest` - A class used in the filtering predicate.

- `student` - A student object used in the filtering predicate.

The interaction between these variables is illustrated in the following object diagram:
<puml src="diagrams/ViewClassObjectDiagram.puml" alt="ViewClassObjectDiagram" />

#### Walkthrough

1. LogicManager calls `ViewCommand#execute()`.

2. `ViewCommand` calls `Model#getClassByIndex(classIndex)` to retrieve the class object corresponding to the specified index.

3. `ViewCommand` calls `Model#updateFilteredClassList(Predicate<Class> predicate)` to update the filtered class list.

4. `ViewCommand` calls `Model#updateFilteredStudentList(Predicate<Student> predicate)` to update the filtered student list which is displayed on the UI.

5. `ViewCommand` returns a `CommandResult` with a success message.

The interaction between these variables is illustrated in the following sequence diagram:
<puml src="diagrams/ViewClassSequenceDiagram.puml" alt="ViewClassSequenceDiagram" />

The following activity diagram summarises what happens when a user executes the View Command:
<puml src="diagrams/ViewClassActivityDiagram.puml" alt="ViewClassActivityDiagram" />

### Add a class feature

#### Implementation

Given below is an example usage scenario and how the add class mechanism behaves at each step:

Step 1. The user launches the application.

Step 2. The user executes `add /c cs2103t` command to add a new class with the class name "CS2103T". The `add /c` command calls `Parser#AddClassCommandParser` to retrieve the provided class name argument.

Step 3. A new `Class` is created with the specified class name, an empty student list, an empty class note and empty class schedule.

Step 4. The created `Class` is added to `UniqueClassList` if it does not exists in UniqueClassList. Then the `Storage` is updated to save the current state of `EduTrack`.

<puml src="diagrams/AddAClassObjectDiagram.puml" alt="AddAClassObjectDiagram" />

Step 5. The application prints the successful message to the user. UI automatically updates the current state of the shown class list.

<box type="info" seamless>

**Note:**

- The received class name will be converted to uppercase using the `toUpperCase()` method.
- If the command fails its execution, the class list remains unchanged and an error message will be printed to notify the user.

</box>

The following sequence diagram shows how the add class operation works:

<puml src="diagrams/AddAClassSequenceDiagram.puml" alt="AddAClassSequenceDiagram" />

1. `LogicManager#execute()` is called.
2. `EduTrackParse#parseCommand()` is called.
3. `AddClassCommandParser#parse()` is called, a new `Class c` is created. Returns `AddClassCommand addClassCommand`.
4.  `addClassCommand` calls `Model#hasClass(c)`. If class does not exists, calls `Model#addClass(c)`.
5.  `addClassCommand` returns `CommandResult` to `LogicManager`.

The following activity diagram summarizes what happens when a new Class is added:

<puml src="diagrams/AddAClassActivityDiagram.puml" alt="AddAClassActivityDiagram" />

#### Design Consideration

**Aspect: The number of parameters required**

- **Alternative 1 (current choice):** Creates a new class with only class name.

  - Pros: Easy to implement.
  - Cons: Needs to use another command to update class note and class schedule.

- **Alternative 2:** Creates a new class with class name, class note and class schedule.

  - Pros: All information are specified by the time the class is created.
  - Cons: Requires the user to provide additional details like class notes or class schedule

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

School of Computing (SoC) tutor who:

- has a need to manage classes with a significant number of students
- needs to do tasks like taking attendance, and keeping track of class participation
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: Each tutor has multiple groups of students where each group can be large, keeping track of students and records would become a hassle. Our product provides a centralised system that would help to organise all student records for easy and quick access.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​  | I want to …​                              | So that I can…​                                                   |
| -------- | -------- | ----------------------------------------- | ----------------------------------------------------------------- |
| `* * *`  | TA       | add a class                               | -                                                                 |
| `* * *`  | TA       | remove a class                            | -                                                                 |
| `* * *`  | TA       | view a class                              | view all students of a specific class                             |
| `* * *`  | TA       | add a student                             | -                                                                 |
| `* * *`  | TA       | remove a student                          | -                                                                 |
| `* * *`  | TA       | save the data automatically               | ensure that my data is not lost when I forget to save             |
| `* *`    | TA       | update a class                            | keep the class information up to date                             |
| `* *`    | TA       | update a student                          | keep the student record up to date                                |
| `* *`    | TA       | take attendance in a lesson               | spend more time teaching                                          |
| `* *`    | TA       | monitor a lesson of a class               | keep track of administrative information for each lesson          |
| `* *`    | TA       | find a specific student by using a filter | quickly update their record such as attendance when they are late |
| `* *`    | new user | see the app populated with sample data    | easily see what the app looks like when it is in use              |

### Use cases

(For all use cases below, the **System** is the `EduTrack` and the **Actor** is the `user`, unless specified otherwise)

**Use case: See the app populated with sample data**

**MSS**

1. User launches the app for the first time.
2. EduTrack populates the app with sample data.

   Use case ends.

---

**Use case: View all the information about a class**

**MSS**

1. User chooses a class out of all the classes they are teaching.
2. User requests to view all the information about that class.
3. EduTrack displays all the information about that class. (e.g. name, students, attendance, notes, etc)

---

**Use case: Add a class**

**MSS**

1.  User requests to add a class and specifies the class name.
2.  EduTrack adds the class to the list of classes.
3.  EduTrack shows the updated list of classes.

**Extensions**

- 1a. Class name is not specified.

  - 1a1. EduTrack informs user that class name is empty.

    Use case ends.

- 1b. Class name already exists.

  - 1b1. EduTrack informs user that class already exists.

    Use case ends.

---

**Use case: Remove a class**

**MSS**

1.  User requests to view the list of classes.
2.  EduTrack shows a list of classes.
3.  User requests to delete a specific class in the list.
4.  EduTrack deletes the class.
5.  EduTrack informs the user that the class is successfully deleted.

    Use case ends.

**Extensions**

- 1a. Class name is not specified.

  Use case ends.

- 3a. The given class index is invalid.

  - 3a1. EduTrack shows an error message.

  Use case ends.

- 3b. No class index specified.

  - 3b1. EduTrack shows an error message.

  Use case ends.

- 4b. EduTrack detects that the class does not exist.

  - 4b1. EduTrack informs user that the class does not exist.
  - 4b2. EduTrack terminates the request.

    Use case ends.

---

**Use case: Edit a class**

**MSS**

1.  User requests to view the list of classes.
2.  EduTrack shows a list of classes.
3.  User requests to update either the class name, memo or schedule of a particular class.
4.  EduTrack overwrites the updated attribute of that particular class.
5.  EduTrack informs that user of the new class details.

**Extensions**

- 2a. The list is empty.

  Use case ends

- 3a. The provided class index is invalid.

  - 3a1. EduTrack informs the user that class index is invalid.

    Use case ends.

- 3b. User request to edit class name but class name is not specified.

  - 3b1. EduTrack informs the user to specify the class name.

    Use case ends.

- 3c. The provided class name already exists in the class list.

  - 3c1. EduTrack informs the user that class name already exists.

    Use case ends.

---

**Use case: Marking a student present for a lesson**

**MSS**

1.  User requests to view the students in a particular class.
2.  EduTrack shows a list of students.
3.  User requests to mark a student present.
4.  EduTrack marks the student present, updates the student's lessons attended counter and current attendance.
5.  EduTrack informs the user the student was successfully marked present.

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given student index is invalid.

  - 3a1. EduTrack shows an error message.

    Use case ends.

- 3b. THe given student index is empty.

  - 3b1. EduTrack shows an error message.

    Use case ends.

- 3c. The given class name is invalid.

  - 3c1. EduTrack shows an error message.

    Use case ends.

- 3d. No class name specified.

  - 3d1. EduTrack shows an error message.

    Use case ends.

- 3e. Lesson details was of invalid format.

  - 3e1. EduTrack informs the user he should enter a lesson of the correct format.

    Use case ends.

---

**Use case: Marking all students present in a class**

**MSS**

1.  User requests to view the students in a particular class.
2.  EduTrack shows a list of students.
3.  User requests to mark a student present.
4.  EduTrack marks the student present, updates the student's lessons attended counter and current attendance.
5.  EduTrack informs the user the student was successfully marked present.

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given class index is invalid.

  - 3a1. EduTrack shows an error message.

    Use case ends.

- 3b. The given class index is empty.

  - 3b1. EduTrack shows an error message.

    Use case ends.

---

**Use case: Add a student**

**MSS**

1. User requests to add a new student to an existing class.
2. EduTrack adds the student into the specified class.

**Extensions**

- 1a. Not all required parameters are present.

  - 1a1. EduTrack shows an error message.

    Use case ends.

- 1b. Student already exists.

  - 1b1. EduTrack informs user that the student already exists.

    Use case ends.

- 1c. Class index is invalid.

  - 1c1. EduTrack informs user that the class index is invalid.

    Use case ends.

---

**Use case: Remove a student**

**MSS**

1. User chooses to remove a student from a class.
2. User requests to delete a specific student in a class.
3. EduTrack removes the student from that class.

   Use case ends.

**Extensions**

- 2a. EduTrack detects that class name is not specified.

  - 2a1. EduTrack informs user that class name is empty.
  - 2a2. EduTrack terminates the request.

    Use case ends.

- 2b. EduTrack detects that student index is not specified.

  - 2b1. EduTrack informs user that student index is empty.
  - 2b2. EduTrack terminates the request.

    Use case ends.
- 2c. EduTrack detects that student index is not valid.
  
  - 2c1. EduTrack inform user that student index is invalid.
  - 2c2. EduTrack terminates the request.
  
    Use case ends.

---

**Use case: Edit a student**

**MSS**

1. User requests to view the students from a particular class.
2. User requests to edit the details of a specific student in the list based on index.
3. EduTrack edits the records of the specified student from the specified class.
4. EduTrack updates and displays the student list of the class.

   Use case ends.

**Extensions**

- 2a. Not all compulsory parameters are present.

  - 2a1. EduTrack shows an error message.

    Use case ends.

- 2b. Compulsory parameters are valid but none of the optional parameters are present.

  - 2b1. EduTrack shows an error message.

    Use case ends.

- 2c. Student index is invalid.

  - 2c1. EduTrack informs user that the student index is invalid.

    Use case ends.

- 2d. Class does not exist.

  - 2d1. EduTrack informs user that class does not exist.

    Use case ends.

- 2e. Another identical student is found.

  - 2e1. EduTrack informs user the student already exists.

    Use case ends.

---

### Non-Functional Requirements

1.  **Environment requirement:**

- Should work on any _mainstream OS_ as long as it has Java `11` or above installed.

2.  **Scalability:**

- Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.

3.  **Usability:**

- A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
- The user interface should be intuitive and user-friendly to minimize the learning curve for TAs.

4.  **Performance**

- The system should respond to user requests within a reasonable time frame (i.e. under 2 seconds).

_{More to be added}_

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, macOS
- **Database**: A storage for all data to be stored in.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info">

**Note:** These instructions only provide a starting point for testers to work on; testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   2. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.
<br>
2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### List all classes

List all the classes that has been added into EduTrack.

1. Test case: `list`<br>
   Expected: All the classes that have been added into EduTrack are shown. If the user is previously viewing a class, switches from class's student list to class list display.

2. Test case: `list 1`<br>
   Expected: Similar to the previous. Additional invalid parameters are ignored.

3. Test case: `list /n John`<br>
   Expected: Similar to the previous.

### Adding a class

Adding a class to EduTrack.

1. Test case: `add /c CS2103T`<br>
   Expected: New class is added.

2. Test case: `add /c cs2101`<br>
   Expected: New class is added.

3. Test case: `add /c cs2101`<br>
   Expected: No class is added. Error details shown.<br>
   Note: This have to be performed after test case 2.

### Removing a class

Removes a class from EduTrack.

1. Prerequisites: List all classes using the `list` command. Multiple classes in the list.

2. Test case: `remove /c 1`<br>
   Expected: First class is removed from the list. Class name is shown in delete message.

3. Test case: `remove /c 0`<br>
   Expected: No classes are deleted. Error details shown.

### Viewing a class

Views the detailed information of a class.

1. Prerequisites: List all classes using the `list` command. Multiple classes in the list.

2. Test case: `view /c 1`<br>
   Expected: Displays the student list of the first class as well as the class details in the Class information box.

3. Test case: `view /c he`<br>
   Expected: No change in display. Error details shown.

### Editing a class

Edits the details of a class in EduTrack.

1. Prerequisites: List all classes using the `list` command. There is a class called `T1` at index 1.

2. Test case: `edit /c 1 /n T0`<br>
   Expected: Name of the class is replaced with `T0`. Displays only the edited class and the edited details.

### Starting a lesson

Starts the lesson of a class.

1. Prerequisites: List all classes using the `list` command. The first class called `CS2103T` have multiple students.

2. Test case: `startlesson /c CS2103T`<br>
   Expected: Starts a new lesson for the class `CS2103T`. Displays the student list of the class and class information. All students total attendance increase by 1 and are now all absent (ie. the `Present` column is all `N`).

3. Some invalid test cases to try (Error details shown):<br>
   * Missing `/c` prefix: `startlesson`
   * Missing class name: `startlesson /c`
   * Non-existent class: `startlesson /c NOTACLASS`

### Setting number of lessons of a class

Sets the total number of a class.

1. Prerequisites: List all classes using the `list` command. The first class called `CS2103T` have multiple students. The current total lessons of the class is `5`.

2. Test case: `setlesson /c CS2103T /l 10`<br>
   Expected: Sets the total lesson to 10 for the class `CS2103T`. Displays the student list and class details of class `CS2103T` and the overall attendance will be out of `10`.

3. Test case: `setlesson /c CS2103T /l 0`<br>
   Expected: Sets the total lesson to 0 for the class `CS2103T`. Displays the student list and class details of class `CS2103T` and the overall attendance will be out of `0`.<br>
   Note: This command is meant for users to edit the total lessons if they have incorrectly `startlesson`. Thus, no checks are done to ensure `/l` is valid. Do not be alarmed by invalid attendance records if command is used incorrectly.

### Adding a student

Adds a student to a class.

1. Prerequisite: List all classes using the `list` command. There is a class called `T1` at index 1 with no students.

2. Test case: `add /s John /c 1`<br>
       Expected: New student called `John` is added to the first class (ie. `T1`). Displays the student list and class information of the class the student is added into (ie. `T1`).

3. Some invalid test cases to try (Error details shown):<br>
   * Missing student name: `add /s /c 1`
   * Class index missing, or it is lesser or equal to 0: `add /s John /c`, `add /s John /c -1`
   * Class index larger than number of classes: `add /s John /c 100`
   * Non-alphanumeric Characters used for student name: `Add /s R@chel /c 1`

### Removing a student

Removes a student from a class.

1. Prerequisite: View the first class called `CS2103T` with multiple students using the `view /c` command. Multiple students in the student list.

2. Test case: `remove /s 2 /c CS2103T`<br>
   Expected: Second student is deleted from the class `CS2103T`. Displays student list of the class the student is removed from.

3. Some invalid test cases to try (Error details shown):<br>
   * Invalid student index: `remove /s 100 /c CS2103T`

### Editing a student

Edits a student record.

1. Prerequisite: View the first class called `CS2103T` with multiple students using the `view /c` command. There is a student named `Bob` in index 1 of the student list.

2. Test case: `edit /s 1 /c CS2103T /n John /id A0000000U /m Quiet`<br>
   Expected: Student name is replaced with `John`, id is replaced with `A0000000U`, memo is replaced with `Quiet`. Details of the edited is shown.

3. Test case: `edit /s 1 /c CS2103T /p Answered some questions.`<br>
   Expected: Ui's class participation column is updated to `Answered some questions.` Details of edited student is shown.

4. Some invalid test cases to try (Error details shown):<br>
   * No optional fields: `edit /s 1 /c CS2103T`
   * Student index larger than number of students in the class: `edit /s 100 /c CS2103T /m Bob`

### Marking a student present

Marks a student present for the current class.

1. Prerequisites: View the first class called `CS2103T` with multiple students using the `view /c` command. There is a student at index 2 who has not been marked present.

2. Test case: `mark /s 2 /c CS2103T`<br>
   Expected: Marks student at index 2 present. Display under `Present` changes from `N` to `Y` and overall attendance increase by 1.

3. Some invalid test cases to try (Error details shown):<br>
   * Student index = 0: `mark /s 0 /c CS2103T`
   * Student index larger than student list: `mark /s 100 /c CS2103T`
   * Class name that does not exist: `mark /s 100 /c NOTACLASS`
   * Marking a student present again: `mark /s 2 /c CS2103T`

### Marking a student absent

Marks a student present for the current class.

1. Prerequisites: View the first class called `CS2103T` with multiple students using the `view /c` command. There is a student at index 2 who has been marked present.

2. Test case: `unmark /s 2 /c CS2103T`<br>
   Expected: Marks student at index 2 absent. Display under `Present` changes from `Y` to `N` and overall attendance decrease by 1.

3. Some invalid test cases to try (Error details shown):<br>
   * Student index = 0: `unmark /s 0 /c CS2103T`
   * Student index larger than student list: `unmark /s 100 /c CS2103T`
   * Class name that does not exist: `unmark /s 100 /c NOTACLASS`
   * Marking a student absent again: `unmark /s 2 /c CS2103T`

### Marking all students in a class present

Marks all student present for the current class.

1. Prerequisites: List all classes using the `list` command. The first class have multiple students.

2. Test case: `markall /c 1`<br>
   Expected: Marks all the students in the first class of EduTrack present where display under `Present` changes to `Y`. Displays the student list of the class that is marked present.

3. Some invalid test cases to try (Error details shown):<br>
   * Missing `/c` prefix: `markall 1`
   * Missing class index: `markall /c`
   * Class index larger than class list: `markall /c 100`

### Help

Showing the help window that contains a link to the User Guide.<br>
Note: If you minimise the window, using the help command will not do anything. Do look for the minimised window in the taskbar of your computer!

1. Test case: `help`<br>
   Expected: Shows the help window successfully.

2. Test case: `help 1`<br>
   Expected: Shows the help window successfully. Additional invalid parameters are ignored.


### Clear

Clearing all stored data in EduTrack.

1. Prerequisites: EduTrack is populated with data (classes, students, both, or none).

2. Test case: `clear`<br>
   Expected: Clears all stored data in EduTrack. Clear will be successful even if EduTrack have no data.

3. Test case: `clear 1`<br>
   Expected: Clears all stored data in EduTrack. Additional invalid parameters are ignored.

### Saving data

Dealing with missing/corrupted data files

1. Make sure that there is a `./data/edutrack.json` file. <br>
   If not, open the application (the jar file) and make some changes (e.g. `add /c T1`) and close the app (by typing in the `exit` command or clicking on the close button).

2. Open `./data/edutrack.json` in a text editor or an integrated development environment (IDE).

3. Remove the starting `{` character of the JSON file and save the file.

4. Launch the app by running `java -jar edutrack.jar` in the console or double-click the application. <br>
   Expected: The GUI should pop up with no entries. The console should give warnings about incorrect data format (due to the removal of the `{` character at the start of the `edutrack.json` file). Now, you can start over and add whatever entries you want.<br>
   Note: If you want to start with populated data, delete the entire `edutrack.json` file and launch the application.

## **Appendix: Planned Enhancements**

1. Enhancement to validation of identical students
    <br><br>
    As of now, to validate that 2 students are identical, the method `isSameStudent` check if all `Student`'s fields: name, id, total attendance, current attendance, memo and class participation are equal.
    <br><br>
    Thus, this allows the user to create (through adding or editing) 2 students that have 1 differing field apart from the name and id field. For example, there can be two `Student` with name Bob, id A0123456Z but one with a memo of "Actively asks question during class" and another with an empty memo. This can be confusing for the user if done unintentionally as the GUI would show 2 students that seem to represent the same student.
    <br><br>
    Enhancement to be made:
    The method `isSameStudent` will be adjusted so that 2 students are identical if and only if both their name and id are the same. This will ensure every student is unique (based on name and id) in EduTrack (i.e. even between different classes).
