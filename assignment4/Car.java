import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * The class Car is an insurable object.
 * This type of object has fields containing
 * info about the make,mode, mileage and purchase
 * date of a Car
 */
class Car extends Insurable {

    //Make of the Car
    private String make;
    //Model of the Car
    private String model;
    //Day the car was purchased
    private Date purchaseDate;
    //The amount of distance the car has traveled
    private long mileague;

    //Variable depicting what type of insurable object it is
    //Remains the same throughout all instances of this class
    static final String inputTag = "CAR";

    /**
     * The Car constructor takes in a HashMap
     * to set the value of the object's fields.
     *
     * @param tags HashMap containing ownersName, value,
     *             make, model, purchaseDate and mileage
     *             of the car.
     * @throws ParseException The program is capable of throwing a
     *                        a Exception, may need to be handled
     *                        during implementation.
     */
    Car(HashMap<String, Tag> tags) throws ParseException {
        //Calling the Super Classes constructor with the tags parameter
        super(tags);

        //Getting value's for the field variables from the HashMap parameter.
        make = tags.get("MAKE").getValue();
        model = tags.get("MODEL").getValue();
        purchaseDate = Utils.convertDate(tags.get("PURCHASE_DATE").getValue());
        mileague = Long.parseLong(tags.get("MILEAGE").getValue());
    }

    /* The following methods are accessor methods that are used to get the classes
       field values from outside of the class.

       The fields that can be accessed are ownerName, value, make, model, purchaseDate, mileage
       and the Input Tag. (getValue method is inherited from the super class)
    */

    /**
     * This method overrides the superclasses getOwnerName.
     *
     * @return the field ownerName, which is of type String.
     */
    public String getOwnerName() {
        return ownerName;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public long getMileague() {
        return mileague;
    }

    public static String getInputTag() {
        return inputTag;
    }
}

