import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

abstract class Plan {
    String name;
    long premium;
    long maxCoveragePerClaim;
    long deductible;
    RangeCriterion customerAgeCriterion = new RangeCriterion();
    RangeCriterion customerIncomeCriterion = new RangeCriterion();
    //Creating a new criterion for Customer Wealth
    RangeCriterion customerWealthCriterion = new RangeCriterion();

    Plan(HashMap<String, Tag> tags) {
        //Initializes class fields using data extracted from the HashMap
        name = tags.get("NAME").getValue();
        premium = Integer.parseInt(tags.get("PREMIUM").getValue());
        maxCoveragePerClaim = Integer.parseInt(tags.get("MAX_COVERAGE_PER_CLAIM").getValue());
        deductible = Integer.parseInt(tags.get("DEDUCTIBLE").getValue());


        //Adding criteria to the criterion objects
        //Adding the criteria with the new Keys denoting if its < or >
        if (tags.get("CUSTOMER.AGE") != null) {
            customerAgeCriterion.addCriterion(tags.get("CUSTOMER.AGE"));
        }
        if (tags.get("CUSTOMER.AGE>") != null) {
            customerAgeCriterion.addCriterion(tags.get("CUSTOMER.AGE>"));
        }
        if (tags.get("CUSTOMER.AGE<") != null) {
            customerAgeCriterion.addCriterion(tags.get("CUSTOMER.AGE<"));
        }
        if (tags.get("CUSTOMER.INCOME") != null) {
            customerIncomeCriterion.addCriterion(tags.get("CUSTOMER.INCOME"));
        }
        if (tags.get("CUSTOMER.INCOME<") != null) {
            customerIncomeCriterion.addCriterion(tags.get("CUSTOMER.INCOME<"));
        }
        if (tags.get("CUSTOMER.INCOME>") != null) {
            customerIncomeCriterion.addCriterion(tags.get("CUSTOMER.INCOME>"));
        }

        //Adding the criterion for customer wealth based off of the new tags
        if (tags.get("CUSTOMER.WEALTH") != null) {
            customerWealthCriterion.addCriterion(tags.get("CUSTOMER.WEALTH"));
        }
        if (tags.get("CUSTOMER.WEALTH<") != null) {
            customerWealthCriterion.addCriterion(tags.get("CUSTOMER.WEALTH<"));
        }
        if (tags.get("CUSTOMER.WEALTH>") != null) {
            customerWealthCriterion.addCriterion(tags.get("CUSTOMER.WEALTH>"));
        }

    }

    abstract boolean isEligible(Insurable insurable, Date date);

    abstract ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database);

    abstract Insurable getInsuredItem(String insurableID, Database database);

    boolean isEligible(Customer customer, Date currentDate) {
        // Extracting the age of the customer
        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBirthDate = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //Stores the assumed age
        long assumedAge = localCurrentDate.getYear() - localBirthDate.getYear();

        //Stores the final age
        long age;

        //Compare the days to be sure that the persons birthday has already happened
        //if it has happened then our assumedAge is valid, otherwise subtract one
        if (localBirthDate.getDayOfYear() <= localCurrentDate.getDayOfYear()) {
            age = assumedAge;
        } else {
            age = assumedAge - 1;
        }

        // Checking if the age is in the range.
        if (!customerAgeCriterion.isInRange(age))
            return false;
        //Checking customer wealrth
        if (!customerWealthCriterion.isInRange(customer.getWealth())) {
            return false;
        }
        // Checking if the income is in the range.
        return customerIncomeCriterion.isInRange(customer.getIncome());
    }

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
