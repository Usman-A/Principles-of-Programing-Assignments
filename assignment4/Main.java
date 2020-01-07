import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the Main class, it is used to run the actual code, the `insurance company`. It does this by creating all the objects and runs everything,
 */
public class Main {
    public static void main(String[] args) {
        //Tries to run the main functions of the code like getting input, getting and handling the commands, if everything works than okay
        try {
            //Setting up the inputReader Object
            InputReader inputReader = InputReader.getInstance();
            //Creating a list of commands and getting the users input.
            ArrayList<Command> commands = inputReader.getCommands();
            Iterator<Command> currentCommand = commands.iterator();

            //Creates a command handler instance to execute commands.
            CommandHandler commandHandler = new CommandHandler(new Database());

            //while there is another command keep handling all the commands.
            while (currentCommand.hasNext()) {
                commandHandler.run(currentCommand.next());
            }
        }
        //If the code above can't run it tries to handle the error,
        catch (ParseException e) {
            //prints out an error message for a ParseException
            System.out.println(e.getMessage());
        } catch (BadCommandException e) {
            //Prints out an error message for BadCommandException
            System.out.println(e.getMessage());
        }
    }
}
