package seedu.duke;

import seedu.duke.exceptions.InvalidDayException;
import seedu.duke.exceptions.InvalidFormatException;
import seedu.duke.exceptions.InvalidUserException;
import seedu.duke.exceptions.NoUserException;
import seedu.duke.ui.UI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Parser {
    public static final String[] DAYS = new String[]
        {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static final int COMMAND_INDEX_DAY = 2;
    private static final int USERS_INDEX = 5;
    private static final int DAY_INDEX = 3;
    private static final int DESCRIPTION_INDEX = 5;
    private static final int START_INDEX = 5;
    private static final int END_INDEX = 3;
    private static final int TYPE_INDEX = 5;
    private static final int DAY_INDEX_IN_ADD_FOR_ALL = 2;
    private static final int USER_PART = 1;
    private static final int DAY_PART = 2;
    private static final int DESCRIPTION_PART = 3;
    private static final int START_PART = 4;
    private static final int END_PART = 5;
    private static final int TYPE_PART = 6;

    public static String capitalizeFirstLetter(String input) {
        String lowerCase = input.toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }

    /**
     * Parses User Input and Identifies the command used.
     *
     * @param command The users text input.
     */
    public static void parseCommand(String command, UserList userList) throws
            InvalidFormatException, InvalidDayException, InvalidUserException, NoUserException, IOException {
        if (command.equalsIgnoreCase("list")) {
            UI.printListingUsers();
            userList.listAll();
        } else if (command.equalsIgnoreCase("help")) {
            UI.printHelp();
        } else if (command.equalsIgnoreCase("bye")) {
            UI.printBye();
            Main.setIsFinished(true);
        } else if (command.equalsIgnoreCase("current")) {
            UI.printActiveUser(userList.getActiveUser().getName());
        } else if (command.equalsIgnoreCase("view")) {
            userList.getActiveUser().viewTimetable();
        } else if (command.equalsIgnoreCase("next")) {
            printNextTask(userList.getActiveUser());
        } else if (command.toLowerCase().startsWith("adduser")) {
            InputValidator.validateAddUserInput(command);
            String[] parts = command.split("\\s+");
            String userName = Parser.capitalizeFirstLetter(parts[1]);
            if (userList.containsUser(userName)) {
                throw new InvalidUserException("User already exists. Use a different name. ");
            } else {
                User newUser = new User(userName);
                UI.printNewUser(newUser.getName());
                userList.addUser(newUser);
                newUser.getStorage().addUserInFolder();
            }
        } else if (command.toLowerCase().startsWith("switch")) {
            InputValidator.validateSwitchInput(command);
            String[] parts = command.split("\\s+");
            String userName = parts[1];
            userList.setActiveUser(userList.findUser(userName));
            UI.printActiveUser(userList.getActiveUser().getName());
        } else if (command.toLowerCase().startsWith("addtask")) {
            if (userList.getUsers().isEmpty()) {
                throw new NoUserException();
            }
            Task task = parseTask(command);
            User currentUser = userList.getActiveUser();
            if (checkClash(task, currentUser)) {
                UI.printClashTasks();
                return;
            }
            addTask(command, userList);
            currentUser.getStorage().writeTaskInFile(currentUser);
        } else if (command.toLowerCase().startsWith("addtwdc")) {
            addTaskWithDuplicationCheck(command, userList);
        } else if (command.toLowerCase().startsWith("deletetask")) {
            deleteTask(command, userList);
        } else if (command.toLowerCase().startsWith("changetasktiming")) {
            changeTaskTiming(command, userList);
        } else if (command.toLowerCase().startsWith("addrepeattask")) {
            if (userList.getUsers().isEmpty()) {
                throw new NoUserException();
            }
            addRepeatTask(command, userList);
        } else if (command.toLowerCase().startsWith("changetasktype")) {
            if (userList.getUsers().isEmpty()) {
                throw new NoUserException();
            }
            changeTaskType(command, userList);
        } else if (command.toLowerCase().startsWith("todaytask")) {
            if (userList.getUsers().isEmpty()) {
                throw new NoUserException();
            }
            todayTask(command,userList);
        } else if (command.toLowerCase().startsWith("compareall")) {
            InputValidator.validateCompareAllInput(command);
            UI.printComparingAll();
            UI.printSharedTime(Timetable.compareAllTimetables(userList));
        } else if (command.toLowerCase().startsWith("compare")) {
            InputValidator.validateCompareInput(command);
            String[] parts = command.split("\\s+");
            String user1 = capitalizeFirstLetter(parts[1]);
            String user2 = capitalizeFirstLetter(parts[2]);
            InputValidator.validateUserInput(user1, userList);
            InputValidator.validateUserInput(user2, userList);
            UI.printCompareUsers(user1, user2);
            UI.printSharedTime(Timetable.compareTimetable(userList.findUser(user1).getTimetable(),
                    userList.findUser(user2).getTimetable()));
        } else if (command.toLowerCase().startsWith("addforall")) {
            InputValidator.validateAddTaskForAll(command);
            addTaskForAll(command, userList);
        } else if (command.toLowerCase().startsWith("addfor")) {
            InputValidator.validAddFor(command);
            addFor(command, userList);
        } else if (command.toLowerCase().startsWith("viewcommonevents")) {
            printConfirmedEvent(userList);
        } else if (command.toLowerCase().startsWith("urgent /in")) {
            if (userList.getUsers().isEmpty()) {
                throw new NoUserException();
            }
            printUrgentTasks(command, userList);
        } else {
            UI.printInvalidCommand();
        }
    }

    private static void printUrgentTasks(String command, UserList userList) {
        try {
            String[] parts = command.split("\\s+");
            int hours = Integer.parseInt(parts[2]);

            LocalDateTime now = LocalDateTime.now();
            int currentHour = now.getHour();
            if(currentHour + hours >= 24){
                System.out.println("Hour input exceeds current day.");
            }
            LocalDateTime deadline = now.plusHours(hours);
            List<Task> urgentTasks = findUrgentTasks(userList.getActiveUser(), now, deadline);
            if (urgentTasks.isEmpty()) {
                System.out.println("No urgent tasks within the specified timeframe.");
            } else {
                if(currentHour + hours >= 24){
                    System.out.println("Urgent tasks until end of today: ");
                    for (Task task : urgentTasks) {
                        System.out.println(task);
                    }
                } else {
                    System.out.println("Urgent tasks within the next " + hours + " hours:");
                    for (Task task : urgentTasks) {
                        System.out.println(task);
                    }
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Invalid command format. Please use: urgent /in [hours]");
        }
    }

    private static List<Task> findUrgentTasks(User user, LocalDateTime now, LocalDateTime deadline) {
        List<Task> urgentTasks = new ArrayList<>();
        Timetable userTimetable = user.getTimetable();
        String currentDayOfWeek = now.getDayOfWeek().toString().toLowerCase(); // Get current day of the week
        String capitalisedDay = Parser.capitalizeFirstLetter(currentDayOfWeek);
        ArrayList<Task> tasks = userTimetable.getWeeklyTasks().get(capitalisedDay);
        if (tasks != null) {
            for (Task task : tasks) {
                LocalDateTime taskStartTime = LocalDateTime.of(now.toLocalDate(), task.getStartTime());
                if (now.isBefore(taskStartTime) && taskStartTime.isBefore(deadline)) {
                    urgentTasks.add(task);
                }
            }
        }
        return urgentTasks;
    }



    private static void changeTaskType(String command, UserList userList) throws InvalidFormatException {
        try {
            InputValidator.validateChangeTaskType(command);
            String[] parts = command.split("\\s+");
            List<String> wordList = Arrays.asList(parts);
            String day = wordList.get(COMMAND_INDEX_DAY);
            int index = Integer.parseInt(wordList.get(wordList.indexOf("/index") + 1));
            String newType = wordList.get(wordList.indexOf("/type") + 1);
            InputValidator.validateDay(day);

            User currentUser = userList.getActiveUser();
            currentUser.getTimetable().changeTaskType(day, index - 1, newType);
            System.out.println("Task type changed successfully.");
            currentUser.getStorage().writeTaskInFile(currentUser);
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidFormatException("The selected task does not exist. ");
        } catch (InvalidDayException e) {
            throw new InvalidFormatException("[ERROR] Invalid day. Please enter a day from Monday - Sunday. ");
        }
    }

    private static void deleteTask(String command, UserList userList) {
        try {
            InputValidator.validateDeleteTaskInput(command);
            String[] parts = command.split("\\s+");
            String day = parts[2];
            int index = Integer.parseInt(parts[4]) - 1;
            InputValidator.validateDay(day);
            User currentUser = userList.getActiveUser();
            currentUser.getTimetable().deleteUserTask(day, index);
            currentUser.getStorage().writeTaskInFile(currentUser);
        } catch (InvalidFormatException | InvalidDayException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Adds a task to the timetable with task duplication detection.
     *
     * @param command  The user input command.
     * @param userList The list of users.
     */
    private static void addTaskWithDuplicationCheck(String command, UserList userList) {
        try {
            InputValidator.validateAddTaskWDCInput(command);
            Task task = parseTask(command);
            boolean addedSuccessfully = userList.getActiveUser().getTimetable()
                    .addUserTaskWithDuplicationCheck(task.day, task);

            if (addedSuccessfully) {
                UI.printAddTask(task);
            } else {
                System.out.println("Task already exists. Cannot add duplicate task.");
            }
        } catch (InvalidFormatException | InvalidDayException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addTask(String command, UserList userList) {
        try {
            InputValidator.validateAddTaskInput(command);
            Task task = parseTask(command);
            User currentuser = userList.getActiveUser();
            currentuser.getTimetable().addUserTask(task.day, task);
            UI.printAddTask(task);

        } catch (InvalidFormatException | InvalidDayException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkClash(Task taskToBeAdded, User user) {
        Timetable timetable = user.getTimetable();
        String day = taskToBeAdded.day;
        String capitalisedDay = Parser.capitalizeFirstLetter(day);
        timetable.getWeeklyTasks().get(capitalisedDay).sort(Comparator.comparing(Task::getStartTime));
        for (Task task : timetable.getWeeklyTasks().get(capitalisedDay)) {
            if (taskToBeAdded.startTime.isAfter(task.getStartTime()) &&
                taskToBeAdded.startTime.isBefore(task.getEndTime())
                || (taskToBeAdded.endTime.isAfter(task.getStartTime()) &&
                taskToBeAdded.endTime.isBefore(task.getEndTime()))
                || (taskToBeAdded.startTime.equals(task.getStartTime()))
                || (taskToBeAdded.endTime.equals(task.getEndTime()))
                || (taskToBeAdded.startTime.isBefore(task.getStartTime()) &&
                taskToBeAdded.endTime.isAfter(task.getEndTime()))) {
                return true;
            }
        }
        return false;
    }

    public static Task parseTask(String command) throws InvalidDayException, InvalidFormatException {
        InputValidator.validateAddTaskInput(command);
        String[] parts = command.split("\\s+");
        List<String> wordList = Arrays.asList(parts);
        String day = Parser.capitalizeFirstLetter(parts[2]);
        String description = parseDescription(wordList);
        String startTime = parts[wordList.indexOf("/from") + 1];
        String endTime = parts[wordList.indexOf("/to") + 1];
        String type = parts[wordList.indexOf("/type") + 1];
        InputValidator.validateDay(day);
        return new Task(description, day, startTime, endTime, type);
    }

    public static Task parseAddForAllTask(String command) throws InvalidDayException {
        String[] parts = command.split("\\s+");
        List<String> wordList = Arrays.asList(parts);
        String day = Parser.capitalizeFirstLetter(parts[DAY_INDEX_IN_ADD_FOR_ALL]);
        String description = parseDescription(wordList);
        String startTime = parts[wordList.indexOf("/from") + 1];
        String endTime = parts[wordList.indexOf("/to") + 1];

        String type = "common";
        InputValidator.validateDay(day);
        return new Task(description, day, startTime, endTime, type);
    }

    /**
     * Prints tasks for today.
     *
     * @param userList The list of users.
     */
    public static void todayTask(String command, UserList userList) {
        String[] parts = command.trim().split("\\s+");
        if(parts.length > 1){
            System.out.println("Please simply use 'todaytask' to view today's task" +
                    " without additional text!");
            return;
        }
        String dayOfWeek = DayOfWeek.from(LocalDate.now()).toString();
        String capitalizedDay = Parser.capitalizeFirstLetter(dayOfWeek);
        ArrayList<Task> tasksForToday = userList.getActiveUser().getTimetable().getWeeklyTasks().get(capitalizedDay);
        if (tasksForToday.isEmpty()) {
            UI.printNoTask("today");
            return;
        }
        UI.printLine();
        UI.printDayHeader("Today");
        int count = 1;
        for (Task task : tasksForToday) {
            UI.printTaskInList(count, task.toString());
            count++;
        }
    }

    private static void changeTaskTiming(String command, UserList userList) throws
            InvalidFormatException, InvalidDayException {
        try {
            InputValidator.validateChangeTaskTiming(command);
            String[] parts = command.split("\\s+");
            List<String> wordList = Arrays.asList(parts);
            String day = parts[2];
            int index = Integer.parseInt(parts[wordList.indexOf("/index") + 1]);
            LocalTime newStartTime = LocalTime.parse(parts[wordList.indexOf("/from") + 1]);
            LocalTime newEndTime = LocalTime.parse(parts[wordList.indexOf("/to") + 1]);
            InputValidator.validateDay(day);

            User currentUser = userList.getActiveUser();
            currentUser.getTimetable().changeFlexibleTaskTiming(day,
                    index - 1, newStartTime, newEndTime);
            currentUser.getStorage().writeTaskInFile(currentUser);
            System.out.println("Flexible task timing changed successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidDayException e) {
            throw new InvalidDayException("[ERROR] Invalid day. Please enter a day from Monday - Sunday.");
        }
    }

    private static void addRepeatTask(String command, UserList userList) {
        try {
            InputValidator.validateAddRepeatTask(command);
            String[] parts = command.split("\\s+");
            List<String> wordlist = Arrays.asList(parts);
            int taskIndex = wordlist.indexOf("/task");
            if (taskIndex == -1 || taskIndex + 1 >= wordlist.size()) {
                throw new InvalidFormatException(("Please enter a task name!"));
            }
            String description = command.substring(command.indexOf("/task") + "/task".length(),
                    command.indexOf("/on")).trim();
            int daysIndex = wordlist.indexOf("/on") + 1;
            int endDaysIndex = wordlist.indexOf("/from");
            String[] days = Arrays.copyOfRange(parts, daysIndex, endDaysIndex);
            if (days.length < 2) {
                throw new InvalidDayException("Please enter at least 2 days, or you want to use addtask command!");
            }
            String startTime = parts[wordlist.indexOf("/from") + 1];
            String endTime = parts[wordlist.indexOf("/to") + 1];
            String type = parts[wordlist.indexOf("/type") + 1];
            User currentUser = userList.getActiveUser();
            for (String day : days) {
                String capitalizedDay = Parser.capitalizeFirstLetter(day);
                Task task = new Task(description, capitalizedDay, startTime, endTime, type);
                currentUser.getTimetable().addUserTask(capitalizedDay, task);
            }
            currentUser.getStorage().writeTaskInFile(currentUser);
            System.out.println("Repeated task added successfully!");
        } catch (InvalidDayException e) {
            System.out.println("Invalid day input: " + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("[ERROR] Invalid addRepeatTask format.\n" +
                    "Expected format: addRepeatTask /task [description] /on [day(s)] /from [start time]" +
                    "/to [end time] /type [f/c]");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static String parseDescription(List<String> words) {
        int startIndex = words.indexOf("/task") + 1;
        int endIndex = words.indexOf("/from") - 1;
        StringBuilder description = new StringBuilder();
        for (int i = startIndex; i <= endIndex; i++) {
            description.append(words.get(i));
            if (i < endIndex) {
                description.append(" ");
            }
        }
        return description.toString();
    }


    private static void addTaskForAll(String command, UserList userList)
            throws InvalidDayException, IOException {
        //InputValidator.validateAddTaskForAll(command);
        Task task = parseAddForAllTask(command);
        assert !userList.getUsers().isEmpty() : "There is no user added.";
        for (User user : userList.getUsers()) {
            user.getTimetable().addUserTask(task.day, task);
            user.getStorage().writeTaskInFile(user);
        }
        UI.printAddForAll(task);
    }

    private static void printConfirmedEvent(UserList userList) {
        assert !userList.getUsers().isEmpty() : "There is no user added.";
        int taskCount = 1;
        for (String day : DAYS) {
            for (Task task : userList.getActiveUser().getTimetable().getWeeklyTasks().get(day)) {
                if (task.type.equals("common")) {
                    System.out.println(taskCount + ". " + task);
                    taskCount++;
                }
            }
        }
        if (taskCount == 1) {
            System.out.println("There is no common events.");
        }
    }

    private static void printNextTask(User currentUser) {
        LocalTime currentTime = LocalTime.now();
        String dayOfWeek = DayOfWeek.from(LocalDate.now()).toString();
        String capitalizedDay = Parser.capitalizeFirstLetter(dayOfWeek);
        Task current = new Task("temp", dayOfWeek, currentTime.toString(), currentTime.toString(), "f");

        ArrayList<Task> tasksOfDay = currentUser.getTimetable().getWeeklyTasks().get(capitalizedDay);
        int numOfTasks = tasksOfDay.size();

        if (numOfTasks == 0) {
            UI.printNoTasks();
            return;
        }
        Task nextTask = null;
        for (int i = numOfTasks - 1; i >= 0; i -= 1) {
            if (current.getStartTime().isBefore(tasksOfDay.get(i).getStartTime())) {
                nextTask = tasksOfDay.get(i);
            } else {
                if (nextTask == null) {
                    UI.printNoTasks();
                } else {
                    UI.printNext();
                    System.out.println(nextTask);
                }
                return;
            }
        }
    }

    private static void addFor(String command, UserList userList) throws IOException, InvalidUserException {
        String[] words = command.split("/");
        String users = words[USER_PART].substring(USERS_INDEX).trim(); // to exclude words "/user"
        String day = words[DAY_PART].substring(DAY_INDEX).trim(); // to exclude words "/on"
        String description = words[DESCRIPTION_PART].substring(DESCRIPTION_INDEX).trim(); // to exclude words "/task"
        String start = words[START_PART].substring(START_INDEX).trim(); // to exclude words "/from"
        String end = words[END_PART].substring(END_INDEX).trim(); // to exclude words "/to"
        String type = words[TYPE_PART].substring(TYPE_INDEX).trim(); // to exclude words "/type"
        String[] usernames = users.split(",");

        Task task = new Task(description, day, start, end, type);
        ArrayList<String> clashedUsers = new ArrayList<>();
        ArrayList<String> invalidUsers = new ArrayList<>();
        for (String username : usernames) {
            User user = userList.findUser(username.trim());
            if (user == null) {
                if (!invalidUsers.contains(username.trim())) {
                    invalidUsers.add(username.trim());
                }
                continue;
            }
            if (checkClash(task, user)) {
                if (!clashedUsers.contains(username.trim())) {
                    clashedUsers.add(username.trim());
                }
            }
        }

        if (!invalidUsers.isEmpty() || !clashedUsers.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            if (!invalidUsers.isEmpty()) {
                errorMessage.append("Invalid users: ").append(String.join(", ", invalidUsers)).append(". ");
            }
            if (!clashedUsers.isEmpty()) {
                errorMessage.append("Users with task clashes: ").append(String.join(", ", clashedUsers)).append(". ");
            }
            throw new InvalidUserException(errorMessage.toString());
        }


        for (String username : usernames) {
            User user = userList.findUser(username.trim());
            if (!checkClash(task, user)) {
                user.getTimetable().addUserTask(day, task);
                user.getStorage().writeTaskInFile(user);
            } else {
                System.out.println("For " + user.getName()
                    + ", the task " + task + " clashes with existing tasks OR already exist!");
            }
        }
    }
}
