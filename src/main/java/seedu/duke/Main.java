package seedu.duke;

import seedu.duke.exceptions.NoUserException;
import seedu.duke.ui.UI;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static final Scanner IN = new Scanner(System.in);
    static boolean isFinished = false;

    public static void setIsFinished(boolean b) {
        isFinished = b;
    }

    public static void main(String[] args) throws FileNotFoundException {
        UI.printGreeting();
        UserList userList = new UserList();
        Storage.createFolder();
        Storage.addExistingUsers(userList);

        if (userList.getListLength() == 0) {
            UI.printHelp();
        }

        while (!isFinished) {
            try {
                String input = IN.nextLine();
                Parser.parseCommand(input, userList);

            } catch (NoUserException e) {
                UI.printNoUsers();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}
