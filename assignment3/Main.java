/**
 * Usman Asad
 * Asadu -- 400199934
 * Computer Science: 2S03 Assignment 3
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //Taking the users input and storing it in a variable
        Scanner keyboard = new Scanner(System.in);
        String userInput = keyboard.nextLine();

        //Creating an InputProcessor Object with the usersInput and printing the solved expression
        InputProcessor input = new InputProcessor(userInput);
        System.out.println(input.solve());

    }
}