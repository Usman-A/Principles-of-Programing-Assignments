/**
 * The tag class is an class that contains contains a name, value and a relation.
 * this tag is used to create instances of other objects later on.
 */
class Tag {

    /**
     * Relation is a enumeration data type, it contains the 3 constants SMALLER, LARGER and EQUAL
     * These are the only types of relations a tag can have.
     */
    public enum Relation {
        SMALLER, LARGER, EQUAL
    }

    //Creating the field for relation
    private Relation relation;
    //Creating the field that stores name
    private String name;
    //Creating a string field that stores the value
    private String value;

    /**
     * Constructor for the class
     *
     * @param tokens List of strings that field values will be taken from
     */
    Tag(String[] tokens) {
        //Setting the Tag's name to the first string in the list
        name = tokens[0];
        //Checking to see if the the first character of the 2nd string i the list is < > or =
        //depending on the result, it would set relation to the signs corresponding value.
        switch (tokens[1].charAt(0)) {
            case '<':
                relation = Relation.SMALLER;
                break;
            case '>':
                relation = Relation.LARGER;
                break;
            case '=':
                relation = Relation.EQUAL;
                break;
            default:
                //If nothing matches, return an Exception
                throw new BadCommandException("Invalid tag: ill-defined bad relation.");
        }
        //The value is set to the third element in a tag
        value = tokens[2];
    }

    /*
    The following methods are accessor methods, they're used to retrieve the fields of the class
    from other classes. The fields that can be accessed are the relation field, name field and value
    field.
     */
    public Relation getRelation() {
        return relation;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
