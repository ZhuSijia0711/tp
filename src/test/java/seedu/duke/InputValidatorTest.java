package seedu.duke;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.InvalidFormatException;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {
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
            assertEquals("[ERROR] Invalid changeTaskTiming format. Expected format: " +
                    "changeTaskTiming /on [day] /index [index] /from [new start time] /to [new end time]", e.getMessage());
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
            assertEquals("[ERROR] Invalid addRepeatTask format. Expected format: addRepeatTask " +
                    "/task [description] /on [day(s)] /from [start time] /to [end time] /type [f/c]", e.getMessage());
        }
    }

    @Test
    public void testCorrectInputsAddTask() {
        String input1 = "addtask /on monday /task running together /from 10:00 /to 19:00 /type f";
        String input2 = "AddTask /on Saturday /task run /from 8:00 /to 12:00 /type c  ";
        String input3 = "addTASK /on sunday /task running together forever /from 11:00 /to 11:15 /type f ";
        String input4 = "addtask /on tuesday /task do nothing /from 17:00 /to 23:59 /type c";
        String input5 = "addtask /on Wednesday /task tryitout_please /from 00:00 /to 01:00 /type f";

        String[] inputs = {input1, input2, input3, input4, input5};

        for (String input : inputs) {
            try {
                InputValidator.validateAddTaskInput(input);
            } catch (InvalidFormatException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testInvalidInputsAddTask() {
        String input1 = "ddtask /on monday /task running together /from 10:00 /to 19:00 /type f";
        String input2 = "addtask /o Saturday /task run /from 8:00 /to 12:00 /type c  ";
        String input3 = "addtask /on sunday //task running together forever /from 11:00 /to 11:15 /type f ";
        String input4 = "addtask /on tuesday /task do nothing /from 17:00 /to 23:590 /type c";
        String input5 = "addtask /on Wednesday /task tryitout_please /from 00:00 /to 01:00 /type ";

        String[] inputs = {input1, input2, input3, input4, input5};

        for (String input : inputs) {
            try {
                InputValidator.validateAddTaskInput(input);
            } catch (InvalidFormatException e) {
                assertEquals("[ERROR] Invalid addtask format. Expected format: addtask " +
                        "/on [day] /task [description] /from [start time] /to [end time] /type [f/c]", e.getMessage());
            }
        }
    }

    @Test
    public void testCorrectInputsAddForAll() {
        String input1 = "addforall /on monday /task running together /from 10:00 /to 19:00 /type f";
        String input2 = "Addforall /on Saturday /task run /from 8:00 /to 12:00 /type c  ";
        String input3 = "addforall /on sunday /task running together forever /from 11:00 /to 11:15 /type f ";
        String input4 = "addforall /on tuesday /task do nothing /from 17:00 /to 23:59 /type c";
        String input5 = "addforall /on Wednesday /task tryitout_please /from 00:00 /to 01:00 /type f";

        String[] inputs = {input1, input2, input3, input4, input5};

        for (String input : inputs) {
            try {
                InputValidator.validateAddTaskForAll(input);
            } catch (InvalidFormatException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testInvalidInputsAddForAll() {
        String input1 = "ddforall /on monday /task running together /from 10:00 /to 19:00 /type f";
        String input2 = "Addforall /o Saturday /task run /from 8:00 /to 12:00 /type c  ";
        String input3 = "addforall /on sunday //task running together forever /from 11:00 /to 11:15 /type f ";
        String input4 = "addforall /on tuesday /task do nothing /from 17:00 /to 23:590 /type c";
        String input5 = "addforall /on Wednesday /task tryitout_please /from 00:00 /to 01:00 /type ";

        String[] inputs = {input1, input2, input3, input4, input5};

        for (String input : inputs) {
            try {
                InputValidator.validateAddTaskForAll(input);
            } catch (InvalidFormatException e) {
                assertEquals("[ERROR] Invalid addtask format. Expected format: addforall " +
                        "/on [day] /task [description] /from [start time] /to [end time] /type [f/c]", e.getMessage());
            }
        }
    }

}