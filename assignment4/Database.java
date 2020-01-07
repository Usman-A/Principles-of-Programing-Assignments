import java.util.ArrayList;

/**
 * The class Database is used to store all of the data regarding the insurance company.
 * the data it contains consists of customers, homes,  cars, plans, contracts and claims.
 */
class Database {
    //Declaring fields that will be used to store the customers, homes, cars, plans, contracts and claims.
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Home> homes = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Plan> plans = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();
    private ArrayList<Claim> claims = new ArrayList<>();

    /*
     The following methods are used to add an element to the list of data types.

     There are methods that let you add to homes, cars, customers, plans, contracts and claims.
    */
    void insertHome(Home home) {
        homes.add(home);
    }

    void insertCar(Car car) {
        cars.add(car);
    }

    void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    void insertPlan(Plan plan) {
        plans.add(plan);
    }

    void insertClaim(Claim claim) {
        claims.add(claim);
    }

    void insertContract(Contract contract) {
        contracts.add(contract);
    }

    /**
     * getCustomer searches through the list of customers and returns the one with the matching name
     * if it's not found it returns null
     *
     * @param name The name of the customer you're looking for
     * @return That customers specific object.
     */
    Customer getCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    /**
     * getPlan searches through the list of plans and returns the one with the matching name
     * if it's not found it returns null
     *
     * @param name The name of the plan you're looking for
     * @return That plan's specific object.
     */
    Plan getPlan(String name) {
        for (Plan plan : plans) {
            if (plan.name.equals(name))
                return plan;
        }
        return null;
    }

    /**
     * getContract searches through the list of contracts and returns the one with the matching name
     * if it's not found it returns null
     *
     * @param name The name of the contract you're looking for
     * @return That contract's specific object.
     */
    Contract getContract(String name) {
        for (Contract contract : contracts) {
            if (contract.getContractName().equals(name))
                return contract;
        }
        return null;
    }

    /**
     * getHome searches through the list of homes and returns the one with the matching name
     * if it's not found it returns null.
     * There is at most one home owned by each person.
     *
     * @param ownnerName The name of the home you're looking for
     * @return That home's specific object.
     */
    Home getHome(String ownnerName) {
        for (Home home : homes) {
            if (home.getOwnerName().equals(ownnerName))
                return home;
        }
        return null;
    }

    /**
     * getCar searches through the list of cars and returns the one with the matching name
     * if it's not found it returns null.
     * There is at most one car owned by each person.
     *
     * @param ownnerName The name of the car you're looking for
     * @return That car's specific object.
     */
    Car getCar(String ownnerName) {
        for (Car car : cars) {
            if (car.getOwnerName().equals(ownnerName))
                return car;
        }
        return null;
    }

    /**
     * totalClaimAmountByCustomer is used to get the total amount claimed by a customer, this is done by going through
     * the list of claims and finding a contract that has a customers name on i t
     *
     * @param customerName Name of the customer
     * @return the total Claim amount
     */
    long totalClaimAmountByCustomer(String customerName) {
        long totalClaimed = 0;
        for (Claim claim : claims) {
            if (getContract(claim.getContractName()).getCustomerName().equals(customerName))
                totalClaimed += claim.getAmount();
        }
        return totalClaimed;
    }


    /**
     * totalReceivedAmountByCustomer is used to get the total amount the customer received.
     * This is done by going through the list of contracts, finding the one with the customers
     * name on it and checking if a claim was successful. If everything passes then the total
     * amount the customer receives is returned ( this is found by subtracting the deductible
     * from the claim amount).
     *
     * @param customerName Name of the customer
     * @return the total amount of money recieved by the customer.
     */
    long totalReceivedAmountByCustomer(String customerName) {
        long totalReceived = 0;
        for (Claim claim : claims) {
            Contract contract = getContract(claim.getContractName());
            if (contract.getCustomerName().equals(customerName)) {
                if (claim.wasSuccessful()) {
                    long deductible = getPlan(contract.getPlanName()).getDeductible();
                    totalReceived += Math.max(0, claim.getAmount() - deductible);
                }
            }
        }
        return totalReceived;
    }

    /**
     * totalCustomersPlan gives the # of customers in a plan
     *
     * @param planName the name of the plan that you are checking
     * @return the number of customers in that plan
     */
    long totalCustomersInPlan(String planName) {
        //Counter for total customers
        long totalCustomers = 0;
        //Going through all contracts, if a contract matches the plan name then increase the counter by 1
        for (Contract contract : contracts) {
            if (contract.getPlanName().equals(planName))
                totalCustomers += 1;
        }
        //returns the amount of customers
        return totalCustomers;
    }

    /**
     * totalPayedToCustomers gives the total amount a plan pays to a customer
     *
     * @param planName the name of the plan that you are checking
     * @return the amount payed to customers
     */
    long totalPayedToCustomers(String planName) {
        //Counter for total payed to customers
        long totalPayed = 0;
        //Iterating through all the claims
        for (Claim claim : claims) {
            //Getting the contract from claim and getting the deductable from the contracts plan
            Contract contract = getContract(claim.getContractName());
            long deductible = getPlan(contract.getPlanName()).getDeductible();
            if (claim.wasSuccessful() && contract.getPlanName().equals(planName))
                //If the claim was successful and the plan names match, then add the claim amount() - deductible.
                totalPayed += Math.max(0, claim.getAmount() - deductible);
            ;
        }
        return totalPayed;
    }
}

