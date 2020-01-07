import java.text.ParseException;
import java.util.HashMap;

/**
 * Block Command is a subclass of Command, it processes the users command and creates objects based on the command type
 */
class BlockCommand extends Command {
    //Creating a field called blockType
    private String blockType;
    //Creating a HashMap field called tags
    private HashMap<String, Tag> tags = new HashMap<>();

    /**
     * Constructor for the class BlockCommand, takes in a name string and sets the field with
     * that strings value
     *
     * @param blockType string indicating what type of block command is being created
     */
    BlockCommand(String blockType) {
        this.blockType = blockType;
    }

    /**
     * adds a tag to the HashMap field
     *
     * @param tag Tag to be added to the tags HashMap
     */
    void addTag(Tag tag) {
        tags.put(tag.getName(), tag);
    }

    /**
     * This is an accessor method that returns the blockType field
     *
     * @return the blockType field
     */
    String getBlockType() {
        return blockType;
    }

    /**
     * Runs the commands that the user inputs. When it deals with claims, it prints if the claim is successful or not
     *
     * @param database object used to store all of the data regarding the insurance company.
     * @throws ParseException this program is capable of throwing an exception, this may need to be handled
     *                        later on during implementation.
     */
    @Override
    void run(Database database) throws ParseException {
        /*
        Creates either a customer, home, car, claim, contract, HomePlan or CarPlan depending on what
        the block type is and stores it in the database.
         */
        if (blockType.equals(Customer.inputTag)) {
            database.insertCustomer(new Customer(tags));
        }
        if (blockType.equals(Home.inputTag)) {
            database.insertHome(new Home(tags));
        }
        if (blockType.equals(Car.inputTag)) {
            database.insertCar(new Car(tags));
        }
        if (blockType.equals(Claim.inputTag)) {
            //Create a new claim using tags and stores it in the database.
            Claim claim = new Claim(tags);
            database.insertClaim(claim);
            //If the claim can be processed, set the claims flag to true and print a success message
            if (processClaim(claim, database)) {
                claim.setSuccessful(true);
                System.out.println("Claim on " + Utils.formattedDate(claim.getDate())
                        + " for contract " + claim.getContractName() + " was successful.");
            }
            //Otherwise, set the flag to false and print an error message
            else {
                claim.setSuccessful(false);
                System.out.println("Claim on " + Utils.formattedDate(claim.getDate())
                        + " for contract " + claim.getContractName() + " was not successful.");
            }
        }
        if (blockType.equals(Contract.inputTag)) {
            database.insertContract(new Contract(tags));
        }
        if (blockType.equals(HomePlan.inputTag)) {
            database.insertPlan(new HomePlan(tags));
        }
        if (blockType.equals(CarPlan.inputTag)) {
            database.insertPlan(new CarPlan(tags));
        }
    }

    /**
     * the method processClaim is supposed to check whether a claim can or cannot be processed
     * depending on the result it returns true or false.
     *
     * @param claim    claim to be processed
     * @param database database containing all the instances of the other objects
     * @return true or false depending on whether or not the claim can be processed
     */
    private boolean processClaim(Claim claim, Database database) {
        //Extracting data from the database to create Contract, customer and insurable objects
        Contract contract = database.getContract(claim.getContractName());
        Customer customer = database.getCustomer(contract.getCustomerName());
        Plan plan = database.getPlan(contract.getPlanName());
        Insurable insurable = plan.getInsuredItem(customer, database);

        // If the claimed amount is higher than covered by the plan. The claim is not processed, returns false.
        if (plan.getMaxCoveragePerClaim() < claim.getAmount())
            return false;

        // If the claim date is not in the contract period. The claim is not processed, returns false.
        if (claim.getDate().after(contract.getEndDate()) || claim.getDate().before(contract.getStartDate()))
            return false;

        // If the customer was not eligible. The claim is not processed, returns false.
        if (!plan.isEligible(customer, claim.getDate()))
            return false;

        //If the plan eligible then the claim can be processed, return true.
        return plan.isEligible(insurable, claim.getDate());
    }
}
