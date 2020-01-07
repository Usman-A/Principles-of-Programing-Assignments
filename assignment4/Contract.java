import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * The class Contract is used to make contracts for the Insurance company.
 * Contracts have a contract name, customer name, plan name, start date,
 * end date and a discount percentage.
 */
class Contract {

    //Name of the contract
    private String contractName;
    //Name of the customer
    private String customerName;
    //Name of the insurance plan
    private String planName;
    //Contract's effective date
    private Date startDate;
    //Contract's termination date
    private Date endDate;
    //The discount in % for the contract
    private int discountPercentage;

    //Variable depicting what type of insurable object it is
    //Remains the same throughout all instances of this class
    static final String inputTag = "CONTRACT";

    /**
     * The constructor for the Contract class, constructs the class
     * using values from the HashMap parameter.
     *
     * @param tags A HashMap containing the contractName,
     *             customerName, planName, discountPercentage
     *             and the start and end date.
     * @throws ParseException The program is capable of throwing a Parse
     *                        Exception, it may need to be handled during
     *                        implementation
     */
    Contract(HashMap<String, Tag> tags) throws ParseException {
        //Getting the field values from the HashMap
        contractName = tags.get("CONTRACT_NAME").getValue();
        customerName = tags.get("CUSTOMER_NAME").getValue();
        planName = tags.get("PLAN_NAME").getValue();
        discountPercentage = Integer.parseInt(tags.get("DISCOUNT_PERCENTAGE").getValue());

        //Getting the date from the HashMap and converting it to one of the right format
        //Using the Utils.convertDate() method
        startDate = Utils.convertDate(tags.get("START_DATE").getValue());
        endDate = Utils.convertDate(tags.get("END_DATE").getValue());

    }

    /*
    The following methods are accessor methods, they are used to access the value of the fields.

    The fields that have accessor methods are customerName, planName, startDate, endDate, inputTag,
    and discountPercentage
     */

    public String getCustomerName() {
        return customerName;
    }

    public String getPlanName() {
        return planName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public static String getInputTag() {
        return inputTag;
    }

    public String getContractName() {
        return contractName;
    }
}
