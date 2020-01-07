import java.util.HashMap;

/**
 * The Insurable class is used to create objects that can be insured.
 * These types of objects have a value, and an Owners Name.
 */
class Insurable {
    //String that stores the objects owners name.
    protected String ownerName;
    //Long that stores the value of the Insurable object.
    protected long value;

    /**
     * Constructor for the Insurable class
     * uses data type HashMap, to set the
     * objects field values.
     *
     * @param tags a HashMap containing the
     *             ownersName and the value
     *             of the object
     */
    Insurable(HashMap<String, Tag> tags) {
        // Getting the ownerName and value, values from the HashMap
        ownerName = tags.get("OWNER_NAME").getValue();
        value = Long.parseLong(tags.get("VALUE").getValue());
    }

    /**
     * Return's the objects owner's name.
     *
     * @return the ownerName field
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Return's the objects value
     *
     * @return the value field
     */
    public long getValue() {
        return value;
    }
}
