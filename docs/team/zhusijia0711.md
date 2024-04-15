# Zhu Sijia's Project Portfolio Page

## Project: Time Comparer

Time Comparer is a desktop application used for time management between users.

The user can add and delete tasks using the application and can compare their 

timetable with other users using the same desktop.

Given below are my contributions to the project.

- **New Feature:** Add a command that allows an user to add one task to his/her timetable.
  - What it does: Allows the user to add a task with a task description, start time, end time and task type one at a time.
  - Justification: This is the basic functionality for a time management application.
  
- **New Feature:** Add a command that allows an user to delete the task he/she previously added.
  - What it does: Allow the user to delete a task using the index of the task in a day.
  - Justification: This is the basic functionality for a time management application.
  
- **New Feature:** Add a command that allows an user to add a same task to different days.
  - What it does: Allow the user to add a task which can happen in multiple days to the timetable.
  - Justification: This feature reduces troublesome of users as they would not need to add repeated task one by one.
  
- **New Feature:** Add a command that allows an user to change their task timing
  - What is does: Allow the user to change the start time and end time for the previously added task.
  - Justification: This feature reduces troublesome of users as they do not need to first delete the task and add the 
    task one more time in order to change the time.
  
- **New Feature:** Add a command that allows an user to change their task type.
  - What it does: In our application logic, if the task type is assigned to be flexible(f), it means the timings of the 
    task can be changed. If a task is marked as compulsory(c), it means its timing cannot be changed. This feature helps 
    users to change the task type whenever there is a change in their plan.
  - Justification: This feature reduces troublesome of users as they do not need to first delete the task and add the
    task one more time in order to change the task type.
  
- - **New Feature:** Add a command that allows an user to see today's tasks(if any).
  - What it does: Allow the users to see the tasks that happens today.
  
- - **New Feature:** Add a command that allows an user to see urgent tasks in the next few hours.
  - What it does: Allow the users to see the tasks that happens in the next few hours in today.
  - If the stated urgent hours exceeds the 23:59, the method will print the tasks until the end of today.
  
- Code contributed: 
  - https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=ZhuSijia0711&tabRepo=AY2324S2-CS2113-T13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false

- Project management:
  - Managed releases v1.release, v2.0 release jar file and user guide pdf

- Enhancement to existing features:
  - Wrote tests for timetable class and input validator class (Pull Request #57, #59)

- Documentation:
  - User guide:
    - Added documentation for the features addtask, delete task and so on. #73
    - Added developer guide. #64

- Community:
  - Reported 9 bugs and suggestions for other teams in the class (above average).