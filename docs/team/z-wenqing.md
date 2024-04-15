# Zhang Wenqing's Project Portfolio Page

## Project: Timetable Comparer

### Team: AY2324S2-CS2113-T13-2

Timetable Comparer is a desktop application used for timetable management among a group of users. It operates through a Command Line Interface (CLI), enabling users to add their weekly tasks and utilize the tool to find common free time slots for group activities.

Given below are my contributions to the project.

* **Code Contributed**: https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=z-wenqing&breakdown=true


* **New feature**: Added the ability to store user data such as username and timetable of each user.
  * When the user launches the application for the first time, a designated folder is created in the application's directory to store all data.
  * When a new user is added, a file named after the user's name will be created within the created data folder.
  * Tasks added by users are recorded in their respective files.
  * On subsequent launches, all the existing users and their corresponding timetables will be retrieved and loaded, allowing users to check their existing tasks.


* **New feature**: Added the feature to check whether the task added clashes with the existing tasks.


* **New feature**: Added the feature to add tasks for multiple users.
  * The users are able to add a task for multiple specified users simultaneously. This feature makes it more convenient for users to add common activities for multiple users, such as attending lectures, and they do not have to add tasks one by one.
  * The usernames inputted by the user will be checked against the existing users and their timetables to make sure that the users specified are valid users and the task added does not clash with the existing tasks. The usernames that are invalid, as well as users that have conflict tasks will be outputted for users to clearly see the issue.


* **New feature**: Added the feature to add tasks for all users.
  * The user can add a confirmed group activity for all users and they can also check events that are common for all users.


* **Contributions to the UG**
  * Add examples of command in version 1.0.


* **Contributions to the DG**
  * Add sequence diagram for the interaction between Storage class and other classes, illustrating how the storing and retrieving data work.


* **Contributions to team-based tasks** 
  * Set up the GitHub team org/repo.
  * General code enhancements to fix testing errors and style errors to pass gradle checks.


* **Testing**
  * Added extensive testing for storage class. A new folder named "test_data" is created for testing. During testing, the directory will be directed to "test_data" instead of "data" folder where all the actual user data is stored.
  * Helped teammates to fix test case errors where storage is involved.

