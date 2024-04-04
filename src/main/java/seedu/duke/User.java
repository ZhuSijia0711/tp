package seedu.duke;

import seedu.duke.ui.UI;

public class User {
    private Timetable timetable;
    private final String name;

    private static String folderPath = "data";
    private Storage storage;

    public User(String name) {
        this.name = name;
        this.timetable = new Timetable();
        this.storage = new Storage(folderPath + "/" + name + ".txt");
    }

    public static void setFolderPath(String newPath) {
        User.folderPath = newPath;
    }

    public Storage getStorage() {
        return storage;
    }

    public String getName() {
        return name;
    }

    public void viewTimetable() {
        for (String day : Timetable.DAYS) {
            UI.printTasksOfTheDay(day, timetable.getWeeklyTasks());
        }
    }

    public Timetable getTimetable() {
        return timetable;
    }
}
