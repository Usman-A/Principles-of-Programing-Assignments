/**
 * Usman Asad
 * Asadu -- 400199934
 * Computer Science: 2S03 Assignment 3
 */

import java.util.ArrayList;

public class InputProcessor {

    // String that is going to be processed
    private String input;
    //A constant containg the users input, this value will not be changed!
    private String PERM_INPUT;
    //This will be used to find the index within a string later on.
    private int index;

    InputProcessor(String input) {
        this.input = input;
        this.PERM_INPUT = input;
    }

    private int insideLeftBracketPos(String data) {
        //Index is used to store the position of the innerMost Bracket
        index = 0;

        //Finding the inner most bracket, then returning it's value
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '(') {
                index = i;
            }
        }
        return index;
    }

    private int insideRightBracketPos(String data) {
        //Index is used to store the position of the innerMost Bracket
        index = 0;

        //Finding the inner most bracket
        for (int i = insideLeftBracketPos(data); i < data.length(); i++) {
            if (data.charAt(i) == ')') {
                index = i + 1;
                break;
            }
        }
        return index;
    }

    private String removeParenthesis(String data) {
        //Removing all Occurrences of parenthesis in a string and returning it.
        data = data.replace("(", "");
        data = data.replace(")", "");
        return data;
    }

    private String findFirstOperator(String data) {
        //Iterating through the String, and returning the first operator thats detected
        for (int i = 0; i < data.length(); i++) {
            switch (data.charAt(i)) {
                case '@':
                    return "@";

                case '&':
                    return "&";
            }
        }
        return "";
    }

    private String minMaxOperator(String data) {
        //If there are no operators in the expression, then return the expression without brackets.
        if (findFirstOperator(data) == "") {
            return removeParenthesis(data);
        }

        //List that stores output from the split up input string
        String[] decypheredData;
        //Will be used to store a segment of the expression later on
        String tempData;
        //Stores the index of the first Operator in the string
        int posOfFirstOperator = data.indexOf(findFirstOperator(data));
        //String that stores rest of the equation after the first operator
        String remainingEquation = data.substring(posOfFirstOperator + 1);

        //Checks if there is no other operation in the remaining portion of the expression. Occurs in the form of (#Operator#)
        if (findFirstOperator(remainingEquation) == "") {
            //If true then set tempData to data with its parenthesis removed.
            tempData = removeParenthesis(data);
        }
        //If there is another operation, find where it happens, then calculate the index at which the first operation
        //ends at, then set tempData to location of the first operation in the initial input
        else {
            int posOfSecondOperator = remainingEquation.indexOf(findFirstOperator(remainingEquation));
            int endOfFirstOperation = posOfFirstOperator + posOfSecondOperator + 1;
            tempData = removeParenthesis(data.substring(0, endOfFirstOperation));
        }

        //Split the tempData(first Operation on left side) and set it to a string
        //array, then split operands of their parenthesis
        decypheredData = tempData.split(findFirstOperator(tempData));
        decypheredData[0] = removeParenthesis(decypheredData[0]);
        decypheredData[1] = removeParenthesis(decypheredData[1]);

        //Checking which operation is happening @(min) or &(max).
        switch (findFirstOperator(tempData)) {
            case "@":
                //if min, return the lowest value
                if (Integer.parseInt(decypheredData[0]) < Integer.parseInt(decypheredData[1])) {
                    return data.replace(tempData, decypheredData[0]);
                } else {
                    return data.replace(tempData, decypheredData[1]);
                }
            case "&":
                //If max, return the max value
                if (Integer.parseInt(decypheredData[0]) > Integer.parseInt(decypheredData[1])) {
                    return data.replace(tempData, decypheredData[0]);
                } else {
                    return data.replace(tempData, decypheredData[1]);
                }
        }
        return data;
    }

    /*This code is based off of the example ParenthesisMatcher.java that Dr.Hassan Provided
      What it's doing is adding all the open brackets to a list. and if a close bracket is
      found it will remove its corresponding openbracket from the list. If a close bracket
      is found before an open bracket it will return false;  If all goes well the list will
      be empty, if its not then it'll return false because there is an expression error.
     */
    private boolean checkParenthesis(String data) {
        //Creating an ArrayList to store brackets
        ArrayList<String> list = new ArrayList<String>();
        //Last item in the list
        String lastItem;

        //Looping through the characters inside the string
        for (int i = 0; i < data.length(); i++) {
            //If a Open bracket is found, add it to the list
            if (data.charAt(i) == '(') {
                list.add("(");
            } else if (data.charAt(i) == ')') {

                //If the first bracket found is a close bracket, then invalid expression, return false.
                if (list.isEmpty()) {
                    return false;
                }

                //Storing the last element of the list to a 'cache variable' and removing it from the list
                lastItem = list.get(list.size() - 1);
                list.remove(list.size() - 1);

                //If the last element of the list isn't an open Bracket, then invalid expression, return false;
                if (lastItem != "(") {
                    return false;
                }
            }
        }
        //If the brackets are implemented properly, the list will be empty and it will return true
        return list.isEmpty();
    }

    private boolean checkCharacters(String data) {
        //Creating a list of valid characters
        char[] validCharacters = {'(', ')', '@', '&', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //A counter for the amount of correct characters
        int correctCharCount = 0;
        for (int i = 0; i < data.length(); i++) {
            for (int j = 0; j < validCharacters.length; j++) {
                //Counts the amount of correctCharacters in the string
                if (data.charAt(i) == validCharacters[j]) {
                    correctCharCount++;
                }
            }
        }
        //If the input contains only correct characters, return true.
        if (correctCharCount == data.length()) {
            return true;
        }
        return false;
    }

    private boolean checkOperator(String data) {
        //Try to evaluate the string
        try {
            evaluate();
            //If succesfull, resets the input fields value and returns true
            input = PERM_INPUT;
            return true;
        } catch (Exception InvalidOp) {
            //If an error occured, then return false
            return false;
        }
    }

    private boolean canOperate(String data) {
        //If there are operators in the input and the length of the input is less than 1000
        //then return true, otherwise you can't operate on this string and return false
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '&' || data.charAt(i) == '@' && data.length() <= 1000) {
                return true;
            }
        }
        return false;
    }

    private String evaluate() {
        //String that stores the temporary calculation
        String tempCalculation;

        //Keep operating while there are operands in the expression. If there are no operators
        //in the expression being evaluated, the loop will break
        while (canOperate(input)) {
            //Setting the range to the index of the left inner most bracket and right inner most bracket
            int[] range = {insideLeftBracketPos(input), insideRightBracketPos(input)};

            //if there are no brackets, run the minMax Operations on the input and skip the rest of the loop
            if (range[0] == 0 && range[1] == 0) {
                input = minMaxOperator(input);
                continue;
            }

            //Creating a substring that has the contents of the innermost Bracket
            String innerMostExpression = input.substring(range[0], range[1]);

            //Trying to see if the inner most expression is an integer
            try {
                //Removing brackets and attempting to see if contents of innermost bracket is just a number
                String removedBracketVersion = removeParenthesis(innerMostExpression);
                Integer.parseInt(removedBracketVersion);

                //Replacing the inner most bracket with itself with removed parenthesis
                input = input.replace(innerMostExpression, removedBracketVersion);
                //Calculating the innerMostExpression
                tempCalculation = minMaxOperator(innerMostExpression);
                //replacing the innerMostExpression with its solution
                input = input.replace(innerMostExpression, tempCalculation);
            }
            //If the converting it to an integer failed, calculate the innermost bracket.
            catch (Exception error) {
                //Calculating the innerMostExpression
                tempCalculation = minMaxOperator(innerMostExpression);
                //replacing the innerMostExpression with its solution
                input = input.replace(innerMostExpression, tempCalculation);
            }
        }
        //Returning the expression reduced to a single number
        return removeParenthesis(input);
    }

    String solve() {

        //If there are invalid characters, return INVALID CHARACTERS
        if (!checkCharacters(input)) {
            return "INVALID CHARACTERS";
            //If the brackets don't match, the operator is not used properly or the input is blank return INVALID EXPRESSION
        } else if (!checkParenthesis(input) || !checkOperator(input) || input.length() == 0) {
            return "INVALID EXPRESSION";
        }

        //If there are brackets with nothing enclosed between them then it is an invalid Expression.
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' && input.charAt(i + 1) == ')') {
                return "INVALID EXPRESSION";
            }
        }

        //If everything passes, then the equation can be solved.
        if (checkCharacters(input) && checkParenthesis(input) && checkOperator(input)) {
            return evaluate();
        }
        return "";
    }
}
