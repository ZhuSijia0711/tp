# User Guide

### Introduction

Welcome to **TimeTableComparer**! This application is meant for creating and storing task within a weekly calendar and seeing shared time between user's timetables. A convient way to determine shared free time!

## Table of Contents
- [Quick Start](#quick-start)

## Quick Start <a name="quick-start"></a>
Downloading TimeTableComparer

1. Ensure that you have [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or above installed.
2. Down the latest version of `tP.jar` from [here](http://link.to/duke).
3. Copy the file to the folder you want to use as the home folder for the application. For example,`C:\Users\setupuser\Documents\StockPal\stockpal.jar`.
4. Open your terminal or gitBash and `cd` into the folder you placed the .jar file.
5. Run `java -jar tP.jar`

## Features 
> Note: `CAPITAL_LETTERS` Indicate the section for user's input.

## Adding a user: `adduser`

Add a new user into the user list.

Format: `adduser NAME`

Example: `adduser mike`

Expected Output: 
```
New user added: mike
The active user is: mike\n
File created: mike.txt
```
> *If this is the first user created then the active user will be set to the new user*

## Show current user: `current`

Displays the active user.

Format:`current`

Example: `current`
```
adduser john
adduser jill
adduser jack
current
```
Expected Output:
```
The active user is: john
```

## Listing all existing users: `list`

View the list of existing user names.

Format: `list`

Example: `list`

Expected Output: 
```
The current users are: 
jane
john
jill
```

## Switch to another user: `switch`

Switch the active user to the inputted name.

Format: `switch USER_NAME`

Example: `switch jane`

Expected Output:
```
The active user is: jane
```

## Adding a task: `addtask`

Add a new task into the active user's list.

Format: `addtask /on DAY /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`
> * Both starting time and end time must be in the format of HH:MM or H:MM.
> * TASK_TYPE is either `f` (for flexible tasks) or `c` (for compulsory tasks).

Example: `addtask /on monday /task lecture /from 11:00 /to 12:00 /type f`

Expected Output: 
```
The following task is added: lecture (monday from 11:00 to 12:00) type: f
Timetable has been written to data/mike.txt
```

## Adding a task with duplication check: `addtwdc`

Adding a new task with a duplication check, ensuring that task of this type does not get duplicated.

Format: `addtwdc /on DATE /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`
>* Both starting time and end time must be in the format of HH:MM or H:MM.
>* TASK_TYPE is either `f` (for flexible tasks) or `c` (for compulsory tasks).

Example: `addtwdc /on monday /task lecture /from 11:00 /to 12:00 /type f`

Expected Output (Duplicate Found):
```
Invalid command. 
Task already exists. Cannot add duplicate task.
```

## Delete task from one user's timetable: `deletetask`

Delete a task given a specific day and task number.

Format: `deletetask /on DAY /index TASK_INDEX`

Example: `deletetask /on monday /index 1`

Expected Output:
```
Task lecture is deleted from monday
New task list for Monday:
No task for monday!
Timetable has been written to data/jane.txt
```
> This output is given that we deleted the only task on monday.

## Compare two timetables: `compare`

Compare timetables between two specified users.

Format: `compare NAME_1 NAME_2`

Example: `compare john jane`

Expected Output:
```
_________________________________________
Shared free time on Monday:
    00:00 - 09:00
    11:00 - 12:00
    13:00 - 23:59
_________________________________________
Shared free time on Tuesday:
    00:00 - 09:00
    11:00 - 23:59
_________________________________________
Shared free time on Wednesday:
    ** Whole day is free on Wednesday
_________________________________________
Shared free time on Thursday:
    ** Whole day is free on Thursday
_________________________________________
Shared free time on Friday:
    00:00 - 13:00
    14:00 - 23:59
_________________________________________
Shared free time on Saturday:
    ** Whole day is free on Saturday
_________________________________________
Shared free time on Sunday:
    ** Whole day is free on Sunday
```

## Compare all timetables: `compareall`

Compare timetables between all existing users.

Format: `compareall`

Example: `compareall`

Expected Output:
```
Comparing all timetables: 
_________________________________________
Shared free time on Monday:
    00:00 - 09:00
    11:00 - 12:00
    13:00 - 23:59
_________________________________________
Shared free time on Tuesday:
    00:00 - 09:00
    11:00 - 23:59
_________________________________________
Shared free time on Wednesday:
    ** Whole day is free on Wednesday
_________________________________________
Shared free time on Thursday:
    ** Whole day is free on Thursday
_________________________________________
Shared free time on Friday:
    00:00 - 13:00
    14:00 - 23:59
_________________________________________
Shared free time on Saturday:
    00:00 - 02:00
    04:00 - 23:59
_________________________________________
Shared free time on Sunday:
    ** Whole day is free on Sunday

```

## Add a task for all existing users: `addforall`

Add a task for all existing users.

Format: `addforall /on DAY /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`

Example: `addforall /on sunday /task laundry /from 7:00 /to 9:00 /type f`

Expected Output:
```
Timetable has been written to data/jane.txt
Timetable has been written to data/john.txt
Timetable has been written to data/jill.txt
The following task is added for all users: laundry (sunday from 07:00 to 09:00) type: f
```

## Change the start time or end time of a task: `changetasktiming`

Change the starting/ending time of a task given a day and task number.

Format:`changetasktiming /on DAY /index TASK_INDEX /from TIME /to TIME`

Example: `changetasktiming /on monday /index 1 /from 12:00 /to 13:00`

Expected Output:
```
Flexible task timing changed successfully.
Timetable has been written to data/jane.txt
```

## Change the task type: `changetasktype`

Format:`changetasktype /on DAY /index TASK_INDEX /type F_OR_C`

Example: `changetasktype /on monday /index 1 /type c`

Expected Output:
```
Task type changed successfully.
Timetable has been written to data/jane.txt
```

## View today's tasks: `todaytask`

Format: `todaytask`

Example: `todaytask`

Expected Output:
```
_________________________________________
Today :
    1. walk dog (friday from 13:00 to 14:00) type: c
```
>Given that tasks exist the day this command is ran.

## Add a task which occurs on multiple days: `addrepeaettask`

Add a task which occurs on multiple days given a set of days.

Format:` addrepeattask /task TASK_NAME /on DAYS /from START_TIME /to END_TIME /type F_OR_C`

Example: `addrepeattask /task lecture /on monday tuesday /from 9:00 /to 11:00 /type c`

Expected Output:
```
Timetable has been written to data/user.txt
Repeated task added successfully!
```
## List name of commands: `help`

Format: `help`

Example: `help`

## Exit the program: `bye`

Format: `bye`

Example: `bye`

Expected Output: `Bye.`

## FAQ

**Q**: Where are the timetables stored?

**A**: The timetables are stored in the folder named "data" in the same directory. 
The name of the file indicates the owner of the timetable.

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
