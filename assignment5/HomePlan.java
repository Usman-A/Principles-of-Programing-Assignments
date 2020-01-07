import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class HomePlan extends Plan {
    static final String inputTag = "HOME_PLAN";
    private RangeCriterion homeValueCriterion = new RangeCriterion();
    private RangeCriterion homeAgeCriterion = new RangeCriterion();

    HomePlan(HashMap<String, Tag> tags) {
        //Calling the super classes constructor using the tags parameter
        super(tags);

        //Adding criteria to the criterion objects
        //Adding the criteria with the new Keys denoting if its < or >
        if (tags.get("HOME.VALUE") != null) {
            homeValueCriterion.addCriterion(tags.get("HOME.VALUE"));
        }
        if (tags.get("HOME.VALUE>") != null) {
            homeValueCriterion.addCriterion(tags.get("HOME.VALUE>"));
        }
        if (tags.get("HOME.VALUE<") != null) {
            homeValueCriterion.addCriterion(tags.get("HOME.VALUE<"));
        }
        if (tags.get("HOME.AGE") != null) {
            homeAgeCriterion.addCriterion(tags.get("HOME.AGE"));
        }
        if (tags.get("HOME.AGE<") != null) {
            homeAgeCriterion.addCriterion(tags.get("HOME.AGE<"));
        }
        if (tags.get("HOME.AGE>") != null) {
            homeAgeCriterion.addCriterion(tags.get("HOME.AGE>"));
        }

    }

    @Override
    boolean isEligible(Insurable insurable, Date date) {
        if (!(insurable instanceof Home))
            return false;

        Home home = (Home) insurable;
        if (!homeValueCriterion.isInRange(home.getValue()))
            return false;

        // Extracting the age of the home
        LocalDate localCurrentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBuiltDate = home.getBuildDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //Stores the assumed age
        long assumedAge = localCurrentDate.getYear() - localBuiltDate.getYear();

        //Stores the final age
        long age;

        //Compare the days to be sure that the anniversary of the item has happened or not.
        if (localBuiltDate.getDayOfYear() <= localCurrentDate.getDayOfYear()) {
            age = assumedAge;
        } else {
            age = assumedAge - 1;
        }
        return homeAgeCriterion.isInRange(age);
    }

    @Override
    ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database) {
        return database.getHomesByOwnerName(customer.getName());
    }

    @Override
    Insurable getInsuredItem(String insurableID, Database database) {
        return database.getHomeByPostalCode(insurableID);
    }
}
