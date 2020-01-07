import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * the class Claim deales with different insurance claims on insurable objects. Claims have a contractName
 * amount, date and whether or not that claim was successful.
 */
class Claim {

    //Declaring the fields contractName, amount, date and successful.
    private String contractName;
    private long amount;
    private Date date;
    private boolean successful;

    //Field depicting what type of object it is, it stays constant.
    static final String inputTag = "CLAIM";

    /**
     * Constructor for the Claim class, takes date from the HashMap parameter
     * and uses it to set filed values.
     *
     * @param tags HashMap containing contractName, amount, date and
     *             successful.
     * @throws ParseException The program is capable of throwing a ParseException,
     *                        this may need to be handled later on.
     */
    Claim(HashMap<String, Tag> tags) throws ParseException {
        //Getting data from the parameter tags and setting
        //the field values to that data
        contractName = tags.get("CONTRACT_NAME").getValue();
        date = Utils.convertDate(tags.get("DATE").getValue());
        amount = Long.parseLong(tags.get("AMOUNT").getValue());
    }


    /*
    The following methods are accessor methods used to access the field values form outside this class.
    The fields that have their own accessor methods are contractName, date and amount
     */
    public String getContractName() {
        return contractName;
    }

    public long getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public boolean wasSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
