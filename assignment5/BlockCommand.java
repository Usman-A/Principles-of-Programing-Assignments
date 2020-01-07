import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

class BlockCommand extends Command {
    private String blockType;
    private HashMap<String, Tag> tags = new HashMap<>();

    BlockCommand(String blockType) {
        this.blockType = blockType;
    }

    void addTag(Tag tag) {
        tags.put(tag.getName(), tag);
    }

    String getBlockType() {
        return blockType;
    }

    @Override
    void run(Database database) throws ParseException {
        if (blockType.equals(Customer.inputTag)) {
            database.insertCustomer(new Customer(tags));
        }
        //Adding a company
        if (blockType.equals(Company.inputTag)) {
            //Creating two boolean variables, the purpose of these variables are to
            //Identify if the companies owner is another company or a customer
            boolean customer = database.isCustomer(tags.get("OWNER_NAME").getValue());
            boolean company = database.isCompany(tags.get("OWNER_NAME").getValue());
            if (customer) {
                //If its a customer, create a company using the tags parameter
                // and true boolean saying that the company is owned by a
                // customer
                database.insertCompany(new Company(tags, true));

                //Increase the wealth of the companies owner
                String companyName = tags.get("COMPANY_NAME").getValue();
                database.increaseWealth(companyName);
            } else if (company) {
                //If its a customer, create a company using the tags parameter
                // and a false boolean saying that the company is owned by another
                // company
                database.insertCompany(new Company(tags, false));

                //Increasing the wealth of the companies owner
                String companyName = tags.get("COMPANY_NAME").getValue();
                database.increaseWealth(companyName);
            }
        }
        if (blockType.equals(Home.inputTag)) {
            //add the home to the database
            database.insertHome(new Home(tags));

            //Increasing owners wealth by value of house
            String customerName = tags.get("OWNER_NAME").getValue();
            long homeValue = Long.parseLong(tags.get("VALUE").getValue());
            database.getCustomer(customerName).increaseWealth(homeValue);
        }
        if (blockType.equals(Car.inputTag)) {
            //adding the car to the database
            database.insertCar(new Car(tags));

            //Increasing owners wealth by value of house
            String customerName = tags.get("OWNER_NAME").getValue();
            long carValue = Long.parseLong(tags.get("VALUE").getValue());
            database.getCustomer(customerName).increaseWealth(carValue);
        }
        if (blockType.equals(Claim.inputTag)) {
            Claim claim = new Claim(tags);
            database.insertClaim(claim);
            if (processClaim(claim, database)) {
                claim.setSuccessful(true);
                System.out.println("Claim on " + Utils.formattedDate(claim.getDate())
                        + " for contract " + claim.getContractName() + " was successful.");
            } else {
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

    private boolean processClaim(Claim claim, Database database) {
        Contract contract = database.getContract(claim.getContractName());
        Customer customer = database.getCustomer(contract.getCustomerName());
        Plan plan = database.getPlan(contract.getPlanName());
        Insurable insurable = plan.getInsuredItem(claim.getInsurableID(), database);

        /* If INSURABLE_ID was not found or was from the wrong type (car/home).
        For example, if INSURABLE_ID corresponds to a car but the plan
        corresponds to a home then insurable would be null. */
        if (insurable == null)
            return false;

        // If the claimed item is not owned by the owner.
        if (!insurable.getOwnerName().equals(customer.getName()))
            return false;

        // If the claimed amount is higher than covered by the plan.
        if (plan.getMaxCoveragePerClaim() < claim.getAmount())
            return false;

        // If the claim date is not in the contract period.
        if (claim.getDate().after(contract.getEndDate()) || claim.getDate().before(contract.getStartDate()))
            return false;

        // If the customer was not eligible.
        if (!plan.isEligible(customer, claim.getDate()))
            return false;

        return plan.isEligible(insurable, claim.getDate());
    }


}

