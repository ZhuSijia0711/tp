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

Format: `addtask /on DATE /task TASK_NAME /from TIME /to TIME /type TASK_TYPE`

### showing current user: `current`

Format:`current`

### switch to another user:`switch`

Format: `switch USER_NAME`

### delete task from one user's timetable: `deletetask`

Format: `deletetask /on DATE /index TASK_INDEX`

### change the start time or end time of a task: `changetasktiming`

Format:`changetasktiming /on DATE /index TASK_INDEX /from TIME /to TIME`

### change the task type: `changetasktype`

Format:`changetasktype /on DATE /index TASK_INDEX /type F_OR_C`

### add task which happens on multiple days into the timetable: `addrepeaettask`

Format:` addrepeattask /task TASK_NAME /on DATES /from START_TIME /to END_TIME /type F_OR_C`

### Adding a todo: `todo`
Adds a new item to the list of todo items.

Format: `todo n/TODO_NAME d/DEADLINE`

* The `DEADLINE` can be in a natural language format.
* The `TODO_NAME` cannot contain punctuation.  

Example of usage: 

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
