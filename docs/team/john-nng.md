
# John Nguyen Project Portfolio Page</h1>

## Project: Task Scheduler and Calendar Comparison App
### Team: AY2324S2-CS2113-T13-2
[Code Contribution](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=john-nng&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

This is a task scheduler and timetable comparison command line app that allows users to create timetables consisting of task throughout a given week. Multiple users have their own timetable compare timetables to see shared time between users, seeing upcoming task and viewing their timetables for the week. This makes it easy for people to schedule events in relation to each other based on their shared free time.

### Contributions

#### Features
- **Structured the Timetable Class datastructure.** I adjusted the parameters of the Timetable Class to conventiently do calculations with other tables, for example, the data structure of the timetable is a HashMap of days associated with list of tasks which are the values for the dictionary keys.
- **Implemented InputValidator Class and set up the framework for this class.** This class is used to validate the correct syntax for every command in this app. This uses regex to make sure the String pattern of a command correct matches how the comand should be called. I setup the logic for this InputValidator class and my functions I created could be referenced for future additions of new commands.
- **Implemented Compare function between 2 users.** I added the essential feature of comparing two timetables of two users and displaying their shared free time bassed on each day. This is done by merging the two timetables then finding the free time within the merged timetables then displaying the results.
- **Abstracted the Main Class.** I abstracted the Parsing logic that was originally in the Main class, this significantly decluttered the main Class and abstracted the logic into the Parser Class. The while loop simply takes in Input and then Parsing will take care of the bulk of the work.
- **Implemented the Parser Class.** I set up the framework for the Parser class which takes in a string command and will identify that command it is. Based on that It will perform the command logic, abstracting other parts of the code if neccesary.
- example of InputValidator within Parser Class:
- <img width="443" alt="image" src="https://github.com/john-nng/tp/assets/89668122/62da4f66-381c-484c-9f46-f8f0e3fca3f5">


#### Testing
- Wrote test for InputValidator class. I wrote J-Unit test for InputValidator class to ensure that the functions were correctly validating various input commands.
- Wrote some test in the TimetableTest Class for addtask function and compare functions to see if various task would be added properly and compared properly.

#### Enhancements to Current Features
- Enchanced the UI ouput for timetables and timetable comparison commands. Making these outputs more readable.
- Updated the help message UI output. To reflect the complete set of commands and their usages in a clean format.

#### Documentation
- Updated Documentation for all commands in User Guide and formatted them in a easy to read manner. Bulk of work done during these PR's ([1](https://github.com/AY2324S2-CS2113-T13-2/tp/blob/9dbe139124adfecf78f4c94d8f662b283788a8fa/docs/UserGuide.md), [2](https://github.com/AY2324S2-CS2113-T13-2/tp/blob/65c01a9d7d4875eedc494afa923108eec9e48055/docs/UserGuide.md), [3](https://github.com/AY2324S2-CS2113-T13-2/tp/blob/47db8ea97ed3da106a83d75a7ae90152a5e80200/docs/UserGuide.md))
