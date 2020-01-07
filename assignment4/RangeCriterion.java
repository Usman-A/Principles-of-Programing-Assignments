/**
 * The RangeCriterion class is a class used for checking if things meet a criteria.
 */
class RangeCriterion {
    //Declaring field for maximum value in a range
    private long maxValue = Long.MAX_VALUE;
    //Declaring field for minimum value in a range
    private long minValue = Long.MIN_VALUE;

    /**
     * addCriterion takes in a Tag, checks its relation. Depending on weather its smaller
     * or larger it sets the min and max values.
     *
     * @param tag object containing relation name and value of objects.
     */
    void addCriterion(Tag tag) {
        if (tag.getRelation() == Tag.Relation.LARGER) {
            //setting min value based on tag relation being larger
            minValue = Math.max(minValue, Long.parseLong(tag.getValue()));
        }
        if (tag.getRelation() == Tag.Relation.SMALLER) {
            //setting max value based on tag relation being smaller
            maxValue = Math.min(maxValue, Long.parseLong(tag.getValue()));
        }
    }

    /**
     * isInRange takes a long and checks if it is within a certain range
     * if so it returns true, else false.
     *
     * @param value the value to be checked whether or not it is in range
     * @return boolean, true if in range otherwise false.
     */
    boolean isInRange(long value) {
        if (value < maxValue && value > minValue)
            return true;
        return false;
    }
}
