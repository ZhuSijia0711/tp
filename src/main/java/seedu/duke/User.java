package seedu.duke;

import seedu.duke.ui.UI;

public class User {
    private Timetable timetable;
    private final String name;

    private Storage storage;

    public User(String name) {
        this.name = name;
        this.timetable = new Timetable();
        this.storage = new Storage("data/" + name + ".txt");
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
