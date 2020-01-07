import java.text.ParseException;
import java.util.HashMap;

/**
 * This class is used to create companies. A company has the following properties, Owner Name, Company Name, Value
 * , Old Value amd a boolean flag called owner (which identifies if the owner is a company or customer)
 */
public class Company {

    //Creating fields that represent the name of the company, ownder of the company and value of
    // the company
    private String ownerName;
    private String companyName;
    //value of the company
    private long value;
    //Previous value of company before any changes occur
    private long oldValue = 0;

    //Ths field represents if the company is owned by a customer or another Company
    // true = Customer       false = Company
    private boolean owner;

    //This is used as an Input identifier, if they use COMPANY, it refers back to this
    static final String inputTag = "COMPANY";

    /**
     * @param tags    A hashmap containing the data for the fields of the class
     * @param ownedBy a boolean that represents what owns the company, true = customer
     *                and false =  company
     * @throws ParseException This method is capable of throwing a ParseException, may need to be
     *                        handeled during implementation
     */
    Company(HashMap<String, Tag> tags, boolean ownedBy) throws ParseException {
        ownerName = tags.get("OWNER_NAME").getValue();
        companyName = tags.get("COMPANY_NAME").getValue();
        value = Long.parseLong(tags.get("VALUE").getValue());
        owner = ownedBy;
    }

    //The following methods will be used as accessor methods to access the field values
    //out side of the class.

    public String getOwnerName() {
        return ownerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public long getValue() {
        return value;
    }

    public long getOldValue() {
        return oldValue;
    }

    public boolean getOwner() {
        return owner;
    }

    public static String getInputTag() {
        return inputTag;
    }

    //Increases value of company by the increment long variable
    public void increaseValue(long increment) {
        oldValue = value;
        value += increment;
    }


}
