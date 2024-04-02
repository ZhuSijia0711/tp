package seedu.duke;

import seedu.duke.exceptions.InvalidDayException;
import seedu.duke.exceptions.InvalidFormatException;
import seedu.duke.exceptions.InvalidUserException;
import seedu.duke.exceptions.NoUserException;
import seedu.duke.ui.UI;

import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    protected static final String[] DAYS = new String[]
        {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

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
            String userName = parts[1];
            User newUser = new User(userName);
            UI.printNewUser(newUser.getName());
            userList.addUser(newUser);
            newUser.getStorage().addUserInFolder();
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
            addTask(command, userList);
            User currentUser = userList.getActiveUser();
            currentUser.getStorage().writeTaskInFile(currentUser);
        } else if  (command.toLowerCase().startsWith("addtwdc")) {
            addTaskWithDuplicationCheck(command, userList);
        }else if (command.toLowerCase().startsWith("deletetask")) {
            deleteTask(command, userList);
        } else if (command.toLowerCase().startsWith("changetasktiming")) {
            changeTaskTiming(command, userList);
        } else if (command.toLowerCase().startsWith("addrepeattask")) {
            addRepeatTask(command, userList);
        } else if (command.toLowerCase().startsWith("changetasktype")) {
            changeTaskType(command, userList);
        } else if (command.toLowerCase().startsWith("compareall")) {
            UI.printComparingAll();
            UI.printSharedTime(Timetable.compareAllTimetables(userList));
        } else if (command.toLowerCase().startsWith("compare")) {
            InputValidator.validateCompareInput(command);
            String[] parts = command.split("\\s+");
            String user1 = parts[1];
            String user2 = parts[2];
            InputValidator.validateUserInput(user1, userList);
            InputValidator.validateUserInput(user2, userList);
            UI.printSharedTime(Timetable.compareTimetable(userList.findUser(user1).getTimetable(),
                    userList.findUser(user2).getTimetable()));
        } else if (command.toLowerCase().startsWith("addforall")) {
            addTaskForAll(command, userList);
        } else if (command.toLowerCase().startsWith("viewcommonevents")) {
            printConfirmedEvent(userList);
        } else {
            UI.printInvalidCommand();
        }
    }


    private static void changeTaskType(String command, UserList userList) throws InvalidFormatException {
        try {
            InputValidator.validateChangeTaskType(command);
            String[] parts = command.split("\\s+");
            List<String> wordList = Arrays.asList(parts);
            String day = wordList.get(2);
            int index = Integer.parseInt(wordList.get(wordList.indexOf("/index") + 1));
            String newType = wordList.get(wordList.indexOf("/type") + 1);
            InputValidator.validateDay(day);

            User currentUser = userList.getActiveUser();
            currentUser.getTimetable().changeTaskType(day, index - 1, newType);
            System.out.println("Task type changed successfully.");
            currentUser.getStorage().writeTaskInFile(currentUser);
        } catch (InvalidDayException | IndexOutOfBoundsException | NumberFormatException | IOException e) {
            throw new RuntimeException(e);
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
    /**
     * Adds a task to the timetable with duplication check.
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
            userList.getActiveUser().getTimetable().addUserTask(task.day, task);
            UI.printAddTask(task);
        } catch (InvalidFormatException | InvalidDayException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Task parseTask(String command) throws InvalidDayException {
        String[] parts = command.split("\\s+");
        List<String> wordList = Arrays.asList(parts);
        String day = parts[2];
        String description = parseDescription(wordList);
        String startTime = parts[wordList.indexOf("/from") + 1];
        String endTime = parts[wordList.indexOf("/to") + 1];

        String type;
        if (wordList.contains("/type")) {
            type = parts[wordList.indexOf("/type") + 1];
        } else {
            type = "common";
        }

        InputValidator.validateDay(day);
        return new Task(description, day, startTime, endTime, type);
    }

    private static void changeTaskTiming(String command, UserList userList) throws InvalidFormatException {
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
        } catch (InvalidDayException | IOException e) {
            throw new RuntimeException(e);
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
            String description = wordlist.get(taskIndex + 1);
            int daysIndex = wordlist.indexOf("/on") + 1;
            int endDaysIndex = wordlist.indexOf("/from");
            String[] days = Arrays.copyOfRange(parts, daysIndex, endDaysIndex);
            if (days.length < 2) {
                throw new InvalidFormatException("Please enter at least 2 days, or you want to use addtask command!");
            }
            String startTime = parts[wordlist.indexOf("/from") + 1];
            String endTime = parts[wordlist.indexOf("/to") + 1];
            String type = parts[wordlist.indexOf("/type") + 1];
            User currentUser = userList.getActiveUser();
            for (String day : days) {
                Task task = new Task(description, day, startTime, endTime, type);
                currentUser.getTimetable().addUserTask(day, task);
            }
            currentUser.getStorage().writeTaskInFile(currentUser);
            System.out.println("Repeated task added successfully!");
        } catch (InvalidFormatException e) {
            System.out.println("Please enter at least 2 days, or you want to use addtask command!");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
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
            throws InvalidFormatException, InvalidDayException, IOException {
        InputValidator.validateAddTaskForAll(command);
        Task task = parseTask(command);
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
                }
            }
        }
    }

    private static void printNextTask(User currentUser) {
        LocalTime currentTime = LocalTime.now();
        String dayOfWeek = DayOfWeek.from(LocalDate.now()).toString().toLowerCase();
        String capitalizedDay = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
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
                    System.out.println(nextTask.toString());
                }
                return;
            }
        }
    }
}
