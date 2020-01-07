import java.text.ParseException;

/**
 * PrintCommand is a Command, it deals with printing information regarding the insurance company, such as
 * printing things related to customers and plans.
 */
class PrintCommand extends Command {
    //Field representing the type of object
    private String entityType;
    //Field representing the type of query you want to do
    private String queryType;
    //Field representing the value queryValue
    private String queryValue;

    //THe classes constructor, getting the entityType, queryType and queryValue from the tokens parameter
    PrintCommand(String[] tokens) {
        //Calling the super class's constructor
        super();
        //Setting the fields
        entityType = tokens[1];
        queryType = tokens[2];
        queryValue = tokens[3];
    }

    /**
     * This method chooses which type of print to run, printPLan or Print Customer
     *
     * @param database object used to store all of the data regarding the insurance company.
     */
    @Override
    void run(Database database) {
        if (entityType.equals("CUSTOMER"))
            runPrintCustomer(database);
        else if (entityType.equals("PLAN"))
            runPrintPlan(database);
        else {
            throw new BadCommandException("Bad print command.");
        }
    }

    /**
     * This method takes in a database, and from that database extracts the customer data that needs to bne printed
     * and prints it. Types are Total amount claimed and total amount received
     *
     * @param database object used to store all of the data regarding the insurance company.
     */
    private void runPrintCustomer(Database database) {
        if (queryType.equals("TOTAL_CLAIMED")) {
            System.out.println("Total amount claimed by " + database.getCustomer(queryValue).getName() +
                    " is " + database.totalClaimAmountByCustomer(queryValue));
        } else if (queryType.equals("TOTAL_RECEIVED")) {
            System.out.println("Total amount received by " + database.getCustomer(queryValue).getName() +
                    " is " + database.totalReceivedAmountByCustomer(queryValue));
        }
    }

    //TODO

    /**
     * The function takes in a database full of entries and extracts the plan being used and prints it
     *
     * @param database object used to store all of the data regarding the insurance company.
     */
    private void runPrintPlan(Database database) {
        //System.out.println("PRINT PLAN feature is not yet implemented.");
        if (queryType.equals("NUM_CUSTOMERS")) {
            System.out.println("Number of customers under " + database.getPlan(queryValue).getName() +
                    " is " + database.totalCustomersInPlan(queryValue));
        } else if (queryType.equals("TOTAL_PAYED_TO_CUSTOMERS")) {
            System.out.println("Total amount payed under " + database.getPlan(queryValue).getName() +
                    " is " + database.totalPayedToCustomers(queryValue));
        }
    }
}
