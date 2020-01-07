/**
 * Usman Asad
 * Asadu -- 400199934
 * Computer Science: 2S03 Assignment 2
 */

import java.util.*;

class Main {

    /***
     The car goes on a trip and uses some fuel.
     @param plateNumber a string of the plate that is being searched for
     @param plateNumber a string of the plate that is being searched for
     @param carCount The amount of cars that have been created
     @return the index of the car in the Array of cars
     */
    public static int findPlate(Car[] cars, int carCount, String plateNumber) {
        //takes in the amount of cars, the list of cars and the desired plateNumber
        for (int i = 0; i < carCount; i++) {
            //if its found return the position of the car in the car array
            if (Integer.parseInt(plateNumber) == cars[i].getPlateNumber()) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        //Declaring Initial Objects and variables
        Scanner keyboard = new Scanner(System.in);
        String userInput = " ";
        String[] decypheredInput;
        Car[] cars = new Car[100];                   //Maximum 100 cars
        CarModel[] carModels = new CarModel[100];    //Maximum 100 carModels
        int cmdCounter = 0;    //Counts the amount of commands made
        int carCount = 0;      //Counts the amount of cars made
        int modelCount = 0;    //Counts the numbers of models made
        int carPosition;       //this is used to match the plate of the user input with the cars plate
        int tripCount = 0;     //this is used to count the number of long trips
        ArrayList<String[]> decypheredList = new ArrayList<String[]>(); //List that holds the user inputs
        ArrayList<Double> tripsDone = new ArrayList<Double>();          // List used to store successful trips

        //Constantly getting userinput until the command limit is reached or FINISH is entered
        while (!userInput.equals("FINISH") && cmdCounter <= 100) {
            userInput = keyboard.nextLine();
            if (userInput.equals("FINISH")) {
                break;
            }
            //breaking down the string into a list of words
            decypheredList.add(userInput.split(" "));
            cmdCounter++;
        }

        //Running a loop through the list of inputs
        for (String[] command : decypheredList) {
            //Switch is used to check what command is being used
            switch (command[0]) {
                case "MODEL":
                    //creating a model based off of the user input
                    carModels[modelCount] = new CarModel(command[1], Double.parseDouble(command[2]), Double.parseDouble(command[3]));
                    modelCount++;
                    break;

                case "CAR":
                    //Scans for the right carModel Object
                    for (int i = 0; i < modelCount; i++) {
                        if (command[1].equals(carModels[i].getName())) {
                            //Creates a car using the user input and the correct object
                            cars[carCount] = new Car(carModels[i], Integer.parseInt(command[2]));
                        }
                    }
                    carCount++;
                    break;

                case "TRIP":
                    //Uses the method findPlate to determine the right car
                    carPosition = findPlate(cars, carCount, command[1]);

                    //Print's corresponding messages based off of whether or not the car can complete the trip
                    if (cars[carPosition].trip(Double.parseDouble(command[2])) == true) {
                        System.out.println("Trip completed successfully for #" + cars[carPosition].getPlateNumber());
                    } else {
                        System.out.println("Not enough fuel for #" + cars[carPosition].getPlateNumber());
                    }
                    break;

                case "REFILL":
                    //Uses the method findPLat to determine the right car
                    carPosition = findPlate(cars, carCount, command[1]);

                    //Refills the selected car
                    cars[carPosition].refill();
                    break;

                case "LONGTRIPS":
                    //Uses the method findPLat to determine the right car
                    carPosition = findPlate(cars, carCount, command[1]);

                    //Uses the getTripsDone() method to retrieve the successfully completed trips
                    tripsDone = cars[carPosition].getTripsDone();

                    //Loop goes through each element in the list and checks if the there are trips longer than
                    //the users inputs, if so it increases the tripCount
                    for (double trip : tripsDone) {
                        if (trip > Double.parseDouble(command[2])) {
                            tripCount++;
                        }
                    }
                    //Printing the number of long trips
                    System.out.println("#" + cars[carPosition].getPlateNumber() + " made " + tripCount + " trips longer than " + command[2]);
                    tripCount = 0;
                    break;

                case "FINISH":
                    break;
            }
        }
    }
}

