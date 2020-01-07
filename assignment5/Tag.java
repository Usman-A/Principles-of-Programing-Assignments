class Tag {
    public enum Relation {
        SMALLER, LARGER, EQUAL
    }
    private Relation relation;
    private String name;
    private String value;

    Tag(String[] tokens) {
        name = tokens[0];

        /*
        Adding a '<' or '>' to the appropriate case's name.
        The purpose of this is to have a different key name for < and >
        so when you're running the code, consecutive criterion with < and
        > will not override each other. For example instead of processing a
        single "CAR.MILEAGE", we would look at two sperate ones "CAR.MILEAGE>"
        and "CAR.MILEAGE<"
        */
        switch (tokens[1].charAt(0)) {
            case '<':
                name = tokens[0] + '<';
                relation = Relation.SMALLER;
                break;
            case '>':
                name = tokens[0] + '>';
                relation = Relation.LARGER;
                break;
            case '=':
                relation = Relation.EQUAL;
                break;
            default:
                throw new BadCommandException("Invalid tag: ill-defined bad relation.");
        }
        value = tokens[2];
    }

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
