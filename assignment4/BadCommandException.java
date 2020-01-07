/**
 * The class BadCommandException is used to deal with errors.
 */
class BadCommandException extends RuntimeException {

    /**
     * Constructor for the class, creates a RuntimeException
     * with the error message as the message parameter.
     *
     * @param message Error message
     */
    BadCommandException(String message) {
        //utilizes the superclass's constructer with the message parameter
        super(message);
    }
}
