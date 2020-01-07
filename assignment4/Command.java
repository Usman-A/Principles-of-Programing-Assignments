import java.text.ParseException;

/**
 * The abstract class Command is used as a shell for other types of Commands. It can not be Instantiated and is
 * basically made to be extended.
 */
abstract class Command {

    /**
     * The class run is an abstract class, this means that this method
     * must be implemented by any class that extends the class Command
     *
     * @param database object used to store all of the data regarding the insurance company.
     * @throws ParseException The program is capable of throwing a ParseException, this may need to
     *                        be handled during implementation.
     */
    abstract void run(Database database) throws ParseException;
}
