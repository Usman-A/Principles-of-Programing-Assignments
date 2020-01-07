import java.text.ParseException;

/**
 * The class Command handler is used to run the different commands
 */
class CommandHandler {
    //Creating a database Field
    Database database;

    /**
     * Constructor for the class, sets the instances database field value with the given @param
     *
     * @param database object used to store all of the data regarding the insurance company.
     *                 this includes customers, cars, homes claims and plans.
     */
    CommandHandler(Database database) {
        this.database = database;
    }

    /**
     * The method run takes in a Command as a parameter and executes the command by calling the command run function
     * form the class command
     *
     * @param command variable Containing the command that needs to be handled
     * @throws ParseException The program is capable of throwing an exception, this exception may need to be handled
     *                        later on.
     */
    void run(Command command) throws ParseException {
        command.run(database);
    }
}
