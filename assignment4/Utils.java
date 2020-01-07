import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * The class utils is used for dealing with dates. Applications of this class would
 * be able to convert a user given input into an instance of the Date class, and it
 * should be able to take a Date object and convert it back to a string.
 */
class Utils {

    //Static field used to format inputs to the proper `yyyy-MM-dd` format.
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Takes a date as a string and formats it and returns the date as an Object.
     *
     * @param input a string that is to be converted
     * @return returns the inputted date as an Instance of the date class
     * @throws ParseException Method is able to throw a ParseException, might need to be handled during implementation.
     */
    static Date convertDate(String input) throws ParseException {
        return formatter.parse(input);
    }

    /**
     * Does the opposite of convertDate,  takes an instance of Date and coverts it to a string following
     * the correct date format.
     *
     * @param date the date that is to be converted
     * @return the date as a string that follows the yyyy-MM-dd format
     */
    static String formattedDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
