import java.util.ArrayList;

class Database {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Home> homes = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Plan> plans = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();
    private ArrayList<Claim> claims = new ArrayList<>();
    //Created an ArrayList of companies, this stores all the data.
    private ArrayList<Company> companies = new ArrayList<>();

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

    //Added a method to insert a company into the database
    void insertCompany(Company company) {
        companies.add(company);
    }


    //Accessor method that returns the name of the company
    Company getCompany(String name) {
        for (Company company : companies) {
            if (company.getCompanyName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    Plan getPlan(String name) {
        for (Plan plan : plans) {
            if (plan.name.equals(name))
                return plan;
        }
        return null;
    }

    Customer getCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    Contract getContract(String name) {
        for (Contract contract : contracts) {
            if (contract.getContractName().equals(name))
                return contract;
        }
        return null;
    }

    /* This function has been updated to output a list
    of homes rather than a single home. In other words,
    an owner may own multiple homes.
     */
    ArrayList<Home> getHomesByOwnerName(String ownerName) {
        ArrayList<Home> result = new ArrayList<>();
        for (Home home : homes) {
            if (home.getOwnerName().equals(ownerName))
                result.add(home);
        }
        return result;
    }


    /* This function has been updated to output a list
    of homes rather than a single home. In other words,
    an owner may own multiple homes.
     */
    ArrayList<Car> getCarsByOwnerName(String ownerName) {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getOwnerName().equals(ownerName))
                result.add(car);
        }
        return result;
    }

    long totalClaimAmountByCustomer(String customerName) {
        long totalClaimed = 0;
        for (Claim claim : claims) {
            if (getContract(claim.getContractName()).getCustomerName().equals(customerName))
                totalClaimed += claim.getAmount();
        }
        return totalClaimed;
    }

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

    Insurable getCarByPlateNumber(String insurableID) {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(insurableID))
                return car;
        }
        return null;
    }

    Insurable getHomeByPostalCode(String insurableID) {
        for (Home home : homes) {
            if (home.getPostalCode().equals(insurableID))
                return home;
        }
        return null;
    }

    /**
     * The function isCompany checks if the name given is either
     * a company name
     *
     * @param name Name  that you are checking
     * @return a boolean value representing if the name is valid or not
     */
    boolean isCompany(String name) {
        //Going through the list of companies and comparing the names
        for (Company bussiness : companies) {
            if (bussiness.getCompanyName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function isCustomer checks if the name given is either
     * a customer name
     *
     * @param name Name  that you are checking
     * @return a boolean value representing if the name is valid or not
     */
    boolean isCustomer(String name) {
        //Going through the list of customers and comparing the names
        for (Customer person : customers) {
            if (person.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function increaseWealth takes in a company name, and then increases
     * all the parent entities wealth by its own.
     *
     * @param companyName Name of the company that you want to increase wealth of
     */
    void increaseWealth(String companyName) {
        //Grabbing the company using the company name
        Company company = getCompany(companyName);

        //The loop will keep running until the customer's wealth increases
        while (true) {
            //If the company is owned by a person, increase the persons
            //wealth by the value of the company
            if (company.getOwner() == true) {
                //extracting the customer that owns the company
                Customer shopper = getCustomer(company.getOwnerName());
                //figuring out how much to increase the customers value by
                long valueDelta = company.getValue()-company.getOldValue();
                shopper.increaseWealth(valueDelta);
                //ending the loop as no one owns the customer
                break;
            } else {
                //While the company is owned by another company
                while (company.getOwner() == false) {
                    //extracting the owner conpany
                    Company ownerCompany = getCompany(company.getOwnerName());
                    //figuring out how much the value should be increased by, then increasing the parent companys value
                    long valueDelta = company.getValue()-company.getOldValue();
                    ownerCompany.increaseValue(valueDelta);
                    //setting the company being checked to the ownerCompany
                    company = ownerCompany;
                }
            }
        }
    }



}
