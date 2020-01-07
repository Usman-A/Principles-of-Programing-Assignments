import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * The class House is an insurable object.
 * This type of object has fields containing
 * info of normal insurable objects and the
 * specifics fields for a house such as post
 * code and buildDate.
 */
class Home extends Insurable {

    //Declaring fields for the postal code of the house and build date of the house
    private String postalCode;
    private Date buildDate;

    //Variable depicting what type of insurable object it is
    //Remains the same throughout all instances of this class
    static final String inputTag = "HOME";

    /**
     * The Home constructor takes in a HashMap
     * to set the value of the object's fields.
     *
     * @param tags HashMap containing build date and postal
     *             code of the house.
     * @throws ParseException The program is capable of throwing a
     *                        a Exception, may need to be handled
     *                        during implementation.
     */
    Home(HashMap<String, Tag> tags) throws ParseException {
        //Calling the Super Classes constructor with the tags parameter
        super(tags);

        //Getting value's for the field variables from the HashMap parameter.
        postalCode = tags.get("POSTAL_CODE").getValue();
        buildDate = Utils.convertDate(tags.get("BUILD_DATE").getValue());
    }

    /*
    The following methods are accessor methods that are used to get the classes
    field values from outside of the class.

    The fields that can be accessed are ownerName, value, postalCode and buildDate.
    (getValue and getOwnerName methods are inherited from the super class)

 */
    public String getPostalCode() {
        return postalCode;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public static String getInputTag() {
        return inputTag;
    }
}
