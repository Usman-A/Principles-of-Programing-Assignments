import java.util.ArrayList;

class Car {

    // Current gas level in liters
    private double currentGasLevel;

    // Model of the car and its details.
    private CarModel model;

    // Plate number, which is supposed to be unique among all cars.
    private int plateNumber;

    // List of successful trips and their distances
    private ArrayList<Double> tripsDone = new ArrayList<Double>();

    /***
     * Creates a full-tank car from the given model.
     * @param model
     */
    Car(CarModel model, int plateNumber) {
        this.model = model;
        this.currentGasLevel = model.getGasTankSize();
        this.plateNumber = plateNumber;
      //  this.tripsDone = tripsDone;
    }

    void refill() {
        currentGasLevel = model.getGasTankSize();
    }

    /***
     The car goes on a trip and uses some fuel.
    @param distance The length of the trip in kilometers.
     @return true if the trip was successful, false if there was not enough fuel.
      */
    boolean trip(double distance) {
        double estimatedFuelConsumption = (distance/100.0)*model.getFuelEconomy();
        if (estimatedFuelConsumption > currentGasLevel) {
            currentGasLevel = 0;
            return false;
        }
        currentGasLevel -= estimatedFuelConsumption;

        //Adds the trip distance to the list of successful trips
        tripsDone.add(distance);
        return true;
    }

    int getPlateNumber() {
        return plateNumber;
    }

    ArrayList<Double> getTripsDone() {return tripsDone;}

    double getCurrentGasLevel() {
        return currentGasLevel;
    }
}
