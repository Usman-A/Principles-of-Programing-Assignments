import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The class Plan is used to create insurance plans. Insurance plans have a
 * name, premium, maximum coverage per claim, deductible. Since the class is
 * abstract, it can not be instantiated.
 */
abstract class Plan {
    //The plans name
    String name;
    //The plans premium
    long premium;
    //the maximum coverage amount per claim
    long maxCoveragePerClaim;
    //The insurance plans deductible
    long deductible;

    //Creating a new RangeCriterion object based on customer age
    RangeCriterion customerAgeCriterion = new RangeCriterion();
    //Creating a new RangeCriterion object based on customer income
    RangeCriterion customerIncomeCriterion = new RangeCriterion();


    /**
     * Constructor for the Plan class, takes in a HashMap containing data that we set the field
     * variables to.
     *
     * @param tags Data structure containing values to set the field's too.
     */
    Plan(HashMap<String, Tag> tags) {

        //Extracting data from the tags HashMap and setting the fields value to that extracted data.
        name = tags.get("NAME").getValue();
        premium = Integer.parseInt(tags.get("PREMIUM").getValue());
        maxCoveragePerClaim = Integer.parseInt(tags.get("MAX_COVERAGE_PER_CLAIM").getValue());
        deductible = Integer.parseInt(tags.get("DEDUCTIBLE").getValue());

        //Iff the customer's age exists ( not null ), create a RangeCriterion with that tag
        if (tags.get("CUSTOMER.AGE") != null) {
            customerAgeCriterion.addCriterion(tags.get("CUSTOMER.AGE"));
        }
        //Iff the customer's income exists ( not null ), create a RangeCriterion with that tag
        if (tags.get("CUSTOMER.INCOME") != null) {
            customerIncomeCriterion.addCriterion(tags.get("CUSTOMER.INCOME"));
        }
    }

    /**
     * isEligible creates a shell for a method that needs to be implemented in subclass's of this class.
     * What it should do is return a boolean depending on whether or not an object is able to be insured
     *
     * @param insurable an object that should be insurable
     * @param date      date related to the object
     * @return a boolean representing weather or not the insurance is ap
     */
    abstract boolean isEligible(Insurable insurable, Date date);

    /**
     * getInsuredItem finds and returns an insurable object
     *
     * @param customer customer that you are dealing with
     * @param database object containing lists of customers, cars, houses e.t.c.
     * @return the insuredItem object
     */
    abstract Insurable getInsuredItem(Customer customer, Database database);

    /**
     * isEligible is a method that checks if a customer is eligible to have insurance
     *
     * @param customer    customer to be checked
     * @param currentDate the current date
     * @return true or false depending on whether or not the customer can be insured.
     */
    boolean isEligible(Customer customer, Date currentDate) {
        // Extracting the age of the customer
        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBirthDate = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long age = localCurrentDate.getYear() - localBirthDate.getYear();
        // Checking if the age is in the range.
        if (!customerAgeCriterion.isInRange(age))
            return false;
        // Checking if the income is in the range, if it is then returns true otherwise returns false
        return customerIncomeCriterion.isInRange(customer.getIncome());
    }

    /*
     * The following methods are accessor methods used to access field values outside of the class.
     * the fields that can be accessed using these methods are; name, premium, maxCoverageClaim, and
     * deductible.
     */
    String getName() {
        return name;
    }

    long getPremium() {
        return premium;
    }

    long getMaxCoveragePerClaim() {
        return maxCoveragePerClaim;
    }

    long getDeductible() {
        return deductible;
    }

}
