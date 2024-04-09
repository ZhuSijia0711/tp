package seedu.duke;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.Parser.DAYS;
import static seedu.duke.Storage.folderPath;
import static seedu.duke.Storage.setFolderPath;
import static seedu.duke.Storage.writeToFile;
import static seedu.duke.Storage.extractTaskInfo;
import static seedu.duke.Storage.BOX_OUTLINE;
import static seedu.duke.Storage.BOX_OUTLINE_FOR_WEDNESDAY;
import static seedu.duke.Storage.BOX_OUTLINE_FOR_FRIDAY;
import static seedu.duke.Storage.LINE_SEPARATOR;


public class StorageTest {

    private static String filePath;

    @BeforeEach
    void setUp() {
        String userName = "testUser";
        filePath = "test_data/" + userName + ".txt";
        setFolderPath("test_data");
        User.setFolderPath("test_data");
        File folder = new File(folderPath);
        boolean folderCreated;
        if (!folder.exists()) {
            folderCreated = folder.mkdirs();
            if (folderCreated) {
                System.out.println("Folder created successfully.");
            } else {
                System.out.println("Failed to create folder.");
            }
        } else {
            System.out.println("Folder already exists.");
        }
    }


    @AfterAll
    static void resetFolderPath() {
        Storage.setFolderPath("data");
        User.setFolderPath("data");
    }

    @Test
    void testStorageInit() {
        User testUser = new User("testUser");
        assertEquals("test_data/testUser.txt", testUser.getStorage().getFilePath());
    }

    @Test
    void testSetFolderPath() {
        Storage.setFolderPath("testPath");
        assertEquals("testPath", Storage.getFolderPath());
    }

    @Test
    void testAddExistingUsers() throws IOException {
        setFolderPath("test_data");
        String userName = "testUser";

        writeToFile(filePath, "Username: " + userName, false);
        UserList userList = new UserList();
        Storage.addExistingUsers(userList);
        assertTrue(userList.containsUser(userName), "UserList should contain the added user");
    }

    @Test
    public void testAddNewUserToFolder() {
        UserList userList = new UserList();
        User newUser = new User("testAddUser");
        userList.addUser(newUser);
        newUser.getStorage().addUserInFolder();

        File directory = new File(folderPath);
        File file = new File(directory, "testAddUser.txt");
        System.out.println(directory.getAbsolutePath());
        assertTrue(file.exists());
    }

    @Test
    public void testWriteToFile() throws Exception {
        User testUser = new User("testUser");
        testUser.getTimetable().addUserTask("Monday", new Task("lecture", "Monday", "10:00", "11:00", "c"));

        testUser.getStorage().writeTaskInFile(testUser);

        Scanner scanner = new Scanner(new File(filePath));
        assertTrue(scanner.hasNextLine(), "Empty file.");
        assertEquals("Username: testUser", scanner.nextLine().trim(), "First line should contain the username");
    }

    @Test
    public void testExtractTaskInfo() {
        String line = "1. 01:00 - 02:00: lecture (type: c)";
        Task task = extractTaskInfo(line, "Monday");
        assertEquals("lecture", task.getDescription());
        assertEquals("01:00", task.getStartTime().toString());
        assertEquals("02:00", task.getEndTime().toString());
        assertEquals("c", task.getType());
    }

    @Test
    public void testLoadData() throws IOException {
        writeToFile(filePath, "Username: " + "testUser" + "\n", false);
        for (String day : DAYS) {
            String outline;
            switch (day) {
            case ("Wednesday"):
                outline = BOX_OUTLINE_FOR_WEDNESDAY;
                break;
            case ("Friday"):
                outline = BOX_OUTLINE_FOR_FRIDAY;
                break;
            default:
                outline = BOX_OUTLINE;
                break;
            }
            writeToFile(filePath, outline, true);
            writeToFile(filePath, "| " + day + " |" + "\n", true);
            writeToFile(filePath, outline, true);

            if (day.equals("Monday")) {
                writeToFile(filePath, "1. 01:00 - 02:00: lecture (type: c)", true);
            } else if (day.equals("Thursday")) {
                writeToFile(filePath, "2. 02:00 - 03:00: lecture (type: f)", true);
            }
            writeToFile(filePath, LINE_SEPARATOR, true);
            writeToFile(filePath, "\n", true);
        }
    }
}
