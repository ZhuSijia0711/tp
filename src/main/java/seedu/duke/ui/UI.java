package seedu.duke.ui;

import seedu.duke.Task;
import seedu.duke.Timetable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import static seedu.duke.Timetable.findOverlappingFreeTime;

public class UI {
    public static void printGreeting() {
        System.out.println("Timetable comparison app opened. ");
    }

    public static void printBye() {
        System.out.println("Bye. ");
    }

    public static void printListingUsers() {
        System.out.println("The current users are: ");
    }

    public static void printNewUser(String name) {
        System.out.println("New user added: " + name);
    }

    public static void printActiveUser(String description) {
        System.out.println("The active user is: " + description);
    }

    public static void printInvalidCommand() {
        System.out.println("Invalid command. ");
    }

    public static void printAddTask(Task task) {
        System.out.println("The following task is added: " + task);
    }

    public static void printHelp() {
        System.out.println(
                "Note: use hh:mm 24hr time format (ex. 13:00) \n" +
                "List of available commands: " + UI.line() +
                "View list of commands (what you are looking at): \n" + "help" + UI.line() +
                "List all users: \n" + "list" + UI.line() +
                "Exit the app: \n" + "bye" + UI.line() +
                "View current user: \n" + "current" + UI.line() +
                "View timetable of current user: \n" + "view" + UI.line() +
                "View your next task: \n" + "next" + UI.line() +
                "Add new user: \n" + "adduser <NAME>" + UI.line() +
                "Switch to user: \n" + "switch <USERNAME>" + UI.line() +
                "Add task for current user:\n" +
                    "addtask /on <DAY> /task <DESCRIPTION> /from <START_TIME> /to <END_TIME> /type <f or c>"
                        + UI.line() +
                "Add task for current user (check duplicates):\n" +
                    "addtwdc /on <DAY> /task <DESCRIPTION> /from <START_TIME> /to <END_TIME> /type <f or c>"
                        + UI.line() +
                "Add a task for all users: \n" +
                    "addforall /on <DAY> /task <DESCRIPTION> /from <START_TIME> /to <END_TIME>"
                        + UI.line() +
                "Add a task that repeats over certain days: \n" +
                    "addrepeattask /on <DAY_1 ...> /task <DESCRIPTION> /from <START_TIME> /to <END_TIME> /type <f or c>"
                        + UI.line() +
                "Add a task for certain users: \n" +
                "addfor /user <U_1, ...> /on <DAY> /task <DESCRIPTION> /from <START_TIME> /to <END_TIME> /type <f or c>"
                        + UI.line() +
                "Delete task: \n" + "deletetask" + UI.line() +
                "Change a task's timing: \n" +
                    "changetasktiming /on <DAY> /index <TASK_INDEX> /from <NEW START> /to <NEW END>" + UI.line() +
                "Change a task's type: \n" +
                    "changetasktype /on <DAY> /index <TASK_INDEX> /type <f or c>" + UI.line() +
                "Compare timetables of all users: \n" + "compareall" + UI.line() +
                "Compare timetables between two users: \n" + "compare <USER_1> <USER_2>" + UI.line() +
                "List today's tasks: \n" + "todaytasks" + UI.line() +
                "List urgent tasks within certain timeframe: \n" + "urgent /in <HOURS>" + UI.line() +
                "View common events: \n" + "viewcommonevents");
        printLine();
    }

    public static void printAddForAll(Task task) {
        System.out.println("The following task is added for all users: " + task.toString());
    }

    public static void printEmptyDirectory() {
        System.out.println("Directory is empty.");
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static String line() {
        return ("\n_________________________________________\n");
    }

    /**
     * <p>
     * Prints the overlapping free time for each day in the format:
     * ----------------------
     * Shared Free Time on [day]
     * HH:mm - HH:mm: Overlapping Free Time
     */
    public static void printSharedTime(Timetable merged) {
        for (String day : Timetable.DAYS) {
            UI.printLine();
            System.out.println("Shared free time on " + day + ":");
            findOverlappingFreeTime(merged.getWeeklyTasks().get(day), day);

        }
    }

    public static void printCompareUsers(String user1, String user2) {
        System.out.println("Comparing timetables of " + user1 + " and " + user2 + ": ");
    }

    public static void printComparingAll() {
        System.out.println("Comparing all timetables: ");
    }

    /**
     * Prints tasks of the day specified.
     *
     * @param day day of the week the task is on.
     */
    public static void printTasksOfTheDay(String day, Map<String, ArrayList<Task>> weeklyTask) {
        String capitalizedDay = day.substring(0, 1).toUpperCase() + day.substring(1);
        if (weeklyTask.get(capitalizedDay).isEmpty()) {
            UI.printNoTask(day);
            return;
        }
        System.out.println("_________________________________________");
        UI.printDayHeader(capitalizedDay);
        int count = 1;
        for (Task task : weeklyTask.get(capitalizedDay)) {
            UI.printTaskInList(count, task.toString());
            count++;
        }
    }

    public static void printTimeFrame(LocalTime time1, String time2) {
        System.out.println("    "+time1 + " - " + time2);
    }
    public static void printTimeFrame(LocalTime time1, LocalTime time2) {
        System.out.println("    "+time1 + " - " + time2);
    }
    public static void printFullDay() {
        System.out.println("    None");
    }
    public static void printFreeDay(String day) {
        System.out.println("    ** Whole day is free on " + day);
    }
    public static void printNoTask(String day) {
        System.out.println("No task for " + day + "!");
    }
    public static void printDayHeader(String day) {
        System.out.println(day + " :");
    }
    public static void printTaskInList(int count, String task) {
        System.out.println("    "+count + ". " + task);
    }

    public static void printNext() {
        System.out.println("Your next task is: ");
    }

    public static void printNoTasks() {
        System.out.println("You have no tasks today. ");
    }

    public static void printNoUsers() {
        System.out.println("Please add a user before adding tasks.");
    }

    public static void printClashTasks() {
        System.out.println("The task you want to add clashes with existing tasks. Please check again.");
    }
}
