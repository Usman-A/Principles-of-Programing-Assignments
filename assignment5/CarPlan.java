import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class CarPlan extends Plan {
    static final String inputTag = "CAR_PLAN";
    RangeCriterion mileageCriterion = new RangeCriterion();

    CarPlan(HashMap<String, Tag> tags) {
        //Calling the super classes constructor using the tags parameter
        super(tags);

        //Adding criteria to the criterion objects
        if (tags.get("CAR.MILEAGE") != null) {
            mileageCriterion.addCriterion(tags.get("CAR.MILEAGE"));
        }

        //Adding the criteria with the new Keys denoting if its < or >
        if (tags.get("CAR.MILEAGE<") != null) {
            mileageCriterion.addCriterion(tags.get("CAR.MILEAGE<"));
        }
        if (tags.get("CAR.MILEAGE>") != null) {
            mileageCriterion.addCriterion(tags.get("CAR.MILEAGE>"));
        }


    }

    @Override
    boolean isEligible(Insurable insurable, Date date) {
        if (!(insurable instanceof Car))
            return false;
        Car car = (Car) insurable;

        return mileageCriterion.isInRange(car.getMileage());
    }

    @Override
    ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database) {
        return database.getCarsByOwnerName(customer.getName());
    }

    @Override
    Insurable getInsuredItem(String insurableID, Database database) {
        return database.getCarByPlateNumber(insurableID);
    }

}
