package seedu.duke;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.Storage.*;

public class StorageTest {

    private static String filePath;

    @BeforeAll
    static void setUp() {
        String userName = "testUser";
        filePath = "test_data/" + userName + ".txt";
        //File f = new File(filePath);
        Storage.setFolderPath("test_data");
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
    static void deleteTestFile() {
        Storage.setFolderPath("data");
        User.setFolderPath("data");
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

//    @Test
//    public void testWriteToFileAndLoadData() throws Exception {
//        String testPath = tempDir.resolve("testUser.txt").toString();
//        Storage storage = new Storage(testPath);
//        User user = new User("testUser");
//        user.getTimetable().addUserTask("Monday", new Task("lecture", "Monday", "10:00", "11:00", "c"));
//
//        storage.writeTaskInFile(user);
//
//        Scanner scanner = new Scanner(new File(testPath));
//        assertTrue(scanner.hasNextLine(), "Empty file.");
//        assertEquals("Username: testUser", scanner.nextLine().trim(), "First line should contain the username");
//
//        // Load data and check if tasks are loaded correctly (requires modification to User and Timetable for assertion)
//        User newUser = new User("tempUser");
//        newUser.setStorage(new Storage(tempFilePath));
//        newUser.getStorage().loadData(newUser);
//        // Add assertions to verify tasks are loaded correctly
//    }
}
