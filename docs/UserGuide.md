# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
2. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

{Give detailed description of each feature}

### Adding a user: `adduser`

add a new user into the user list.

Format: `adduser /NAME`

Example: `adduser /MIKE`

### adding a task: `addtask'

Format: `addtask /on DAY /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`
* Both starting time and end time must be in the format of HH:MM.
* Task_type is either `f` (for flexible tasks) or `c` (for compulsory tasks).

Example: `addtask /on monday /task lecture /from 11:00 /to 12:00 /type f`
### adding a task with duplication check: `addtwdc'

Format: `addtwdc /on DATE /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`

Example: `addtwdc /on monday /task lecture /from 11:00 /to 12:00 /type f`

### showing current user: `current`

Format:`current`

### switch to another user:`switch`

Format: `switch USER_NAME`

Example: `switch max`

### delete task from one user's timetable: `deletetask`

Format: `deletetask /on DAY /index TASK_INDEX`

Example: `deletetask /on monday /index 1`

### change the start time or end time of a task: `changetasktiming`

Format:`changetasktiming /on DAY /index TASK_INDEX /from TIME /to TIME`

Example: `changetasktiming /on monday /index 1 /from 12:00 /to 13:00`

### change the task type: `changetasktype`

Format:`changetasktype /on DAY /index TASK_INDEX /type F_OR_C`

### view today's tasks: `todaytask`

Format: `todaytask`

### Adding a todo: `todo`
Adds a new item to the list of todo items.

Example: `changetasktype /on monday /index 1 /type c`

### add task which happens on multiple days into the timetable: `addrepeaettask`

Format:` addrepeattask /task TASK_NAME /on DAYS /from START_TIME /to END_TIME /type F_OR_C`

Example: `addrepeattask /task lecture /on monday tuesday /from 9:00 /to 11:00 /type c`


## FAQ

**Q**: Where are the timetables stored?

**A**: The timetables are stored in the folder named "data" in the same directory. 
The name of the file indicates the owner of the timetable.

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
