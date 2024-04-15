package seedu.duke;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import seedu.duke.exceptions.InvalidDayException;
import seedu.duke.exceptions.InvalidFormatException;
class InputValidatorTest {
    @Test
    public void testAddUser() {
        String[] inputList = {
            "adduser john",
            "adduser john100 ",
            "ADDUSEr j",
            "ADDUSER  xjohnx",
            "addUser 20  "
        };
        for (String input : inputList) {
            try {
                InputValidator.validateAddUserInput(input);
            } catch (InvalidFormatException e){
                fail();
            }
        }
    }

    @Test
    public void testValidChangeTaskTimingFormat() {
        String validInput = "changeTaskTiming /on monday /index 1 /from 09:00 /to 10:00";
        try {
            InputValidator.validateChangeTaskTiming(validInput);
        } catch (InvalidFormatException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidChangeTaskTimingFormat() {
        String invalidInput = "changeTaskTiming /on monday /index 1 /from 09:00";
        try {
            InputValidator.validateChangeTaskTiming(invalidInput);
            fail("Expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid changeTaskTiming format. Expected format: changeTaskTiming " +
                    "/on [day] /index [index] /from [new start time] /to [new end time]", e.getMessage());
        }
    }
    @Test
    public void testValidChangeTaskTypeFormat() {
        String validInput = "changetasktype /on Monday /index 1 /type f";
        try {
            InputValidator.validateChangeTaskType(validInput);
        } catch (InvalidFormatException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidChangeTaskTypeFormat() {
        String invalidInput = "changetasktype /on Monday /index 1 /type a";
        try {
            InputValidator.validateChangeTaskType(invalidInput);
            fail("Expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid changeTaskType format. Expected format: " +
                    "changeTaskType /on [day] /index [index] /type [f/c]", e.getMessage());
        }
    }
    @Test
    public void testValidAddRepeatTaskFormat() {
        String validInput = "addRepeatTask /task lec /on monday tuesday /from 08:00 /to 10:00 /type f";
        try {
            InputValidator.validateAddRepeatTask(validInput);
        } catch (InvalidFormatException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidAddRepeatTaskFormat() {
        String invalidInput = "addRepeatTask /task lec /from 08:00 /to 10:00 /type f";
        try {
            InputValidator.validateAddRepeatTask(invalidInput);
            fail("Expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid addRepeatTask format. Expected format: addRepeatTask /task " +
                    "[description] /on [day(s)] /from [start time] /to [end time] /type [f/c]", e.getMessage());
        }
    }

    @Test
    public void testValidateDay() {
        String[] inputList = {"monday", "tuesday", "wednesday", "thursday", "friday", "Monday", "MONDAY"};
        for (String input : inputList) {
            try {
                InputValidator.validateDay(input);
            } catch (InvalidDayException e) {
                fail();
            }
        }
    }

    @Test
    public void testAddTask() {
        String[] inputList = {
            "addtask /on MONDAY /task walk dogs /from 10:00 /to 16:00 /type c",
            "ADDTASK /on tuesday /task walk dogs   /from 00:00 /to 23:59 /type f",
            "addtask /on MONDAY /task walk dogs100  /from 10:00 /to 16:00 /type c",
            "Addtask /on sunday   /task walk dogssSS /from 10:00   /to 16:00   /type f",
            "addtask  /on Friday /task walk dogs /from 10:00 /to 16:00 /type c"
        };
        for (String input : inputList) {
            try {
                InputValidator.validateAddTaskInput(input);
            } catch (InvalidFormatException e){
                fail();
            }
        }
    }

    @Test
    public void testInvalidAddTask() {
        //addtask mispelled
        String invalidInput1 = "addtas /on MONDAY /task walk dogs /from 10:00 /to 16:00 /type c";
        try {
            InputValidator.validateAddTaskInput(invalidInput1);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid addtask format. " +
                    "Expected format: addtask /on [day] /task [description] /from [start time] /to [end time] " +
                    "/type [f/c]", e.getMessage());
        }
        //No space  between from time and "/to"
        String invalidInput2 = "addtask /on MONDAY /task walk dogs /from 10:00/to 16:00 /type c";
        try {
            InputValidator.validateAddTaskInput(invalidInput2);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid addtask format. " +
                    "Expected format: addtask /on [day] /task [description] /from [start time] /to [end time] " +
                    "/type [f/c]", e.getMessage());
        }
        //Monday extra y
        String invalidInput3 = "addtask /on MONDAYy /task walk dogs /from 10:00 /to 16:00 /type c";
        try {
            InputValidator.validateAddTaskInput(invalidInput3);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid addtask format. " +
                    "Expected format: addtask /on [day] /task [description] /from [start time] /to [end time] " +
                    "/type [f/c]", e.getMessage());
        }
        //different type v
        String invalidInput4 = "addtask /on MONDAY /task walk dogs /from 10:00 /to 16:00 /type v";
        try {
            InputValidator.validateAddTaskInput(invalidInput4);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid addtask format. " +
                    "Expected format: addtask /on [day] /task [description] /from [start time] /to [end time] " +
                    "/type [f/c]", e.getMessage());
        }
    }

    @Test
    public void testDeleteTask() {
        String[] inputList = {
            "deletetask /on MONDAY /index 1",
            "deletetask /on saturday /index 100",
            "Deletetask /on MONDAY /index 12",
            "DELETETASK /on tuesdAY /index 1",
            "deleteTASK /on saturday  /index 4000"
        };
        for (String input : inputList) {
            try {
                InputValidator.validateDeleteTaskInput(input);
            } catch (InvalidFormatException e){
                fail();
            }
        }
    }


    @Test
    public void testCompare() {
        String[] inputList = {"compare john jill", "COMPARE john jill  ", "COMpare john bill", "comPArE     jane  yu"};
        for (String input : inputList) {
            try {
                InputValidator.validateCompareInput(input);
            } catch (InvalidFormatException e) {
                fail();
            }
        }
    }

    @Test
    public void testInvalidCompare() {
        String invalidInput1 = "compare john jack jill";
        try {
            InputValidator.validateCompareInput(invalidInput1);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compare format. " +
                    "Expected format: compare <user1> <user2>", e.getMessage());
        }
        String invalidInput2 = "compar john jack ";
        try {
            InputValidator.validateCompareInput(invalidInput2);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compare format. " +
                    "Expected format: compare <user1> <user2>", e.getMessage());
        }
        String invalidInput3 = "compare jo";
        try {
            InputValidator.validateCompareInput(invalidInput3);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compare format. " +
                    "Expected format: compare <user1> <user2>", e.getMessage());
        }
    }

    @Test
    public void testCompareAll() {
        String[] inputList = {"compareall ", "COMPAREALL  ", "COMpareAll", "comPArEall"};
        for (String input : inputList) {
            try {
                InputValidator.validateCompareAllInput(input);
            } catch (InvalidFormatException e) {
                fail();
            }
        }
    }

    @Test
    public void testInvalidCompareAll() {
        String invalidInput1 = "compareall l";
        try {
            InputValidator.validateCompareAllInput(invalidInput1);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compareall format. " +
                "Expected format: compareall", e.getMessage());
        }
        String invalidInput2 = "comparall ";
        try {
            InputValidator.validateCompareAllInput(invalidInput2);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compareall format. " +
                    "Expected format: compareall", e.getMessage());
        }
        String invalidInput3 = "compareallllll";
        try {
            InputValidator.validateCompareAllInput(invalidInput3);
        } catch (InvalidFormatException e) {
            assertEquals("[ERROR] Invalid compareall format. " +
                    "Expected format: compareall", e.getMessage());
        }
    }
}
