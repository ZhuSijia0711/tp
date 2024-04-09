package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.InvalidDayException;
import seedu.duke.exceptions.InvalidFormatException;
import seedu.duke.exceptions.InvalidUserException;
import seedu.duke.exceptions.NoUserException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    UserList userlist = new UserList();

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void adduserCommandTest() throws InvalidDayException, InvalidFormatException, InvalidUserException,
            NoUserException, IOException {
        Parser.parseCommand("addUser User1", userlist);

        assertEquals(1, userlist.getListLength());
        assertEquals("User1", userlist.getActiveUser().getName());

        Parser.parseCommand("addUser User2", userlist);
        Parser.parseCommand("addUser User3", userlist);
        Parser.parseCommand("addUser User4", userlist);

        assertEquals(4, userlist.getListLength());
    }

    @Test
    public void invalidAdduserCommandTest() {
        try {
            Parser.parseCommand("addUser", userlist);
            fail();
        } catch (Exception e) {
            assertEquals("[ERROR] Invalid addUser format. Expected format: adduser <desired user's name>",
                    e.getMessage());
        }
    }

    @Test
    public void compareCommandTest() {
        User user1 = new User("User1");
        User user2 = new User("User2");
        userlist.addUser(user1);
        userlist.addUser(user2);

        try {
            Parser.parseCommand("compare User1 User1", userlist);
            Parser.parseCommand("compare user1 user1", userlist);
            Parser.parseCommand("compare User1 User2", userlist);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void switchCommandTest() throws InvalidDayException, InvalidFormatException, InvalidUserException,
            NoUserException, IOException {
        User user1 = new User("User1");
        User user2 = new User("User2");
        userlist.addUser(user1);
        userlist.addUser(user2);
        Parser.parseCommand("switch User2", userlist);

        assertEquals(2, userlist.getListLength());
        assertEquals("User2", userlist.getActiveUser().getName());
    }

    @Test
    public void invalidSwitchCommandTest() {
        User user1 = new User("User1");
        User user2 = new User("User2");
        userlist.addUser(user1);
        userlist.addUser(user2);

        try {
            Parser.parseCommand("switch", userlist);
            fail();
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid switch format. Expected format: switch <desired user's name>",
                    e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseCommand("switch noUser", userlist);
            fail();
        } catch (InvalidUserException e) {
            assertEquals("User does not exist!", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addTaskCommandTest() throws InvalidDayException, InvalidUserException, InvalidFormatException,
            NoUserException, IOException {

        //
        Storage.setFolderPath("test_data");
        User.setFolderPath("test_data");

        User user1 = new User("User1");
        userlist.addUser(user1);

        Parser.parseCommand("addtask /on Monday /task test1 /from 9:00 /to 11:00 /type f", userlist);
        Parser.parseCommand("addtask /on Monday /task test2 /from 13:00 /to 15:00 /type f", userlist);
        ArrayList<Task> testTasksMon = user1.getTimetable().getWeeklyTasks().get("Monday");
        ArrayList<Task> testTasksTue = user1.getTimetable().getWeeklyTasks().get("Tuesday");

        assertEquals("test1", testTasksMon.get(0).getDescription());
        assertEquals("09:00", testTasksMon.get(0).getStartTime().toString());
        assertEquals("11:00", testTasksMon.get(0).getEndTime().toString());

        assertEquals("test2", testTasksMon.get(1).getDescription());
        assertEquals("13:00", testTasksMon.get(1).getStartTime().toString());
        assertEquals("15:00", testTasksMon.get(1).getEndTime().toString());

        try {
            String testDescription = testTasksTue.get(1).getDescription();
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index 1 out of bounds for length 0", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void invalidAddTaskCommandTest() {
        User user1 = new User("User1");
        userlist.addUser(user1);

        String expectedErrorMessage = "[ERROR] Invalid addtask format. Expected format: addtask /on [day]" +
                " /task [description] /from [start time] /to [end time] /type [f/c]";
        String[] testMessages = {"addtask",
                "addtask /on Monday",
                "addtask /on Monday /task description",
                "addtask /on Monday /task description /from 09:00 /to 11:00",
                "addtask Monday description 09:00 11:00",
                "addtask /on MONDAY /task description /from 09:00 /to 11:00"};
        for (String message : testMessages) {
            try {
                Parser.parseCommand(message, userlist);
                fail();
            } catch (Exception e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
        }

        try {
            Parser.parseCommand("addtask /on Oneday /task lecture /from 9:00 /to 11:00 /type f", userlist);
        } catch (Exception e) {
            assertEquals("[ERROR] Invalid day. Please enter a day from Monday - Sunday.", e.getMessage());
        }

        try {
            Parser.parseCommand("addtask /on Monday /task lecture /from 9:00 /to 25:00 /type f", userlist);
        } catch (Exception e) {
            assertEquals("Text '25:00' could not be parsed: Invalid value for HourOfDay" +
                    " (valid values 0 - 23): 25", e.getMessage());
        }
    }

    @Test
    public void addForAllTest() throws InvalidDayException, InvalidUserException, InvalidFormatException,
            NoUserException, IOException {
        User user1 = new User("User1");
        User user2 = new User("User2");
        userlist.addUser(user1);
        userlist.addUser(user2);

        Parser.parseCommand("addforall /on Monday /task lecture /from 9:00 /to 11:00", userlist);

        Task addedTaskUser1 = user1.getTimetable().getWeeklyTasks().get("Monday").get(0);
        Task addedTaskUser2 = user1.getTimetable().getWeeklyTasks().get("Monday").get(0);

        assertEquals("lecture", addedTaskUser1.getDescription());
        assertEquals("lecture", addedTaskUser2.getDescription());
        assertEquals("09:00", addedTaskUser2.getStartTime().toString());
        assertEquals("11:00", addedTaskUser2.getEndTime().toString());
    }

    @Test
    public void changeTaskTypeTest() throws InvalidDayException, NoUserException, IOException,
            InvalidUserException, InvalidFormatException {
        User user1 = new User("User1");
        userlist.addUser(user1);
        Parser.parseCommand("addtask /on Monday /task test1 /from 9:00 /to 11:00 /type f", userlist);
        Task task = user1.getTimetable().getWeeklyTasks().get("Monday").get(0);
        assertEquals("f", task.getType());

        Parser.parseCommand("changetasktype /on Monday /index 1 /type c", userlist);
        assertEquals("c", task.getType());

        Parser.parseCommand("changetasktype /on Monday /index 1 /type c", userlist);
        assertEquals("c", task.getType());
    }

    @Test
    public void invalidChangeTaskTypeTest() throws InvalidDayException, NoUserException, IOException,
            InvalidUserException, InvalidFormatException {
        User user1 = new User("User1");
        userlist.addUser(user1);
        Parser.parseCommand("addtask /on Monday /task test1 /from 9:00 /to 11:00 /type f", userlist);
        Task task = user1.getTimetable().getWeeklyTasks().get("Monday").get(0);
        assertEquals("f", task.getType());

        try {
            Parser.parseCommand("changetasktype /on Monday /index 2 /type c", userlist);
            fail();
        } catch (InvalidFormatException e) {
            assertEquals("The selected task does not exist. ", e.getMessage());
        } catch (IOException e) {
            fail();
        }
    }
}
