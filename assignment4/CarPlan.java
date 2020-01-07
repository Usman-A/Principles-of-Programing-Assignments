import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * The class CarPlan is a subclass of Plan. It contains plans specific to Car's.
 */
class CarPlan extends Plan {

    //Field depicting what type of object it is, it stays constant.
    static final String inputTag = "CAR_PLAN";
    //Creating a RangeCriterion Field for mileage.
    RangeCriterion mileageCriterion = new RangeCriterion();

    /**
     * Constructor for the Car Plan class, takes in a HashMap containing data that we set the field
     * variables to.
     *
     * @param tags Data structure containing field values
     */
    CarPlan(HashMap<String, Tag> tags) {
        //Calling the superclass's constructor with the tags parameter
        super(tags);

        //Iff the customer's income exists ( not null ), create a criterion with that tag
        if (tags.get("CAR.MILEAGE") != null) {
            mileageCriterion.addCriterion(tags.get("CAR.MILEAGE"));
        }
    }

    /**
     * isEligible checks if the object is an instance of car,
     * if it is and the mileage is within range then the car is
     * insurable, otherwise its false.
     *
     * @param insurable an object that should be insurable
     * @param date      date related to the object
     * @return true or false depending on if the
     * object can be insured.
     */
    @Override
    boolean isEligible(Insurable insurable, Date date) {
        //if insurable isn't an instance of car then returns false.
        if (!(insurable instanceof Car))
            return false;
        //Converts the Insurable object to a Car object, then returns
        //if its within the mileage range.
        Car car = (Car) insurable;
        return mileageCriterion.isInRange(car.getMileague());
    }

    /**
     * getInsuredItem gets the object containing the customers car
     * from the database and returns it
     *
     * @param customer customer that you are dealing with
     * @param database object containing lists of customers, cars, houses e.t.c.
     * @return the Insurable car's object
     */
    @Override
    Insurable getInsuredItem(Customer customer, Database database) {
        return database.getCar(customer.getName());
    }
}
