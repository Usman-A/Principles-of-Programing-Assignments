import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * The class HomePlan is a subclass of Plan. It contains plans specific to Home's.
 */
class HomePlan extends Plan {
    //Field depicting what type of object it is, it stays constant.
    static final String inputTag = "HOME_PLAN";

    //Creating RangeCriterion fields for home value and home age.
    private RangeCriterion homeValueCriterion = new RangeCriterion();
    private RangeCriterion homeAgeCriterion = new RangeCriterion();

    /**
     * Constructor for the Home Plan class, takes in a HashMap containing data that we set the field
     * variables to.
     *
     * @param tags Data structure containing field values
     */
    HomePlan(HashMap<String, Tag> tags) {
        //Calling the superclass's constructor with the tags paramter
        super(tags);

        //If the Home's value exists ( not null ), create a criterion with that tag
        if (tags.get("HOME.VALUE") != null) {
            homeValueCriterion.addCriterion(tags.get("HOME.VALUE"));
        }
        //If the Home's age exists ( not null ), create a criterion with that tag
        if (tags.get("HOME.AGE") != null) {
            homeAgeCriterion.addCriterion(tags.get("HOME.AGE"));
        }
    }

    /**
     * isEligible checks if the object is an instance of home,
     * if it's value is in range and if the owner's age is in range
     * then the house is insurable, otherwise its false.
     *
     * @param insurable an object that should be insurable
     * @param date      date related to the object
     * @return true or false depending on if the
     * object can be insured.
     */
    @Override
    boolean isEligible(Insurable insurable, Date date) {
        //if insurable isn't an instance of home then returns false.
        if (!(insurable instanceof Home))
            return false;

        //Converting home from an insurable object to a Home object for evaluation
        Home home = (Home) insurable;
        //If the home's value is not in range, return false.
        if (!homeValueCriterion.isInRange(home.getValue()))
            return false;

        // Extracting the age of the customer
        LocalDate localCurrentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBuiltDate = home.getBuildDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long age = localCurrentDate.getYear() - localBuiltDate.getYear();
        ;
        // Checking if the age is in the range.
        return homeAgeCriterion.isInRange(age);
    }

    /**
     * getInsuredItem gets the object containing the customers home
     * from the database and returns it
     *
     * @param customer customer that you are dealing with
     * @param database object containing lists of customers, cars, houses e.t.c.
     * @return the Insurable home's object
     */
    @Override
    Insurable getInsuredItem(Customer customer, Database database) {
        return database.getHome(customer.getName());
    }

}
