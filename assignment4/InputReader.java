import java.util.ArrayList;
import java.util.Scanner;

/**
 * The InputReader class deals with user input. It takes in User Input and converts them to a print command or a block command
 */
class InputReader {
    //Creating a Scanner field called keyboard, this will be used toget user input
    private Scanner keyboard;
    //A variable to store the instance of the class.
    private static InputReader instance = null;
    private int lineNumber = 0;

    /**
     * Input reader initializes the scanner for use
     */
    private InputReader() {
        keyboard = new Scanner(System.in);
    }

    /**
     * getInstance returns the instance of the class, it checks if an instance of the class has been created and if it hasn't it is created and then returned.
     *
     * @return instance of the class InputReader
     */
    static InputReader getInstance() {
        if (instance == null) {
            instance = new InputReader();
        }
        return instance;
    }

    /**
     * The method getCommands returns a list of all the commands
     *
     * @return a list of commands
     */
    ArrayList<Command> getCommands() {
        //Initializing the list of commands
        ArrayList<Command> commands = new ArrayList<>();
        //declaring a variable used to for representing lines of input
        String line = "";
        //variable used for represnting the number of lines
        lineNumber = 0;


        //Tries to process the input
        try {
            //Keeps getting input until an end of file command (^d) is given, or if 'FINISH' is inputted
            while (keyboard.hasNext()) {
                //Increase the number of lines
                lineNumber++;
                //get the next line of input
                line = keyboard.nextLine();

                //If the users command starts with Print, add a new print Command
                // print Command is made using makePrintCommand with line as the parameter
                if (line.startsWith("PRINT ")) {
                    commands.add(makePrintCommand(line));
                }
                //If the users command starts with BEGIN_, add a new block Command
                // block Command is made using makeBlockCommand with line as the parameter
                else if (line.startsWith("BEGIN_")) {
                    commands.add(makeBlockCommand(line));
                }
                //If the input line is "FINISH", end the loop.
                else if (line.equals("FINISH")) {
                    break;
                }
                //If the input isn't a empty, print an error message saying invalid command.
                else if (!line.equals("")) {
                    System.out.println(line);
                    throw new BadCommandException("Invalid command.");
                }
            }
        }
        //If an error occurs, catch the BadCommandException and print a specified error message.
        catch (BadCommandException e) {
            throw new BadCommandException("Line " + lineNumber + " : " + e.getMessage());
        }
        //Return the list of commands
        return commands;
    }

    /**
     * makeBlockCommand takes in a line of input and creates a block command with it.
     *
     * @param line a line of input that needs to be turned into a command
     * @return a command that is extracted from THE LINE PARAMETER
     */
    private Command makeBlockCommand(String line) {
        // Removes "BEGIN_" from the current line to get the command type;
        BlockCommand command = new BlockCommand(line.substring(6));

        //Keeps getting input until an end of file command (^d) is given, or if 'END_' is inputted
        while (keyboard.hasNext()) {
            //increase the number of lines counter
            lineNumber++;
            //gets another input form the user
            line = keyboard.nextLine();
            //ends loop and returns the command if the user enters "END_"
            if (line.equals("END_" + command.getBlockType())) {
                return command;
            }
            //If nothing ins inputed, do nothing.
            else if (line.equals("")) {
            } else {
                //Split the line into different tokens, with a limit of 3
                String[] tokens = line.split(" ", 3);
                //Throw an exception with an error message if there aren't the right amount of tokens
                if (tokens.length != 3 || tokens[1].length() != 1)
                    throw new BadCommandException("Invalid tag.");
                //add the command to the list of commands
                command.addTag(new Tag(tokens));
            }
        }
        //return the list of commands
        return command;
    }

    /**
     * makePrintCommand takes in a line of input and create a print command with it.
     *
     * @param line a line of input that needs to be turned into a command
     * @return a command that is extracted from the parameter
     */
    private Command makePrintCommand(String line) {
        //Splitting line into different tokens
        String[] tokens = line.split(" ", 5);
        //if the number of tokens exceed or are below the proper amount, print an error message
        if (tokens.length > 4) {
            throw new BadCommandException("Invalid print command; too many tokens.");
        } else if (tokens.length < 4) {
            throw new BadCommandException("Invalid print command; too few tokens.");
        }
        //Otherwise return a new PrintCommand using the tokens extracted from the line of input
        return new PrintCommand(tokens);
    }
}