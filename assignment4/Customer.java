import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * The class Customer is used to create a customer for the insurance company,
 * customers have a name, date of birth and income.
 */
class Customer {

    //Declaring the class's fields, name, dateOfBirth and income.
    private String name;
    private Date dateOfBirth;
    private long income;

    //Variable depicting what type of insurable object it is
    //Remains the same throughout all instances of this class
    static final String inputTag = "CUSTOMER";

    /**
     * Constructor for the Customer class, takes in a HashMap parameter
     * and sets the value of the fields
     *
     * @param tags a HashMap containing name, dateOfBirth and income
     * @throws ParseException The program is capable of throwing an
     *                        Exception, may need to be handled
     *                        during implementation.
     */
    Customer(HashMap<String, Tag> tags) throws ParseException {
        //Getting the field values from the HashMap
        name = tags.get("NAME").getValue();
        dateOfBirth = Utils.convertDate(tags.get("DATE_OF_BIRTH").getValue());
        income = Long.parseLong(tags.get("INCOME").getValue());
    }

    /*
    The following methods are accessor methods and are used to access the fields of the class.

    The fields that can be accessed are name, dateOfBirth, income and inputTag
     */

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public long getIncome() {
        return income;
    }

    public static String getInputTag() {
        return inputTag;
    }
}
